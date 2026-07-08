package net.acoyt.recomposed.mixin.potions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(PotionBrewing.Builder.class)
public abstract class PotionBrewingBuilderMixin {
    @WrapMethod(method = "addStartMix")
    private void recomposed$disabledPotionRecipes(Item ingredient, Holder<Potion> potion, Operation<Void> original) {
        if (CRUtil.isPotionDisabled(potion)) return;
        original.call(ingredient, potion);
    }

    @WrapMethod(method = "addMix")
    private void recomposed$disabledPotionRecipe(Holder<Potion> input, Item ingredient, Holder<Potion> output, Operation<Void> original) {
        if (CRUtil.isPotionDisabled(input) || CRUtil.isPotionDisabled(output)) return;
        original.call(input, ingredient, output);
    }
}
