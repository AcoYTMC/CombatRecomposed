package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin {
    @WrapMethod(method = "canEntityWalkOnPowderSnow")
    private static boolean recomposed$canWalkOnSnow(Entity entity, Operation<Boolean> original) {
        return original.call(entity) || !LifeVestItem.getWorn(entity).isEmpty();
    }
}
