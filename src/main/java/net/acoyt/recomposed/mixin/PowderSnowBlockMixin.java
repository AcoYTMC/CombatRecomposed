package net.acoyt.recomposed.mixin;

import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin {
//    @WrapMethod(method = "canEntityWalkOnPowderSnow")
//    private static boolean recomposed$canWalkOnSnow(Entity entity, Operation<Boolean> original) {
//        return original.call(entity) || !LifeVestItem.getWorn(entity).isEmpty();
//    }
}
