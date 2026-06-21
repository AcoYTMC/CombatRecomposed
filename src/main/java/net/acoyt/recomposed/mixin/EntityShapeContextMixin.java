package net.acoyt.recomposed.mixin;

import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

/**
 * @author AcoYT
 */
@Mixin(EntityShapeContext.class)
public class EntityShapeContextMixin {
    @Shadow @Final @Mutable private Predicate<FluidState> walkOnFluidPredicate;

    @Inject(method = "<init>(ZDLnet/minecraft/item/ItemStack;Ljava/util/function/Predicate;Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    protected void recomposed$fluidWalking(boolean descending, double minY, ItemStack heldItem, Predicate<FluidState> walkOnFluidPredicate, Entity entity, CallbackInfo ci) {
        this.walkOnFluidPredicate = this.walkOnFluidPredicate.or(state -> entity instanceof PlayerEntity player && !LifeVestItem.getWorn(player).isEmpty());
    }
}
