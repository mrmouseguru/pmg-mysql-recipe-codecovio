package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.NotesCommand;
import guru.pmouse.recipe.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by PMouse Guru  on 12/25/2019
 */
class NotesToNotesCommandTest {

    public static final long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "recipenotes";
    NotesToNotesCommand notesToNotesCommand;

    @BeforeEach
    void setUp() {
        notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void testNullObject() {
        assertNull(notesToNotesCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(notesToNotesCommand.convert(new Notes()));
    }

    @Test
    void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);
        //when
        NotesCommand notesCommand = notesToNotesCommand.convert(notes);
        //then
        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());

    }
}