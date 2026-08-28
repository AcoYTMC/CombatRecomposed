package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Lifecycle;
import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
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
@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin<T> {
    @Shadow public abstract int getId(@Nullable T object);
    @Shadow public abstract @Nullable T get(@Nullable ResourceKey<T> resourceKey);

    @Shadow @Final ResourceKey<? extends Registry<T>> key;

    @Shadow public abstract HolderOwner<T> holderOwner();

    @Inject(method = "<init>(Lnet/minecraft/resources/ResourceKey;Lcom/mojang/serialization/Lifecycle;Z)V", at = @At("TAIL"))
    private void recomposed$setup(ResourceKey<T> resourceKey, Lifecycle lifecycle, boolean bl, CallbackInfo ci) {
        if (resourceKey.equals(Registries.ENCHANTMENT)) {
            CRUtil.ENCHANTMENT_REGISTRY_OWNER = holderOwner();
        }
    }

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "getId", at = @At("RETURN"))
    private int recomposed$disabled(int original) {
        if (original == -1 && key.equals(Registries.ENCHANTMENT)) {
            return getId(get((ResourceKey<T>) Recomposed.EMPTY_KEY));
        }

        return original;
    }
}
