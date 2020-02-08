package guru.pmouse.recipe.controllers;

import guru.pmouse.recipe.domain.Recipe;
import guru.pmouse.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by PMouse Guru  on 12/22/2019
 */
class IndexControllerTest {
    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);

    }

    @Test
    void getIndexPage() {

        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);

        Recipe recipe2 = new Recipe();
        recipe1.setId(2L);

        Set<Recipe> recipes = new HashSet<>();

        recipes.add(recipe1);recipes.add(recipe2);

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String indexPage = indexController.getIndexPage(model);

        //then
        assertEquals("index", indexPage);

        verify(recipeService, times(1)).getRecipes();

        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        Set<Recipe> setInController = argumentCaptor.getValue();

        assertEquals(2, setInController.size());


    }

    @Test
    void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));
    }
}


