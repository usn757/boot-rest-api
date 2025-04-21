package org.example.bootrestapi.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.bootrestapi.model.entity.Recipe;
import org.example.bootrestapi.model.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceJPAImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe save(Recipe recipe) throws BadRequestException {
        if (recipe.getName().isEmpty()) {
            throw new BadRequestException("이름이 없다");
        }
        return recipeRepository.save(recipe);
    }

    @Override
    public void delete(long id) {
        recipeRepository.deleteById(id);
    }
}
