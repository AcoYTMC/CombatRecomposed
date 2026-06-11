package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.recomposed.api.ItemMaxCountEvent;
import net.acoyt.recomposed.impl.cca.entity.WindChimeComponent;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author AcoYT
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract boolean isCreative();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void recomposed$dropExcess(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if (!this.isCreative()) {
            List<Item> limited = new ArrayList<>();
            Registries.ITEM.forEach(item -> {
                if (ItemMaxCountEvent.EVENT.invoker().getMaxCount(player, item.getDefaultStack()).isPresent()) {
                    limited.add(item);
                }
            });

            for (Item item : limited) {
                Optional<Integer> maxCount = ItemMaxCountEvent.EVENT.invoker().getMaxCount(player, item.getDefaultStack());

                if (maxCount.isPresent()) {
                    int max = maxCount.get();

                    int inv = CRUtil.getCountOnPlayer(player, item);
                    if (inv > max) {}
                }
            }
        }
    }

    @ModifyReturnValue(method = "getOffGroundSpeed", at = @At("RETURN"))
    private float recomposed$windChimeBunnyHopping(float original) {
        if (WindChimeComponent.KEY.get(this).getPossibleJumps() > WindChimeComponent.KEY.get(this).getRemainingJumps()) {
            return original * 1.65F;
        }

        return original;
    }
}
