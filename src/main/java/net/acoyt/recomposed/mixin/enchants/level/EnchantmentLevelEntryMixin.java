package net.acoyt.recomposed.mixin.enchants.level;

import net.minecraft.enchantment.EnchantmentLevelEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * @author AcoYT
 */
@Mixin(EnchantmentLevelEntry.class)
public abstract class EnchantmentLevelEntryMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static int recomposed$universalLevel(int value) {
        return 1;
    }
}
