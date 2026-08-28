package net.acoyt.recomposed.mixin.enchants.level;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.index.tag.CRItemTags;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * @author AcoYT
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @WrapOperation(
            method = "updateEnchantments",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/ItemEnchantments$Mutable;toImmutable()Lnet/minecraft/world/item/enchantment/ItemEnchantments;"
            )
    )
    private static ItemEnchantments recomposed$universalLevel(ItemEnchantments.Mutable instance, Operation<ItemEnchantments> original) {
        ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        original.call(instance).keySet().forEach(enchantment -> builder.upgrade(enchantment, 1));
        return builder.toImmutable();
    }

    @ModifyVariable(method = "setEnchantments", at = @At("HEAD"), argsOnly = true)
    private static ItemEnchantments recomposed$universalLevel(ItemEnchantments itemEnchantments, ItemStack itemStack) {
        ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(itemEnchantments);
        itemEnchantments.keySet().forEach(enchantment -> builder.set(enchantment, 1));
        return builder.toImmutable();
    }

    @WrapMethod(method = "getItemEnchantmentLevel")
    private static int recomposed$universalLevel(Holder<Enchantment> holder, ItemStack itemStack, Operation<Integer> original) {
        int value = original.call(holder, itemStack);
        return value > 0 ? CRUtil.getFunctionalLevel(holder, itemStack.is(CRItemTags.STRONG)) : value;
    }

    @WrapOperation(
            method = "runIterationOnItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentVisitor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentVisitor;accept(Lnet/minecraft/core/Holder;I)V"
            )
    )
    private static void recomposed$enchantmentStrength(EnchantmentHelper.EnchantmentVisitor instance, Holder<Enchantment> entry, int i, Operation<Void> original, ItemStack itemStack) {
        original.call(instance, entry, CRUtil.getFunctionalLevel(entry, itemStack.is(CRItemTags.STRONG)));
    }

    @WrapOperation(
            method = "runIterationOnItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentInSlotVisitor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentInSlotVisitor;accept(Lnet/minecraft/core/Holder;ILnet/minecraft/world/item/enchantment/EnchantedItemInUse;)V"
            )
    )
    private static void recomposed$enchantmentStrength(EnchantmentHelper.EnchantmentInSlotVisitor instance, Holder<Enchantment> entry, int i, EnchantedItemInUse enchantmentEffectContext, Operation<Void> original, ItemStack itemStack) {
        original.call(instance, entry, CRUtil.getFunctionalLevel(entry, itemStack.is(CRItemTags.STRONG)), enchantmentEffectContext);
    }
}
