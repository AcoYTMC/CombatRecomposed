package net.acoyt.recomposed.mixin.enchants.disable;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(SetEnchantmentsFunction.Builder.class)
public abstract class SetEnchantmentsFunctionBuilderMixin {
    @ModifyExpressionValue(
            method = "build",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/storage/loot/functions/SetEnchantmentsFunction$Builder;enchantments:Lcom/google/common/collect/ImmutableMap$Builder;"
            )
    )
    private static ImmutableMap.Builder<Holder<Enchantment>, NumberProvider> recomposed$buildWithoutDisabled(ImmutableMap.Builder<Holder<Enchantment>, NumberProvider> original) {
        ImmutableMap.Builder<Holder<Enchantment>, NumberProvider> filtered = ImmutableMap.builder();
        original.build().forEach((enchantment, provider) -> {
            if (!CRUtil.isDisabled(enchantment)) filtered.put(enchantment, provider);
        });

        return filtered;
    }
}
