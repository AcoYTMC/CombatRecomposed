package net.acoyt.recomposed.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockBehaviourBlockStateBaseMixin {
//    @Unique private static final VoxelShape OFFSET = Block.box(0, 0, 0, 16, 20, 16);
//
//    @Shadow public abstract FluidState getFluidState();
//    @Shadow public abstract Block getBlock();
//
//    @ModifyReturnValue(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"))
//    private VoxelShape recomposed$fluidWalking(VoxelShape original, BlockGetter world, BlockPos pos, CollisionContext context) {
//        if (original.isEmpty() && !getFluidState().isEmpty() && !(getBlock() instanceof LiquidBlock) && context instanceof EntityCollisionContext entityShapeContext) {
//            Entity entity = entityShapeContext.getEntity();
//            if (entity instanceof Player player && !player.isUnderWater() && Mth.frac(entity.getY()) >= 0.5 && !LifeVestItem.getWorn(player).isEmpty() && world.getFluidState(pos.above(Mth.ceil(entity.getBbHeight()) - 1)).isEmpty()) {
//                return OFFSET;
//            }
//        }
//
//        return original;
//    }
}
