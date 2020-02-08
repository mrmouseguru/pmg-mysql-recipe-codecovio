package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.IngredientCommand;
import guru.pmouse.recipe.commands.UnitOfMeasureCommand;
import guru.pmouse.recipe.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by PMouse Guru  on 12/24/2019
 */
class IngredientCommandToIngredientTest {

    public static final long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final long UOM_CM_ID = 1L;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    @BeforeEach
    void setUp() {
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testNullObject() {
        assertNull(ingredientCommandToIngredient.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
    }

    @Test
    void testNullUOMCommand() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setUom(null);
        //when
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        //then
        assertNotNull(ingredient);
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(null, ingredient.getUom());

    }

    @Test
    void convert() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_CM_ID);
        ingredientCommand.setUom(unitOfMeasureCommand);
        //when
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        //then
        assertNotNull(ingredient);
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(UOM_CM_ID, ingredient.getUom().getId());
    }
}