package net.acoyt.recomposed.mixin.villagers;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.npc.AbstractVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin {
    @ModifyReturnValue(method = "canBeLeashed", at = @At("RETURN"))
    private boolean recomposed$leashableVillagers(boolean original) {
        return true;
    }
}
