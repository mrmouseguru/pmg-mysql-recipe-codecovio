package guru.pmouse.recipe.services;

import guru.pmouse.recipe.commands.IngredientCommand;
import guru.pmouse.recipe.converters.IngredientCommandToIngredient;
import guru.pmouse.recipe.converters.IngredientToIngredientCommand;
import guru.pmouse.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.pmouse.recipe.domain.Ingredient;
import guru.pmouse.recipe.domain.Recipe;
import guru.pmouse.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by PMouse Guru  on 12/31/2019
 */
@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional
                = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            log.error("Ingredient id not found. Id: " + ingredientId);
        }
        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){
            log.error("Not found Recipe for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                //update ingredient
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setUom(unitOfMeasureCommandToUnitOfMeasure.convert(ingredientCommand.getUom()));
            }else{
                //add new ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check safety
            if(!savedIngredientOptional.isPresent()){

                //best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            return  ingredientToIngredientCommand.convert(savedIngredientOptional.get());

        }

    }

    @Override
    public void deleteById(Long recipeId, Long idToDelete) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();
            log.debug("Recipe found Id: " + recipe.getId());

            Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
                    .findFirst();

            if(optionalIngredient.isPresent()){

                log.debug("Ingredient found ");
                Ingredient ingredientToDelete = optionalIngredient.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(optionalIngredient.get());
                recipeRepository.save(recipe);
            }
        }else{
            log.debug("Recipe Id Not found Id: " + recipeId);
        }
    }

}
