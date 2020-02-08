package guru.pmouse.recipe.controllers;

import guru.pmouse.recipe.commands.RecipeCommand;
import guru.pmouse.recipe.domain.Recipe;
import guru.pmouse.recipe.exceptions.NotfoundException;
import guru.pmouse.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by PMouse Guru  on 12/23/2019
 */
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void showById() throws Exception {
        Recipe returnRecipe = Recipe.builder().id(1L).build();
        //when
        when(recipeService.findById(anyLong())).thenReturn(returnRecipe);

        //then
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testShowByIdNotFound() throws Exception {
        //when
        when(recipeService.findById(anyLong())).thenThrow(NotfoundException.class);

        //then
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void testShowByIdNumberFormatException() throws Exception {

        //then
        mockMvc.perform(get("/recipe/dsfdd/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    void testShowByIdNotFoundPage() throws Exception {
        //when
        when(recipeService.findById(anyLong())).thenThrow(NotfoundException.class);

        //then
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNewRecipeForm() throws Exception {

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    void testSaveOrUpdate() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        //when
        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
        //then
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some directions")
        ).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    void testSaveOrUpdateFormValidationFail() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        //when
        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
        //then
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some directions")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    void testUpdateRecipeForm() throws Exception {
        //given
        RecipeCommand recipeCommandReturn = new RecipeCommand();
        recipeCommandReturn.setId(1L);
        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommandReturn);
        //then
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }


}