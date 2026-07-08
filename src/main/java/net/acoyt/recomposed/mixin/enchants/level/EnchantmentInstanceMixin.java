package net.acoyt.recomposed.mixin.enchants.level;

import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * @author AcoYT
 */
@Mixin(EnchantmentInstance.class)
public abstract class EnchantmentInstanceMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static int recomposed$universalLevel(int level) {
        return 1;
    }
}
