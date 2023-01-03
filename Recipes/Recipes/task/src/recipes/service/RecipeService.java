package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import recipes.entity.Recipe;
import recipes.repository.RecipeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RecipeService {
    RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public ResponseEntity<String> addRecipe(Recipe recipe) {
        try {
            recipeRepository.save(recipe);
            String response = String.format("""
                {
                   "id": %d
                }
                """, recipe.getId());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Recipe> getRecipe(long id) {
        try {
            return ResponseEntity.ok().body(recipeRepository.findById(id).get());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Recipe> modifyRecipe(long id, Recipe newRecipe) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails details = (UserDetails) auth.getPrincipal();
            Recipe oldRecipe = recipeRepository.findById(id).get();

            if (oldRecipe.getUser().getEmail().equals(details.getUsername())) {
                oldRecipe.copyOf(newRecipe);
                recipeRepository.save(oldRecipe);
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Recipe> deleteRecipe(long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails details = (UserDetails) auth.getPrincipal();
            Recipe recipe = recipeRepository.findById(id).get();

            if (recipe.getUser().getEmail().equals(details.getUsername())) {
                recipeRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EmptyResultDataAccessException | NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Recipe>> searchRecipesByCategory(String category) {
        try {
            return ResponseEntity.ok().body(recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<Recipe>> searchRecipesByName(String name) {
        try {
            return ResponseEntity.ok().body(recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
