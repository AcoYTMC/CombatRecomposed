package net.acoyt.recomposed.mixin;

import net.minecraft.world.phys.shapes.EntityCollisionContext;
import org.spongepowered.asm.mixin.Mixin;

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
