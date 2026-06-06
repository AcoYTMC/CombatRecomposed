package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Lifecycle;
import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author AcoYT
 */
@Mixin(SimpleRegistry.class)
public abstract class SimpleRegistryMixin<T> {
    @Shadow public abstract int getRawId(@Nullable T value);
    @Shadow public abstract @Nullable T get(@Nullable RegistryKey<T> key);

    @Shadow @Final RegistryKey<? extends Registry<T>> key;

    @Shadow public abstract RegistryEntryOwner<T> getEntryOwner();

    @Inject(method = "<init>(Lnet/minecraft/registry/RegistryKey;Lcom/mojang/serialization/Lifecycle;Z)V", at = @At("TAIL"))
    private void recomposed$setup(RegistryKey<T> key, Lifecycle lifecycle, boolean intrusive, CallbackInfo ci) {
        if (key.equals(RegistryKeys.ENCHANTMENT)) {
            CRUtil.ENCHANTMENT_REGISTRY_OWNER = getEntryOwner();
        }
    }

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "getRawId", at = @At("RETURN"))
    private int recomposed$disabled(int original) {
        if (original == -1 && key.equals(RegistryKeys.ENCHANTMENT)) {
            return getRawId(get((RegistryKey<T>) Recomposed.EMPTY_KEY));
        }

        return original;
    }
}
