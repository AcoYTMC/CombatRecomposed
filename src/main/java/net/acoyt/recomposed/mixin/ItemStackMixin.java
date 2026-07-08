package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.index.CRItems;
import net.acoyt.recomposed.impl.index.tag.CRItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract boolean is(Item item);

    @ModifyReturnValue(method = "getDamageValue", at = @At("RETURN"))
    private int recomposed$noDurability(int original) {
        ItemStack stack = (ItemStack)(Object)this;
        return !stack.isEmpty() && !stack.is(CRItemTags.HAS_DURABILITY) ? 0 : original;
    }

    @ModifyReturnValue(method = "isDamageableItem", at = @At("RETURN"))
    private boolean recomposed$noDurability(boolean original) {
        ItemStack stack = (ItemStack)(Object)this;
        return original && stack.is(CRItemTags.HAS_DURABILITY);
    }

    @WrapMethod(method = "isEnchantable")
    private boolean recomposed$unenchantableMace(Operation<Boolean> original) {
        return original.call() && !this.is(Items.MACE);
    }

    @WrapMethod(method = "canBeHurtBy")
    private boolean recomposed$immortalTrinkets(DamageSource source, Operation<Boolean> original) {
        if (this.is(CRItems.WIND_CHIME) || this.is(CRItems.LIFE_VEST)) {
            return false;
        }

        return original.call(source);
    }
}
