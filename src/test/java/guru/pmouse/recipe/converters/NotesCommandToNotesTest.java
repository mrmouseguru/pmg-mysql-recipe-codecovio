package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.NotesCommand;
import guru.pmouse.recipe.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by PMouse Guru  on 12/25/2019
 */
class NotesCommandToNotesTest {
    public static final long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    NotesCommandToNotes notesCommandToNotes;
    @BeforeEach
    void setUp() {
        notesCommandToNotes = new NotesCommandToNotes();
    }

    @Test
    void testNullObject() {
        assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
    }


    @Test
    void convert() {
        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(DESCRIPTION);
        //when
        Notes notes = notesCommandToNotes.convert(notesCommand);
        //then
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(DESCRIPTION, notes.getRecipeNotes());
    }
}