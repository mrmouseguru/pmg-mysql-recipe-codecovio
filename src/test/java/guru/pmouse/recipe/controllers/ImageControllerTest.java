package guru.pmouse.recipe.controllers;

import guru.pmouse.recipe.commands.RecipeCommand;
import guru.pmouse.recipe.services.ImageService;
import guru.pmouse.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by PMouse Guru  on 01/19/2020
 */
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @InjectMocks
    ImageController imageController;

    @Mock
    RecipeService recipeService;

    @Mock
    ImageService imageService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Object[] controllers;
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void showUploadForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));
        //then
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void handleImagePost() throws Exception {

        //given
        MockMultipartFile multipartFile = new
                MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "P Mouse Guru".getBytes());

        //when
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        //then
        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    void testRenderImageFromDB() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        String s = "fake image text";

        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;
        for (byte b : s.getBytes()) {
            bytesBoxed[i++] = b;
        }

        recipeCommand.setImage(bytesBoxed);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        //when
        MockHttpServletResponse response =  mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] reponseBytes = response.getContentAsByteArray();
        //then
        assertEquals(s.getBytes().length, reponseBytes.length);

    }

    @Test
    void testRenderImageFromDBNumberFormat() throws Exception {
        mockMvc.perform(get("/recipe/dad/recipeimage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}