package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.CategoryCommand;
import guru.pmouse.recipe.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by PMouse Guru  on 12/24/2019
 */
class CategoryToCommandCategoryTest {

    public static final long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CategoryToCategoryCommand categoryToCommandCategory;

    @BeforeEach
    void setUp() {
        categoryToCommandCategory = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObject() {
        assertNull(categoryToCommandCategory.convert(null));

    }

    @Test
    void testEmptyObject() {
        assertNotNull(categoryToCommandCategory.convert(new Category()));
    }

    @Test
    void convert() {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = categoryToCommandCategory.convert(category);

        //then
        assertNotNull(categoryCommand);
        assertNotNull(categoryCommand.getId());
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}