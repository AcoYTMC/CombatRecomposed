package net.acoyt.recomposed.impl.util;

import net.acoyt.recomposed.api.FunctionalLevelEvent;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AcoYT
 */
public class CRUtil {
    public static HolderOwner<?> ENCHANTMENT_REGISTRY_OWNER = null;
    public static final Map<Enchantment, Integer> MAX_LEVELS = new HashMap<>();

    public static final List<ResourceKey<Enchantment>> disabledEnchantments = Arrays.asList(
            Enchantments.THORNS,
            Enchantments.UNBREAKING,
            Enchantments.MENDING,
            Enchantments.PROJECTILE_PROTECTION,
            Enchantments.FIRE_PROTECTION,
            Enchantments.FORTUNE,
            Enchantments.DENSITY,
            Enchantments.BREACH,
            Enchantments.WIND_BURST
    );

    public static final List<Holder<Potion>> disabledPotions = Arrays.asList(
            Potions.STRENGTH,
            Potions.LONG_STRENGTH,
            Potions.STRONG_STRENGTH,
            Potions.STRONG_SWIFTNESS
    );

    public static boolean isDisabled(Holder<Enchantment> enchantment) {
        if (enchantment.unwrapKey().isPresent()) {
            return isDisabled(enchantment.unwrapKey().get());
        }

        return false;
    }

    public static boolean isDisabled(ResourceKey<?> resourceKey) {
        return isDisabled(resourceKey.location());
    }

    public static boolean isDisabled(ResourceLocation identifier) {
        return disabledEnchantments.contains(ResourceKey.create(Registries.ENCHANTMENT, identifier));
    }

    public static boolean isPotionDisabled(Holder<Potion> potion) {
        return disabledPotions.contains(potion);
    }

    public static int getFunctionalLevel(Holder<Enchantment> enchantment, boolean strong) {
        return FunctionalLevelEvent.EVENT.invoker().getFunctionalLevel(enchantment).orElseGet(() -> {
            if (enchantment.unwrapKey().isPresent()) {
                ResourceKey<Enchantment> key = enchantment.unwrapKey().get();

                // Weapon
                if (key == Enchantments.SHARPNESS) return 2;
                if (key == Enchantments.LOOTING) return strong ? 3 : 2;

                // Tool
                if (key == Enchantments.EFFICIENCY) return strong ? 5 : 4;
                if (key == Enchantments.RIPTIDE) return 3;
                if (key == Enchantments.LOYALTY) return 3;

                // Armor
                if (key == Enchantments.PROTECTION) return 2;
                if (key == Enchantments.FEATHER_FALLING) return strong ? 4 : 2;
                if (key == Enchantments.BLAST_PROTECTION) return 2;
                if (key == Enchantments.RESPIRATION) return 3;
                if (key == Enchantments.DEPTH_STRIDER) return 3;
                if (key == Enchantments.SOUL_SPEED) return 3;
                if (key == Enchantments.SWIFT_SNEAK) return 3;
            }

            return 1;
        });
    }

    public static boolean isGroundedOrAirborne(LivingEntity living, boolean allowWater) {
        if (living instanceof Player player && player.getAbilities().flying) {
            return false;
        }
        if (!allowWater) {
            if (living.isInWater() || living.isSwimming()) {
                return false;
            }
        }

        return !living.isFallFlying() && !living.isPassenger() && !living.onClimbable();
    }
}
