package net.acoyt.recomposed.impl.event;

import net.acoyt.acornlib.api.event.FilterRecipesEvent;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;

import java.util.Map;

/**
 * @author AcoYT
 */
public class CRRemoveRecipesEvent implements FilterRecipesEvent {
    public void filterRecipesByType(Map<Identifier, RecipeEntry<?>> entries) {
        entries.remove(Identifier.ofVanilla("shield"));
    }
}
