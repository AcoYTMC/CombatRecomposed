package net.acoyt.recomposed.impl.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.acoyt.recomposed.impl.index.CRDataComponents;
import net.acoyt.recomposed.impl.index.CRItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
public class WindChimeItem extends Item implements Trinket {
    public WindChimeItem(Settings settings) {
        super(settings.component(CRDataComponents.JUMPS, 2));
    }

    public void tryDecrement(ItemStack stack, PlayerEntity player) {
    }

    public RegistryEntry<SoundEvent> getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    public static ItemStack getWorn(LivingEntity living) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(living);
        if (component.isEmpty()) return ItemStack.EMPTY;

        TrinketComponent trinkets = component.get();
        for (Pair<SlotReference, ItemStack> pair : trinkets.getEquipped(stack -> stack.isOf(CRItems.WIND_CHIME))) {
            if (pair.getLeft().inventory().getSlotType().getName().equals("trinket") && pair.getRight().isOf(CRItems.WIND_CHIME)) {
                return pair.getRight();
            }
        }

        return ItemStack.EMPTY;
    }
}
