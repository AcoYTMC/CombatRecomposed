package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.index.CRItems;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/ItemEntity;discard()V",
                    ordinal = 1
            )
    )
    private void recomposed$transformIntoSapphireCrystal(ItemEntity instance, Operation<Void> original) {
        Level level = instance.level();
        FluidState posState = level.getFluidState(instance.blockPosition());
        FluidState downState = level.getFluidState(instance.blockPosition().below());
        ItemStack stack = instance.getItem();
        Vec3 pos = instance.position();

        boolean isInWater = posState.is(FluidTags.WATER) || downState.is(FluidTags.WATER) || instance.isInWaterRainOrBubble();

        if (stack.is(Items.HEART_OF_THE_SEA) && isInWater && stack.getCount() == 1) {
            Containers.dropItemStack(level, pos.x, pos.y, pos.z, CRItems.SAPPHIRE_CRYSTAL.getDefaultInstance());
        }

        original.call(instance);
    }
}
