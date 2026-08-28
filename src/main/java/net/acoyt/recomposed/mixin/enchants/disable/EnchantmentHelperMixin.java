package net.acoyt.recomposed.mixin.enchants.disable;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author AcoYT
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @WrapMethod(method = "setEnchantments")
    private static void recomposed$removeIfDisabled(ItemStack itemStack, ItemEnchantments itemEnchantments, Operation<Void> original) {
        ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        itemEnchantments.keySet().forEach(enchantment -> {
            int level = itemEnchantments.getLevel(enchantment);
            if (!CRUtil.isDisabled(enchantment)) {
                builder.upgrade(enchantment, level);
            }
        });

        original.call(itemStack, builder.toImmutable());
    }

    @WrapOperation(
            method = "selectEnchantment",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getAvailableEnchantmentResults(ILnet/minecraft/world/item/ItemStack;Ljava/util/stream/Stream;)Ljava/util/List;"
            )
    )
    private static List<EnchantmentInstance> recomposed$removePossibilities(int i, ItemStack itemStack, Stream<Holder<Enchantment>> stream, Operation<List<EnchantmentInstance>> original) {
        List<EnchantmentInstance> entries = original.call(i, itemStack, stream);
        entries.removeIf(entry -> CRUtil.isDisabled(entry.enchantment));
        return entries;
    }

    @WrapMethod(method = "getItemEnchantmentLevel")
    private static int recomposed$zeroIfNullHolder(Holder<Enchantment> holder, ItemStack itemStack, Operation<Integer> original) {
        return holder == null ? 0 : original.call(holder, itemStack);
    }
}
