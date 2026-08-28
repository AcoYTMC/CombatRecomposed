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
    private void recomposed$disabledPotionRecipes(Item item, Holder<Potion> holder, Operation<Void> original) {
        if (CRUtil.isPotionDisabled(holder)) return;
        original.call(item, holder);
    }

    @WrapMethod(method = "addMix")
    private void recomposed$disabledPotionRecipe(Holder<Potion> holder, Item item, Holder<Potion> holder2, Operation<Void> original) {
        if (CRUtil.isPotionDisabled(holder) || CRUtil.isPotionDisabled(holder2)) return;
        original.call(holder, item, holder2);
    }
}
