package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

/**
 * @author AcoYT
 */
@Mixin(ResourceArgument.class)
public abstract class ResourceArgumentMixin<T> {
    @Shadow @Final public static Dynamic2CommandExceptionType ERROR_UNKNOWN_RESOURCE;

    @Shadow @Final ResourceKey<? extends Registry<T>> registryKey;

    @ModifyReturnValue(method = "getEnchantment", at = @At("RETURN"))
    private static Holder.Reference<Enchantment> recomposed$disable(Holder.Reference<Enchantment> original) throws CommandSyntaxException {
        if (original.is(Recomposed.EMPTY_KEY)) {
            throw ERROR_UNKNOWN_RESOURCE.create(original.key().location(), original.key().registry());
        }

        return original;
    }

    @ModifyExpressionValue(
            method = "listSuggestions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/HolderLookup;listElementIds()Ljava/util/stream/Stream;"
            )
    )
    private <S> Stream<ResourceKey<S>> recomposed$disable(Stream<ResourceKey<S>> original) {
        if (registryKey.equals(Registries.ENCHANTMENT)) {
            return original.filter(key -> !key.equals(Recomposed.EMPTY_KEY));
        }

        return original;
    }
}
