package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.registry.tag.TagGroupLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

/**
 * @author AcoYT
 */
@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin {
    @Shadow @Final private String dataType;

    @WrapOperation(
            method = "resolveAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/registry/tag/TagEntry;resolve(Lnet/minecraft/registry/tag/TagEntry$ValueGetter;Ljava/util/function/Consumer;)Z"
            )
    )
    private <T> boolean recomposed$disableLogErrors(TagEntry instance, TagEntry.ValueGetter<T> valueGetter, Consumer<T> idConsumer, Operation<Boolean> original) {
        return original.call(instance, valueGetter, idConsumer) || (dataType.equals("tags/enchantment") && CRUtil.isDisabled(instance.id));
    }
}
