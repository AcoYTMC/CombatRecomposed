package net.acoyt.recomposed.mixin.enchants.disable.internal;

import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
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
@Mixin(Holder.Reference.class)
public abstract class HolderReferenceMixin<T> {
    @Shadow @Final private HolderOwner<T> owner;

    @Shadow private @Nullable ResourceKey<T> key;
    @Shadow private @Nullable T value;

    @Inject(method = "isBound", at = @At("HEAD"))
    private void recomposed$handleKeyAndValue(CallbackInfoReturnable<Boolean> cir) {
        validate();
    }

    @Inject(method = "key", at = @At("HEAD"))
    private void recomposed$handleKey(CallbackInfoReturnable<ResourceKey<T>> cir) {
        validate();
    }

    @Inject(method = "value", at = @At("HEAD"))
    private void recomposed$handleValue(CallbackInfoReturnable<T> cir) {
        validate();
    }

    @SuppressWarnings("unchecked")
    @Unique
    private void validate() {
        if (CRUtil.ENCHANTMENT_REGISTRY_OWNER != null && owner.canSerializeIn((HolderOwner<T>) CRUtil.ENCHANTMENT_REGISTRY_OWNER)) {
            if (key == null || value == null) {
                key = (ResourceKey<T>) Recomposed.EMPTY_KEY;
                value = (T) Recomposed.EMPTY;
            }
        }
    }
}
