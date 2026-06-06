package net.acoyt.recomposed.mixin.enchants.disable;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author AcoYT
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @WrapMethod(method = "set")
    private static void recomposed$removeIfDisabled(ItemStack stack, ItemEnchantmentsComponent enchantments, Operation<Void> original) {
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
        enchantments.getEnchantments().forEach(enchantment -> {
            int level = enchantments.getLevel(enchantment);
            if (!CRUtil.isDisabled(enchantment)) {
                builder.add(enchantment, level);
            }
        });

        original.call(stack, builder.build());
    }

    @WrapOperation(
            method = "generateEnchantments",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;getPossibleEntries(ILnet/minecraft/item/ItemStack;Ljava/util/stream/Stream;)Ljava/util/List;"
            )
    )
    private static List<EnchantmentLevelEntry> recomposed$removePossibilities(int level, ItemStack stack, Stream<RegistryEntry<Enchantment>> possibleEnchantments, Operation<List<EnchantmentLevelEntry>> original) {
        List<EnchantmentLevelEntry> entries = original.call(level, stack, possibleEnchantments);
        entries.removeIf(entry -> CRUtil.isDisabled(entry.enchantment));
        return entries;
    }
}
