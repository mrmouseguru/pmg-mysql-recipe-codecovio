package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.NotesCommand;
import guru.pmouse.recipe.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by PMouse Guru  on 12/24/2019
 */
@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if(notesCommand == null){
            return  null;
        }

        final Notes notes = new Notes();
        notes.setId(notesCommand.getId());
        notes.setRecipeNotes(notesCommand.getRecipeNotes());
        return notes;
    }
}
