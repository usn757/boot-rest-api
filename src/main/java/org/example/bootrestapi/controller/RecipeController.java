package org.example.bootrestapi.controller;


import org.apache.coyote.BadRequestException;
import org.example.bootrestapi.model.dto.RecipeDTO;
import org.example.bootrestapi.model.entity.Recipe;
import org.example.bootrestapi.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe") // Spring MVC
@CrossOrigin // 다 풀리는 거고 -> Open API
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
//    public List<Recipe> getAllRecipes() {
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        // localhost:8080/api/recipe
        //        Recipe r = new Recipe();
        //        r.setName("커리");
        //        r.setDescription("맛있는 커리");
        //        recipeService.save(r);
        return ResponseEntity.ok(recipeService.findAll());
    }

    @PostMapping
//    public Recipe addRecipe(@RequestBody RecipeDTO recipeDTO) {
    public ResponseEntity<Recipe> addRecipe(@RequestBody RecipeDTO recipeDTO) throws BadRequestException {
        Recipe recipe = new Recipe();
        recipe.setName(recipeDTO.name());
        recipe.setDescription(recipeDTO.description());
//        return recipeService.save(recipe);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recipeService.save(recipe));

    }

    @DeleteMapping("/{id}") // recipe/{id}. recipe?id=???
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        // 204는 response body가 없다!
        // 200 등의 다른 코드를 쓰거나...
    }


    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id, @RequestBody RecipeDTO recipeDTO) throws BadRequestException {
        Recipe oldrecipe = recipeService.findById(id);
        oldrecipe.setName(recipeDTO.name());
        oldrecipe.setDescription(recipeDTO.description());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recipeService.save(oldrecipe));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Recipe> updateName(@PathVariable long id, @RequestBody RecipeDTO recipeDTO) throws BadRequestException {
        Recipe oldrecipe = recipeService.findById(id);
        oldrecipe.setName(recipeDTO.name());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recipeService.save(oldrecipe));
    }
}