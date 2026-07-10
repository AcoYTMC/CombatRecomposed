package net.acoyt.recomposed.mixin.enchants.disable.holder;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

/**
 * @author AcoYT
 */
@Mixin(HolderGetter.class)
public interface HolderGetterMixin {
    @WrapMethod(method = "get(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;")
    private <T> Optional<Holder.Reference<T>> recomposed$nullFixHopefully(ResourceKey<T> key, Operation<Optional<Holder.Reference<T>>> original) {
        return CRUtil.isDisabled(key)
                ? Optional.empty()
                : original.call(key);
    }
}
