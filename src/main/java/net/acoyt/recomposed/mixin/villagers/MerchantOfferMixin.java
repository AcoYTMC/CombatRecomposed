package net.acoyt.recomposed.mixin.villagers;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author AcoYT
 */
@Mixin(MerchantOffer.class)
public abstract class MerchantOfferMixin {
    @Shadow private int uses;

    @Shadow @Final private int maxUses;

    @WrapMethod(method = "increaseUses")
    private void recomposed$dontIncreaseCount(Operation<Void> original) {
        if (this.uses >= this.maxUses) {
            this.uses = this.maxUses;
            return;
        }

        original.call();
    }

    @WrapMethod(method = "isOutOfStock")
    public boolean recomposed$preventRunningOutOfStock(Operation<Boolean> original) {
        return false;
    }
}
