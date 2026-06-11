package net.acoyt.recomposed.mixin.potions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.SetPotionLootFunction;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(SetPotionLootFunction.class)
public abstract class SetPotionLootFunctionMixin {
    @WrapMethod(method = "process")
    private ItemStack recomposed$dontSetIfDisable(ItemStack stack, LootContext context, Operation<ItemStack> original) {
        ItemStack value = original.call(stack, context);
        PotionContentsComponent contents = value.get(DataComponentTypes.POTION_CONTENTS);
        if (contents != null && contents.potion().isPresent() && CRUtil.isPotionDisabled(contents.potion().get())) {
            return ItemStack.EMPTY;
        }

        return value;
    }
}
