package net.acoyt.recomposed.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WindChargeItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author AcoYT
 */
@Mixin(WindChargeItem.class)
public abstract class WindChargeItemMixin extends Item {
    public WindChargeItemMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "use", at = @At("HEAD"))
    private void recomposed$maceCooldown(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        player.getCooldowns().addCooldown(Items.MACE, 40);
    }
}
