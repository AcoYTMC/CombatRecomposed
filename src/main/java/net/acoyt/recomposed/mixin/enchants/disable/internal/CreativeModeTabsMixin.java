package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.core.Holder;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

/**
 * @author AcoYT
 */
@Mixin(CreativeModeTabs.class)
public abstract class CreativeModeTabsMixin {
    @ModifyExpressionValue(
            method = {
                    "generateEnchantmentBookTypesAllLevels",
                    "generateEnchantmentBookTypesOnlyMaxLevel"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/HolderLookup;listElements()Ljava/util/stream/Stream;"
            )
    )
    private static Stream<Holder.Reference<Enchantment>> recomposed$removeIfDisabled(Stream<Holder.Reference<Enchantment>> original) {
        return original.filter(reference -> !reference.is(Recomposed.EMPTY_KEY));
    }
}
