package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.CategoryCommand;
import guru.pmouse.recipe.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by PMouse Guru  on 12/23/2019
 */
class CategoryCommandToCategoryTest {

    public static final long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    void testNullParameter() {
        assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);
        //when
        Category category = categoryCommandToCategory.convert(categoryCommand);

        //then
        assertNotNull(category);
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}