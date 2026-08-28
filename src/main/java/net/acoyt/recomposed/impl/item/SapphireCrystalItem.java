package net.acoyt.recomposed.impl.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.acoyt.recomposed.impl.component.ChargesComponent;
import net.acoyt.recomposed.impl.index.CRDataComponents;
import net.acoyt.recomposed.impl.index.CRItems;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * @author AcoYT
 */
public class SapphireCrystalItem extends Item implements Trinket {
    public SapphireCrystalItem(Properties properties) {
        super(properties.component(CRDataComponents.CHARGES, ChargesComponent.DEFAULT));
    }

    public boolean isBarVisible(ItemStack stack) {
        return ChargesComponent.get(stack).charges() > 0;
    }

    public int getBarWidth(ItemStack stack) {
        ChargesComponent component = ChargesComponent.get(stack);
        return Mth.clamp(Math.round((float) component.charges() / component.maxCharges() * 13), 0, 13);
    }

    public int getBarColor(ItemStack stack) {
        return 0xFF476793;
    }

    public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        ItemStack other = slotAccess.get();
        boolean isWaterBottle = other.is(ConventionalItemTags.BOTTLE_POTIONS) && !other.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).hasEffects();

        if (clickAction == ClickAction.SECONDARY && !ChargesComponent.get(stack).isFullyCharged() && (isWaterBottle || other.is(Items.WATER_BUCKET))) {
            ChargesComponent.getModifiable(stack, true).increment(isWaterBottle ? 2 : 5).apply();
            player.playSound(SoundEvents.BOTTLE_EMPTY, 1.0F, 1.0F);

            ItemStack remainder = isWaterBottle ? Items.GLASS_BOTTLE.getDefaultInstance() : Items.BUCKET.getDefaultInstance();
            slotAccess.set(remainder);
            //if (!player.getInventory().add(remainder)) {
            //    Containers.dropContents(player.level(), player.blockPosition(), NonNullList.of(remainder));
            //}

            return true;
        }

        return false;
    }

    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        ChargesComponent charges = ChargesComponent.get(stack);
        if (entity instanceof Player player && player.isCrouching() && !player.isAutoSpinAttack() && !charges.isFullyCharged() && player.isInWater()) {
            ChargesComponent.getModifiable(stack, true).charges(charges.maxCharges()).apply();
            player.playNotifySound(SoundEvents.SPONGE_ABSORB, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.playNotifySound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.PLAYERS, 1.0F, 0.8F);
        }

        if (entity instanceof LivingEntity living) {
            stack.setEntityRepresentation(living);
        }

        super.inventoryTick(stack, level, entity, slot, selected);
    }

    public Holder<SoundEvent> getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return BuiltInRegistries.SOUND_EVENT.createIntrusiveHolder(SoundEvents.AMBIENT_UNDERWATER_ENTER);
    }

    public static ItemStack getWorn(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return ItemStack.EMPTY;
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(living);
        if (component.isEmpty()) return ItemStack.EMPTY;

        TrinketComponent trinkets = component.get();
        for (Tuple<SlotReference, ItemStack> pair : trinkets.getEquipped(stack -> stack.is(CRItems.SAPPHIRE_CRYSTAL))) {
            if (pair.getA().inventory().getSlotType().getName().equals("trinket") && pair.getB().is(CRItems.SAPPHIRE_CRYSTAL)) {
                return pair.getB();
            }
        }

        return ItemStack.EMPTY;
    }
}
