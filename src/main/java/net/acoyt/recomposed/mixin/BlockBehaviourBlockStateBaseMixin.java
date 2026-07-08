package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockBehaviourBlockStateBaseMixin {
    @Unique private static final VoxelShape OFFSET = Block.box(0, 0, 0, 16, 20, 16);

    @Shadow public abstract FluidState getFluidState();
    @Shadow public abstract Block getBlock();

    @ModifyReturnValue(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"))
    private VoxelShape recomposed$fluidWalking(VoxelShape original, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (original.isEmpty() && !getFluidState().isEmpty() && !(getBlock() instanceof LiquidBlock) && context instanceof EntityCollisionContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity instanceof Player player && !player.isUnderWater() && Mth.frac(entity.getY()) >= 0.5 && !LifeVestItem.getWorn(player).isEmpty() && world.getFluidState(pos.above(Mth.ceil(entity.getBbHeight()) - 1)).isEmpty()) {
                return OFFSET;
            }
        }

        return original;
    }
}
