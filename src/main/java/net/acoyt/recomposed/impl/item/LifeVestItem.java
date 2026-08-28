package net.acoyt.recomposed.impl.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.acoyt.recomposed.impl.index.CRItems;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * @author AcoYT
 */
public class LifeVestItem extends Item implements Trinket {
    public LifeVestItem(Properties properties) {
        super(properties);
    }

    public Holder<SoundEvent> getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return SoundEvents.ARMOR_EQUIP_GENERIC;
    }

    public static ItemStack getWorn(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return ItemStack.EMPTY;
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(living);
        if (component.isEmpty()) return ItemStack.EMPTY;

        TrinketComponent trinkets = component.get();
        for (Tuple<SlotReference, ItemStack> pair : trinkets.getEquipped(stack -> stack.is(CRItems.LIFE_VEST))) {
            if (pair.getA().inventory().getSlotType().getName().equals("trinket") && pair.getB().is(CRItems.LIFE_VEST)) {
                return pair.getB();
            }
        }

        return ItemStack.EMPTY;
    }
}
