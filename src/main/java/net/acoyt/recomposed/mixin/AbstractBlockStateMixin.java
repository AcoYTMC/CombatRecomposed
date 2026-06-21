package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Unique private static final VoxelShape OFFSET = Block.createCuboidShape(0, 0, 0, 16, 20, 16);

    @Shadow public abstract FluidState getFluidState();
    @Shadow public abstract Block getBlock();

    @ModifyReturnValue(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("RETURN"))
    private VoxelShape recomposed$fluidWalking(VoxelShape original, BlockView world, BlockPos pos, ShapeContext context) {
        if (original.isEmpty() && !getFluidState().isEmpty() && !(getBlock() instanceof FluidBlock) && context instanceof EntityShapeContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity instanceof PlayerEntity player && !player.isSubmergedInWater() && MathHelper.fractionalPart(entity.getY()) >= 0.5 && !LifeVestItem.getWorn(player).isEmpty() && world.getFluidState(pos.up(MathHelper.ceil(entity.getHeight()) - 1)).isEmpty()) {
                return OFFSET;
            }
        }

        return original;
    }
}
