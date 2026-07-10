package net.acoyt.recomposed.mixin;

import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
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
@Mixin(EntityCollisionContext.class)
public class EntityCollisionContextMixin {
//    @Shadow @Final @Mutable private Predicate<FluidState> canStandOnFluid;
//
//    @Inject(method = "<init>(ZDLnet/minecraft/world/item/ItemStack;Ljava/util/function/Predicate;Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
//    protected void recomposed$fluidWalking(boolean descending, double minY, ItemStack heldItem, Predicate<FluidState> walkOnFluidPredicate, Entity entity, CallbackInfo ci) {
//        this.canStandOnFluid = this.canStandOnFluid.or(state -> entity instanceof Player player && !LifeVestItem.getWorn(player).isEmpty());
//    }
}
