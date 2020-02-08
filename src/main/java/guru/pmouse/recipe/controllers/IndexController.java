package guru.pmouse.recipe.controllers;

import guru.pmouse.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Guru Mouse on 12/07/2019
 */
@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String getIndexPage(Model model){
        log.debug("Getting index page");

        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
