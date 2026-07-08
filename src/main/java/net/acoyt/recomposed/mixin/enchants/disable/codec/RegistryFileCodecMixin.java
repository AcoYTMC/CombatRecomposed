package net.acoyt.recomposed.mixin.enchants.disable.codec;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(RegistryFileCodec.class)
public abstract class RegistryFileCodecMixin<E> {
    @Shadow @Final private ResourceKey<? extends Registry<E>> registryKey;

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "decode", at = @At("RETURN"))
    private <T> DataResult<Pair<Holder<E>, T>> recomposed$killMePleaseee(DataResult<Pair<Holder<E>, T>> original, DynamicOps<T> ops, T input) {
        if (!original.hasResultOrPartial() && registryKey.equals(Registries.ENCHANTMENT) && ops instanceof RegistryOps<T> registryOps) {
            return registryOps.getter(registryKey)
                    .flatMap(lookup -> lookup.get((ResourceKey<E>) Recomposed.EMPTY_KEY))
                    .map(entry -> DataResult.success(Pair.of((Holder<E>) entry, input)))
                    .orElseThrow();
        }

        return original;
    }
}
