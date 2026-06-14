package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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

    @WrapMethod(method = "shouldDealAdditionalDamage")
    private static boolean recomposed$noSlamIfCooldown(LivingEntity attacker, Operation<Boolean> original) {
        if (attacker instanceof PlayerEntity player && player.getItemCooldownManager().isCoolingDown(Items.MACE)) {
            return false;
        }

        return original.call(attacker);
    }
}
