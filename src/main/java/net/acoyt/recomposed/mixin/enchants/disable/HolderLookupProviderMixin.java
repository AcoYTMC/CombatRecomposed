package net.acoyt.recomposed.mixin.enchants.disable;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

/**
 * @author AcoYT
 */
@Mixin(HolderGetter.Provider.class)
public interface HolderLookupProviderMixin {
    @WrapMethod(method = "get")
    private <T> Optional<Holder.Reference<T>> recomposed$nullFixHopefully(ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> objectKey, Operation<Optional<Holder.Reference<T>>> original) {
        return registryKey.equals(Registries.ENCHANTMENT) && CRUtil.isDisabled(objectKey)
                ? Optional.empty()
                : original.call(registryKey, objectKey);
    }
}
