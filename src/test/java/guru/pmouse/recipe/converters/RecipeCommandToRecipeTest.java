package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.CategoryCommand;
import guru.pmouse.recipe.commands.IngredientCommand;
import guru.pmouse.recipe.commands.NotesCommand;
import guru.pmouse.recipe.commands.RecipeCommand;
import guru.pmouse.recipe.domain.Difficulty;
import guru.pmouse.recipe.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by PMouse Guru  on 12/25/2019
 */
class RecipeCommandToRecipeTest {

    public static final long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    public static final int PREP_TIME = 20;
    public static final int COOK_TIME = 20;
    public static final int SERVINGS = 3;
    public static final String SOURCE = "simple";
    public static final String URL = "example.com";
    public static final String DIRECTIONS = "directions";
    public static final long NOTES_ID = 1L;
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final long INGREDIENT_ID_1 = 1L;
    public static final long INGREDIENT_ID_2 = 2L;
    public static final long CATEGORY_ID_1 = 1L;
    public static final long CATEGORY_ID_2 = 2L;
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        recipeCommandToRecipe = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), new CategoryCommandToCategory());
    }

    @Test
    void testNullObject() {
        assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID_VALUE);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        recipeCommand.setNotes(notesCommand);

        //INGREDIENT
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID_1);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(INGREDIENT_ID_2);

        recipeCommand.getIngredients().add(ingredientCommand);
        recipeCommand.getIngredients().add(ingredientCommand1);
        //CATEGORY
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(CATEGORY_ID_1);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CATEGORY_ID_2);

        recipeCommand.getCategories().add(categoryCommand);
        recipeCommand.getCategories().add(categoryCommand1);

        //when
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        //then
        assertNotNull(recipe);
        assertEquals(ID_VALUE, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals(2, recipe.getCategories().size());
    }
}