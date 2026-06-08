package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

/**
 * @author AcoYT
 */
@Mixin(Registry.class)
public interface RegistryMixin<T> {
    @Shadow RegistryKey<? extends Registry<T>> getKey();
    @Shadow Optional<RegistryEntry.Reference<T>> getEntry(RegistryKey<T> key);

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "unchecked"})
    @ModifyExpressionValue(
            method = "entryOf",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/registry/Registry;getEntry(Lnet/minecraft/registry/RegistryKey;)Ljava/util/Optional;"
            )
    )
    private Optional<RegistryEntry.Reference<T>> recomposed$disabled(Optional<RegistryEntry.Reference<T>> original) {
        if (original.isEmpty() && this.getKey().equals(RegistryKeys.ENCHANTMENT)) {
            return this.getEntry((RegistryKey<T>) Recomposed.EMPTY_KEY);
        }

        return original;
    }
}
