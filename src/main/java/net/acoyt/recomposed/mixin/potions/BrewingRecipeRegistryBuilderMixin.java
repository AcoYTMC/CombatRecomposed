package net.acoyt.recomposed.mixin.potions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(BrewingRecipeRegistry.Builder.class)
public abstract class BrewingRecipeRegistryBuilderMixin {
    @WrapMethod(method = "registerRecipes")
    private void recomposed$disabledPotionRecipes(Item ingredient, RegistryEntry<Potion> potion, Operation<Void> original) {
        if (CRUtil.isPotionDisabled(potion)) return;
        original.call(ingredient, potion);
    }

    @WrapMethod(method = "registerPotionRecipe")
    private void recomposed$disabledPotionRecipe(RegistryEntry<Potion> input, Item ingredient, RegistryEntry<Potion> output, Operation<Void> original) {
        if (CRUtil.isPotionDisabled(input) || CRUtil.isPotionDisabled(output)) return;
        original.call(input, ingredient, output);
    }
}
