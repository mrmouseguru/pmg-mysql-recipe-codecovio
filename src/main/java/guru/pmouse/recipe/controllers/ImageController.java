package guru.pmouse.recipe.controllers;

import guru.pmouse.recipe.commands.RecipeCommand;
import guru.pmouse.recipe.services.ImageService;
import guru.pmouse.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by PMouse Guru  on 01/19/2020
 */
@Controller
@Slf4j
public class ImageController {
    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable Long id, Model model){

        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(id, file);
        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("/recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {

        log.debug("preparing for render Image ...");

        RecipeCommand recipeCommand = recipeService.findCommandById(id);

        byte[] byteArray = new byte[recipeCommand.getImage().length];

        int i = 0;
        for(Byte wrappedByte: recipeCommand.getImage()){
            byteArray[i++] = wrappedByte; //auto unboxing
        }

        response.setContentType("image/jpeg");
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        IOUtils.copy(inputStream, response.getOutputStream());
    }





}
