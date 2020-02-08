package guru.pmouse.recipe.converters;

import guru.pmouse.recipe.commands.RecipeCommand;
import guru.pmouse.recipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by PMouse Guru  on 12/25/2019
 */
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesCommandToNotes;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final CategoryCommandToCategory categoryCommandToCategory;

    public RecipeCommandToRecipe(NotesCommandToNotes notesCommandToNotes, IngredientCommandToIngredient ingredientCommandToIngredient, CategoryCommandToCategory categoryCommandToCategory) {
        this.notesCommandToNotes = notesCommandToNotes;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if(recipeCommand == null){
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setNotes(notesCommandToNotes.convert(recipeCommand.getNotes()));
        recipe.setDifficulty(recipeCommand.getDifficulty());

        if(recipeCommand.getIngredients() != null
                && recipeCommand.getIngredients().size() > 0){
            recipeCommand.getIngredients()
                    .forEach(ingredientCommand -> recipe.getIngredients()
                            .add(ingredientCommandToIngredient.convert(ingredientCommand)));
        }
        if(recipeCommand.getCategories() != null
                && recipeCommand.getCategories().size() > 0 ){
            recipeCommand.getCategories()
                    .forEach(categoryCommand -> recipe.getCategories()
                            .add(categoryCommandToCategory.convert(categoryCommand)));
        }
        return recipe;
    }
}
