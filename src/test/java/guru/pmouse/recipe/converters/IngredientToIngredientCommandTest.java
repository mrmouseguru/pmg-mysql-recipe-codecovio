package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.IngredientCommand;
import guru.pmouse.recipe.domain.Ingredient;
import guru.pmouse.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by PMouse Guru  on 12/24/2019
 */
class IngredientToIngredientCommandTest {

    public static final long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final long UOM_ID = 1L;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    @BeforeEach
    void setUp() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void testNullObject() {
        assertNull(ingredientToIngredientCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));
    }

    @Test
    void testConvertNullUOM() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setUom(null);
        //when
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
        //then
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(null, ingredientCommand.getUom());
    }

    @Test
    void convert() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        ingredient.setUom(uom);

        //when
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());

        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertNotNull(ingredientCommand.getUom());
        assertEquals(UOM_ID, ingredientCommand.getUom().getId());
    }
}