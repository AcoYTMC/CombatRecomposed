package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

/**
 * @author AcoYT
 */
@Mixin(TagLoader.class)
public abstract class TagLoaderMixin {
    @Shadow @Final private String directory;

    @WrapOperation(
            method = "build(Lnet/minecraft/tags/TagEntry$Lookup;Ljava/util/List;)Lcom/mojang/datafixers/util/Either;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/tags/TagEntry;build(Lnet/minecraft/tags/TagEntry$Lookup;Ljava/util/function/Consumer;)Z"
            )
    )
    private <T> boolean recomposed$disableLogErrors(TagEntry instance, TagEntry.Lookup<T> lookup, Consumer<T> consumer, Operation<Boolean> original) {
        return original.call(instance, lookup, consumer) || (directory.equals("tags/enchantment") && CRUtil.isDisabled(instance.id));
    }
}
