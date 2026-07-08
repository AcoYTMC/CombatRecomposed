package net.acoyt.recomposed.impl.event;

import net.acoyt.acornlib.api.event.FilterRecipesEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import java.util.Map;

/**
 * @author AcoYT
 */
public class CRRemoveRecipesEvent implements FilterRecipesEvent {
    public void filterRecipesByType(Map<ResourceLocation, RecipeHolder<?>> entries) {
        entries.remove(ResourceLocation.withDefaultNamespace("shield"));
    }
}
