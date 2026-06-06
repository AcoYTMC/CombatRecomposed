package net.acoyt.recomposed.mixin.enchants.disable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(EnchantRandomlyLootFunction.class)
public abstract class EnchantRandomlyLootFunctionMixin {
    @WrapOperation(
            method = "process",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Util;getRandomOrEmpty(Ljava/util/List;Lnet/minecraft/util/math/random/Random;)Ljava/util/Optional;"
            )
    )
    private <T extends RegistryEntry<Enchantment>> Optional<T> recomposed$disableEnchants(List<T> list, Random random, Operation<Optional<T>> original) {
        List<T> filtered = new ArrayList<>();
        for (T var : list) {
            if (var.getKey().isPresent() && !CRUtil.isDisabled(var.getKey().get().getValue())) {
                filtered.add(var);
            }
        }

        return original.call(filtered, random);
    }
}
