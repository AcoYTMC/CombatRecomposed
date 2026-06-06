package net.acoyt.recomposed.mixin.enchants.disable;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(SetEnchantmentsLootFunction.Builder.class)
public abstract class SetEnchantmentsLootFunctionMixin {
    @ModifyExpressionValue(
            method = "build",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/loot/function/SetEnchantmentsLootFunction$Builder;enchantments:Lcom/google/common/collect/ImmutableMap$Builder;"
            )
    )
    private static ImmutableMap.Builder<RegistryEntry<Enchantment>, LootNumberProvider> recomposed$buildWithoutDisabled(ImmutableMap.Builder<RegistryEntry<Enchantment>, LootNumberProvider> original) {
        ImmutableMap.Builder<RegistryEntry<Enchantment>, LootNumberProvider> filtered = ImmutableMap.builder();
        original.build().forEach((enchantment, provider) -> {
            if (!CRUtil.isDisabled(enchantment)) filtered.put(enchantment, provider);
        });

        return filtered;
    }
}
