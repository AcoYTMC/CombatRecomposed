package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

/**
 * @author AcoYT
 */
@Mixin(Registry.class)
public interface RegistryMixin<T> {
    @Shadow ResourceKey<? extends Registry<T>> key();
    @Shadow Optional<Holder.Reference<T>> getHolder(ResourceKey<T> key);

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "unchecked"})
    @ModifyExpressionValue(
            method = "getHolderOrThrow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/Registry;getHolder(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;"
            )
    )
    private Optional<Holder.Reference<T>> recomposed$disabled(Optional<Holder.Reference<T>> original) {
        if (original.isEmpty() && this.key().equals(Registries.ENCHANTMENT)) {
            return this.getHolder((ResourceKey<T>) Recomposed.EMPTY_KEY);
        }

        return original;
    }
}
