package guru.pmouse.recipe.services;

import guru.pmouse.recipe.commands.IngredientCommand;
import guru.pmouse.recipe.converters.IngredientCommandToIngredient;
import guru.pmouse.recipe.converters.IngredientToIngredientCommand;
import guru.pmouse.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.pmouse.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.pmouse.recipe.domain.Ingredient;
import guru.pmouse.recipe.domain.Recipe;
import guru.pmouse.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by PMouse Guru  on 01/01/2020
 */
@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    // @InjectMocks
    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngredientServiceImplTest() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

    }

    @BeforeEach
    void setUp() {

        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureCommandToUnitOfMeasure);

    }

    @Test
    void findByRecipeIdAndIngredientIdReal() {
        //given
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when

        assertNotNull(ingredientCommand);

        verify(recipeRepository, times(1)).findById(anyLong());

        assertEquals(3L, ingredientCommand.getId());

        assertEquals(1L, ingredientCommand.getRecipeId());

    }

    @Test
    void findByRecipeIdAndIngredientIdFullMock() {
        /*//given
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);


        IngredientCommand ingredientCommandReturn = new IngredientCommand();
        ingredientCommandReturn.setId(2L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);


        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommandReturn);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when

        assertNotNull(ingredientCommand);

        verify(ingredientToIngredientCommand).convert(any());

        verify(recipeRepository, times(1)).findById(anyLong());

        assertEquals(2L, ingredientCommand.getId());

        // assertEquals(1L, ingredientCommand.getRecipeId());*/

    }

    @Test
   // @Transactional
    void testSaveIngredientCommand() {
        //given

        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);


        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);


        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(3L, savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    void testDeleteIngredientById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        ingredient.setRecipe(recipe);

        recipe.addIngredient(ingredient);

        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        //when
        ingredientService.deleteById(1L, 3L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }
}