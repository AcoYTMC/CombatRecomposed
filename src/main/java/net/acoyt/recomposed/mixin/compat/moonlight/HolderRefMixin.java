package net.acoyt.recomposed.mixin.compat.moonlight;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.mehvahdjukaar.moonlight.api.misc.HolderRef;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author AcoYT
 */
@Mixin(value = HolderRef.class, remap = false)
public abstract class HolderRefMixin<T> {
    @Shadow @Final private ResourceKey<Registry<T>> registryKey;
    @Shadow @Final private ResourceKey<T> key;

    @WrapMethod(method = "getHolder(Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/core/Holder;")
    private Holder<T> recomposed$nullIfEmptyEnchantment(HolderLookup.Provider r, Operation<Holder<T>> original) {
        return registryKey.equals(Registries.ENCHANTMENT) && CRUtil.isDisabled(key) ? null : original.call(r);
    }
}
