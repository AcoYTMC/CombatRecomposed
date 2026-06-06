package net.acoyt.recomposed.mixin.enchants.disable.internal;

import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author AcoYT
 */
@Mixin(RegistryEntry.Reference.class)
public abstract class RegistryEntryReferenceMixin<T> {
    @Shadow @Final private RegistryEntryOwner<T> owner;

    @Shadow private @Nullable RegistryKey<T> registryKey;
    @Shadow private @Nullable T value;

    @Inject(method = "hasKeyAndValue", at = @At("HEAD"))
    private void recomposed$handleKeyAndValue(CallbackInfoReturnable<Boolean> cir) {
        validate();
    }

    @Inject(method = "registryKey", at = @At("HEAD"))
    private void recomposed$handleKey(CallbackInfoReturnable<RegistryKey<T>> cir) {
        validate();
    }

    @Inject(method = "value", at = @At("HEAD"))
    private void recomposed$handleValue(CallbackInfoReturnable<T> cir) {
        validate();
    }

    @SuppressWarnings("unchecked")
    @Unique
    private void validate() {
        if (CRUtil.ENCHANTMENT_REGISTRY_OWNER != null && owner.ownerEquals((RegistryEntryOwner<T>) CRUtil.ENCHANTMENT_REGISTRY_OWNER)) {
            if (registryKey == null || value == null) {
                registryKey = (RegistryKey<T>) Recomposed.EMPTY_KEY;
                value = (T) Recomposed.EMPTY;
            }
        }
    }
}
