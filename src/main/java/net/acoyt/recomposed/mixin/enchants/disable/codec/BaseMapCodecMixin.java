package net.acoyt.recomposed.mixin.enchants.disable.codec;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.codecs.BaseMapCodec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(BaseMapCodec.class)
public interface BaseMapCodecMixin<V> {
    @ModifyExpressionValue(
            method = "lambda$decode$3",
            at = @At(
                    value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/objects/Object2ObjectMap;putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
            ),
            remap = false
    )
    private V recomposed$dieDieDieDieDieDieDieDieDieDie(V original) {
        return null;
    }
}
