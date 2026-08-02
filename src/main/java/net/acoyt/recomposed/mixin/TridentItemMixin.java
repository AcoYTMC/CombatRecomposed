package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.component.ChargesComponent;
import net.acoyt.recomposed.impl.item.SapphireCrystalItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(TridentItem.class)
public abstract class TridentItemMixin extends Item {
    public TridentItemMixin(Properties properties) {
        super(properties);
    }

    @WrapOperation(
            method = {"releaseUsing", "use"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"
            )
    )
    private boolean recomposed$allowRiptideWithSapphire(Player instance, Operation<Boolean> original) {
        ItemStack stack = SapphireCrystalItem.getWorn(instance);
        if (!stack.isEmpty() && ChargesComponent.get(stack).charges() > 0) {
            return true;
        }

        return original.call(instance);
    }

    @WrapOperation(
            method = "releaseUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;startAutoSpinAttack(IFLnet/minecraft/world/item/ItemStack;)V"
            )
    )
    private void recomposed$decrementChargesAfterUse(Player instance, int i, float f, ItemStack itemStack, Operation<Void> original) {
        original.call(instance, i, f, itemStack);

        Level level = instance.level();
        ItemStack stack = SapphireCrystalItem.getWorn(instance);
        if (!stack.isEmpty() && ChargesComponent.get(stack).charges() > 0 && !instance.isInWaterOrRain()) {
            ChargesComponent.getModifiable(stack, true).increment(-1).apply();
            instance.playSound(SoundEvents.BOTTLE_EMPTY, 1.0F, 1.0F);
            if (level.dimensionType().ultraWarm()) {
                instance.playSound(SoundEvents.FIRE_EXTINGUISH, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
            }
        }
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        ItemStack stack = SapphireCrystalItem.getWorn(itemStack.getEntityRepresentation());
        if (!stack.isEmpty()) {
            return ChargesComponent.get(stack).charges() > 0;
        }

        return super.isBarVisible(itemStack);
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        ItemStack stack = SapphireCrystalItem.getWorn(itemStack.getEntityRepresentation());
        if (!stack.isEmpty()) {
            ChargesComponent component = ChargesComponent.get(stack);
            return Mth.clamp(Math.round((float) component.charges() / component.maxCharges() * 13), 0, 13);
        }

        return super.getBarWidth(itemStack);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        ItemStack stack = SapphireCrystalItem.getWorn(itemStack.getEntityRepresentation());
        if (!stack.isEmpty()) {
            return 0xFF476793;
        }

        return super.getBarColor(itemStack);
    }
}
