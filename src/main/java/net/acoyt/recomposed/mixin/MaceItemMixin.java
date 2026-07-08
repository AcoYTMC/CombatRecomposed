package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(MaceItem.class)
public abstract class MaceItemMixin extends Item {
    public MaceItemMixin(Properties settings) {
        super(settings);
    }

    @ModifyReturnValue(method = "getAttackDamageBonus", at = @At("RETURN"))
    private float recomposed$maceDamageDebuff(float original) {
        return original * 0.75F;
    }

    @WrapMethod(method = "canSmashAttack")
    private static boolean recomposed$noSlamIfCooldown(LivingEntity attacker, Operation<Boolean> original) {
        if (attacker instanceof Player player && player.getCooldowns().isOnCooldown(Items.MACE)) {
            return false;
        }

        return original.call(attacker);
    }
}
