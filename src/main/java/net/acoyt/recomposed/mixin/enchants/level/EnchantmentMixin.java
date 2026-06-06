package net.acoyt.recomposed.mixin.enchants.level;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @ModifyReturnValue(method = "getMaxLevel", at = @At("RETURN"))
    private int recomposed$universalLevel(int original) {
        Enchantment enchantment = (Enchantment)(Object)this;
        if (!CRUtil.MAX_LEVELS.containsKey(enchantment)) {
            CRUtil.MAX_LEVELS.put(enchantment, original);
        }

        return 1;
    }
}
