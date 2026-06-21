package net.acoyt.recomposed.impl.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.acoyt.recomposed.impl.index.CRDataComponents;
import net.acoyt.recomposed.impl.index.CRItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;

import java.util.Optional;

/**
 * @author AcoYT
 */
public class LifeVestItem extends Item implements Trinket {
    public LifeVestItem(Settings settings) {
        super(settings.component(CRDataComponents.IMMORTAL, true));
    }

    public RegistryEntry<SoundEvent> getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    public static ItemStack getWorn(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return ItemStack.EMPTY;
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(living);
        if (component.isEmpty()) return ItemStack.EMPTY;

        TrinketComponent trinkets = component.get();
        for (Pair<SlotReference, ItemStack> pair : trinkets.getEquipped(stack -> stack.isOf(CRItems.LIFE_VEST))) {
            if (pair.getLeft().inventory().getSlotType().getName().equals("trinket") && pair.getRight().isOf(CRItems.LIFE_VEST)) {
                return pair.getRight();
            }
        }

        return ItemStack.EMPTY;
    }
}
