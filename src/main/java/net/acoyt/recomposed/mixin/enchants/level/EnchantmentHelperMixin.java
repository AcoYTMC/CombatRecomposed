package net.acoyt.recomposed.mixin.enchants.level;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * @author AcoYT
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @WrapOperation(
            method = "apply",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/component/type/ItemEnchantmentsComponent$Builder;build()Lnet/minecraft/component/type/ItemEnchantmentsComponent;"
            )
    )
    private static ItemEnchantmentsComponent recomposed$universalLevel(ItemEnchantmentsComponent.Builder instance, Operation<ItemEnchantmentsComponent> original) {
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
        original.call(instance).getEnchantments().forEach(enchantment -> builder.add(enchantment, 1));
        return builder.build();
    }

    @ModifyVariable(method = "set", at = @At("HEAD"), argsOnly = true)
    private static ItemEnchantmentsComponent recomposed$universalLevel(ItemEnchantmentsComponent enchantments, ItemStack stack) {
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(enchantments);
        enchantments.getEnchantments().forEach(enchantment -> builder.set(enchantment, 1));
        return builder.build();
    }

    @WrapMethod(method = "getLevel")
    private static int recomposed$universalLevel(RegistryEntry<Enchantment> enchantment, ItemStack stack, Operation<Integer> original) {
        int value = original.call(enchantment, stack);
        return value > 0 ? CRUtil.getFunctionalLevel(enchantment) : value;
    }

    @WrapOperation(
            method = "forEachEnchantment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/enchantment/EnchantmentHelper$Consumer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper$Consumer;accept(Lnet/minecraft/registry/entry/RegistryEntry;I)V"
            )
    )
    private static void recomposed$enchantmentStrength(EnchantmentHelper.Consumer instance, RegistryEntry<Enchantment> entry, int i, Operation<Void> original) {
        original.call(instance, entry, CRUtil.getFunctionalLevel(entry));
    }

    @WrapOperation(
            method = "forEachEnchantment(Lnet/minecraft/item/ItemStack;" +
                    "Lnet/minecraft/entity/EquipmentSlot;" +
                    "Lnet/minecraft/entity/LivingEntity;" +
                    "Lnet/minecraft/enchantment/EnchantmentHelper$ContextAwareConsumer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper$ContextAwareConsumer;accept(Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/enchantment/EnchantmentEffectContext;)V"
            )
    )
    private static void recomposed$enchantmentStrength(EnchantmentHelper.ContextAwareConsumer instance, RegistryEntry<Enchantment> entry, int i, EnchantmentEffectContext enchantmentEffectContext, Operation<Void> original) {
        original.call(instance, entry, CRUtil.getFunctionalLevel(entry), enchantmentEffectContext);
    }
}
