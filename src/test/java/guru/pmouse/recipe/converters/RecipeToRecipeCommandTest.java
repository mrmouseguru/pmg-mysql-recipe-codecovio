package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.RecipeCommand;
import guru.pmouse.recipe.domain.Category;
import guru.pmouse.recipe.domain.Difficulty;
import guru.pmouse.recipe.domain.Ingredient;
import guru.pmouse.recipe.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by PMouse Guru  on 12/25/2019
 */
class RecipeToRecipeCommandTest {

    public static final long VALUE_ID = 1L;
    public static final String DESCRIPTIONS = "descriptions";
    public static final int PREP_TIME = 50;
    public static final int COOK_TIME = 20;
    public static final int SERVINGS = 4;
    public static final String SOURCE = "not simple";
    public static final String URL = "http://example.com";
    public static final String DIRECTIONS = "directions";
    public static final Difficulty DIFFICULTY = Difficulty.MODERATE;
    public static final long INGREDIENT_ID_1 = 1L;
    public static final long INGREDIENT_ID_2 = 2L;
    public static final long CATEGORY_ID_1 = 1L;
    public static final long CATEGORY_ID_2 = 2L;
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        recipeToRecipeCommand = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
    }

    @Test
    void testNullObject() {
        assertNull(recipeToRecipeCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(recipeToRecipeCommand.convert(new Recipe()));
    }

    @Test
    void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(VALUE_ID);
        recipe.setDescription(DESCRIPTIONS);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID_1);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient1);

        Category category = new Category();
        category.setId(CATEGORY_ID_1);

        Category category1 = new Category();
        category1.setId(CATEGORY_ID_2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category1);
        //when
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        //then
        assertNotNull(recipe);
        assertEquals(VALUE_ID, recipeCommand.getId());
        assertEquals(DESCRIPTIONS, recipeCommand.getDescription());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());

        assertEquals(2, recipeCommand.getIngredients().size());
        assertEquals(2, recipeCommand.getCategories().size());
    }
}