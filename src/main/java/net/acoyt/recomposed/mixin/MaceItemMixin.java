package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(MaceItem.class)
public abstract class MaceItemMixin extends Item {
    public MaceItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyReturnValue(method = "getBonusAttackDamage", at = @At("RETURN"))
    private float recomposed$maceDamageDebuff(float original) {
        return original * 0.75F;
    }
}
