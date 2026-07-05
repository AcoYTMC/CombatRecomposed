package net.acoyt.recomposed.mixin.enchants.disable.codec;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(RegistryFixedCodec.class)
public abstract class RegistryFixedCodecMixin<E> {
    @Shadow @Final private RegistryKey<? extends Registry<E>> registry;

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "decode", at = @At("RETURN"))
    private <T> DataResult<Pair<RegistryEntry<E>, T>> recomposed$iHateEnchantments(DataResult<Pair<RegistryEntry<E>, T>> original, DynamicOps<T> ops, T input) {
        if (!original.hasResultOrPartial() && registry.equals(RegistryKeys.ENCHANTMENT) && ops instanceof RegistryOps<T> registryOps) {
            return registryOps.getEntryLookup(registry)
                    .flatMap(lookup -> lookup.getOptional((RegistryKey<E>) Recomposed.EMPTY_KEY))
                    .map(entry -> DataResult.success(Pair.of((RegistryEntry<E>) entry, input)))
                    .orElseThrow();
        }

        return original;
    }
}
