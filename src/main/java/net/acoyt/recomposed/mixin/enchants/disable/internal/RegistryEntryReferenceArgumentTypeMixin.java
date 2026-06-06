package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

/**
 * @author AcoYT
 */
@Mixin(RegistryEntryReferenceArgumentType.class)
public abstract class RegistryEntryReferenceArgumentTypeMixin<T> {
    @Shadow @Final public static Dynamic2CommandExceptionType NOT_FOUND_EXCEPTION;

    @Shadow @Final RegistryKey<? extends Registry<T>> registryRef;

    @ModifyReturnValue(method = "getEnchantment", at = @At("RETURN"))
    private static RegistryEntry.Reference<Enchantment> recomposed$disable(RegistryEntry.Reference<Enchantment> original) throws CommandSyntaxException {
        if (original.matchesKey(Recomposed.EMPTY_KEY)) {
            throw NOT_FOUND_EXCEPTION.create(original.registryKey().getValue(), original.registryKey().getRegistry());
        }

        return original;
    }

    @ModifyExpressionValue(
            method = "listSuggestions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/registry/RegistryWrapper;streamKeys()Ljava/util/stream/Stream;"
            )
    )
    private <S> Stream<RegistryKey<S>> recomposed$disable(Stream<RegistryKey<S>> original) {
        if (registryRef.equals(RegistryKeys.ENCHANTMENT)) {
            return original.filter(key -> !key.equals(Recomposed.EMPTY_KEY));
        }

        return original;
    }
}
