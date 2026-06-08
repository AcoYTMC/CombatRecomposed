package net.acoyt.recomposed.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(MaceItem.class)
public abstract class MaceItemMixin extends Item {
    public MaceItemMixin(Settings settings) {
        super(settings);
    }
}
