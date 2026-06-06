package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

/**
 * @author AcoYT
 */
@Mixin(ItemGroups.class)
public abstract class ItemGroupsMixin {
    @ModifyExpressionValue(
            method = {
                    "addAllLevelEnchantedBooks",
                    "addMaxLevelEnchantedBooks"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/registry/RegistryWrapper;streamEntries()Ljava/util/stream/Stream;"
            )
    )
    private static Stream<RegistryEntry.Reference<Enchantment>> recomposed$removeIfDisabled(Stream<RegistryEntry.Reference<Enchantment>> original) {
        return original.filter(reference -> !reference.matchesKey(Recomposed.EMPTY_KEY));
    }
}
