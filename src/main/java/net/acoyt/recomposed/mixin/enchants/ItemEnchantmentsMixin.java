package net.acoyt.recomposed.mixin.enchants;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(ItemEnchantments.class)
public abstract class ItemEnchantmentsMixin {
    @WrapOperation(
            method = "addToTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/Enchantment;getFullname(Lnet/minecraft/core/Holder;I)Lnet/minecraft/network/chat/Component;"
            )
    )
    private Component recomposed$setTemporaryValues(Holder<Enchantment> enchantment, int level, Operation<Component> original, @Local(argsOnly = true) TooltipFlag type) {
        if (type.isAdvanced()) level = CRUtil.getFunctionalLevel(enchantment);
        return original.call(enchantment, level);
    }
}
