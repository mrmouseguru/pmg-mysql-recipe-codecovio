package guru.pmouse.recipe.services;

import guru.pmouse.recipe.commands.RecipeCommand;
import guru.pmouse.recipe.converters.RecipeCommandToRecipe;
import guru.pmouse.recipe.converters.RecipeToRecipeCommand;
import guru.pmouse.recipe.domain.Recipe;
import guru.pmouse.recipe.exceptions.NotfoundException;
import guru.pmouse.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by PMouse Guru  on 12/22/2019
 */
@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {
    public static final long ID = 1L;
    public static final long RECIPE_ID = 1L;
    public static final long RECIPE_COMMAND_ID = RECIPE_ID;
    public static final long SAVED_RECIPE_ID = 2L;
    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.initMocks(this);
        //recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);

        verify(recipeRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        //given
        Recipe returnRecipe = Recipe.builder().id(1L).build();
        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(returnRecipe));
        Recipe recipe = recipeService.findById(1L);
        //then
        assertNotNull(recipe, "Not null returned");
        assertNotNull(recipe.getId());
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void testFindRecipeByIdNotFound() {
        //given
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        // Recipe recipe;
        //when

        //then
        //should go boom
        Exception exception = assertThrows(NotfoundException.class, () -> {
            recipeService.findById(1L);
        });

        assertTrue(exception.getMessage().contains("Recipe Not Found"));
    }

    @Test
    void saveRecipeCommand() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);

        Recipe detachedRecipe = new Recipe();
        detachedRecipe.setId(RECIPE_ID);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(RECIPE_ID);

        RecipeCommand returnRecipeCommand = new RecipeCommand();
        returnRecipeCommand.setId(RECIPE_ID);

        when(recipeCommandToRecipe.convert(any())).thenReturn(detachedRecipe);

        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        when(recipeToRecipeCommand.convert(any())).thenReturn(returnRecipeCommand);

        //then
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //when
        assertNotNull(savedRecipeCommand);
        assertEquals(savedRecipeCommand.getId(), recipeCommand.getId());
        verify(recipeCommandToRecipe, times(1)).convert(any());
        verify(recipeRepository, times(1)).save(any());
        // verify(recipeToRecipeCommand, times(1)).convert(any());
    }

    @Test
    void findRecipeCommandById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);
        //when
        RecipeCommand foundCommand = recipeService.findCommandById(1L);
        //then

        assertNotNull(foundCommand);
        assertEquals(1L, foundCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeToRecipeCommand, times(1)).convert(any());

    }

    @Test
    void testDeleteRecipeById() {
        //given
        Long idToDelete = 1L;
        //then
        recipeService.deleteById(idToDelete);
        //when
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }


}