package seng202.team1.data;

import seng202.team1.model.Recipe;

import java.util.Set;

@Deprecated
public interface RecipeDAO {

    /**
     * returns all the Recipes stored in the system.
     * @return a List of all Recipes stored in the system
     */
    Set<Recipe> getAllRecipes();

    /**
     * gets a single Recipe from the system.
     * @param code the Recipe's unique code
     * @return the desired Recipe or null if not found
     */
    Recipe getRecipeById(String code);

    /**
     * adds a Recipe to storage. the Recipe will be stored using its code attribute.
     * @param recipe the Recipe to store
     */
    void addRecipe(Recipe recipe);

    /**
     * sets the properties of a Recipe to those of a new Recipe.
     * the new Recipe's code should be the same as the one that is being changed.
     * @param code the code of the Recipe to edit
     * @param alteredRecipe the new Recipe whose information should replace the old Recipe's
     */
    void editRecipe(String code, Recipe alteredRecipe);

    /**
     * remove an Recipe from storage.
     * @param code the code of the Recipe to remove
     */
    void removeRecipe(String code);

}
