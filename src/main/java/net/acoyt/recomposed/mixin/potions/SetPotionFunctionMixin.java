package net.acoyt.recomposed.mixin.potions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(SetPotionFunction.class)
public abstract class SetPotionFunctionMixin {
    @WrapMethod(method = "run")
    private ItemStack recomposed$dontSetIfDisable(ItemStack stack, LootContext context, Operation<ItemStack> original) {
        ItemStack value = original.call(stack, context);
        PotionContents contents = value.get(DataComponents.POTION_CONTENTS);
        if (contents != null && contents.potion().isPresent() && CRUtil.isPotionDisabled(contents.potion().get())) {
            return ItemStack.EMPTY;
        }

        return value;
    }
}
