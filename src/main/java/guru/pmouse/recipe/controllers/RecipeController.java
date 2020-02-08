package guru.pmouse.recipe.controllers;

import guru.pmouse.recipe.commands.RecipeCommand;
import guru.pmouse.recipe.exceptions.NotfoundException;
import guru.pmouse.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by PMouse Guru  on 12/23/2019
 */
@Slf4j
@Controller
public class RecipeController {

    public static final String RECIPE_FORM_URL = "recipe/recipeform";
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable Long id, Model model){

        model.addAttribute("recipe", recipeService.findById((id)));

        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_FORM_URL;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid  @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return RECIPE_FORM_URL;

        }

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show" ;
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model){

        model.addAttribute("recipe", recipeService.findCommandById(id));

        return RECIPE_FORM_URL;
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable Long id){
        log.debug("Preparing for delete Recipe ...");
        recipeService.deleteById(id);
        log.debug("Deleted Recipe");
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotfoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }



}
