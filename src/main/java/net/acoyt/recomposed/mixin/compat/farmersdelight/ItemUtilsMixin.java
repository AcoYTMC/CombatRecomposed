package net.acoyt.recomposed.mixin.compat.farmersdelight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.Optional;

/**
 * @author AcoYT
 */
@Mixin(ItemUtils.class)
public abstract class ItemUtilsMixin {
    @WrapOperation(
            method = "getValidatedEnchantmentLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/HolderLookup$RegistryLookup;get(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;"
            )
    )
    private static Optional<Enchantment> recomposed$preventCrashWhenChopping(HolderLookup.RegistryLookup<Enchantment> instance, ResourceKey<Enchantment> resourceKey, Operation<Optional<Enchantment>> original) {
        return CRUtil.isDisabled(resourceKey) ? Optional.empty() : original.call(instance, resourceKey);
    }
}
