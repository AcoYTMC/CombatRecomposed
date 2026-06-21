package net.acoyt.recomposed.impl.util;

import net.acoyt.recomposed.api.FunctionalLevelEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AcoYT
 */
public class CRUtil {
    public static RegistryEntryOwner<?> ENCHANTMENT_REGISTRY_OWNER = null;
    public static final Map<Enchantment, Integer> MAX_LEVELS = new HashMap<>();

    public static final List<RegistryKey<Enchantment>> disabledEnchantments = Arrays.asList(
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

    public static final List<RegistryEntry<Potion>> disabledPotions = Arrays.asList(
            Potions.STRENGTH,
            Potions.LONG_STRENGTH,
            Potions.STRONG_STRENGTH,
            Potions.STRONG_SWIFTNESS
    );

    public static boolean isDisabled(RegistryEntry<Enchantment> enchantment) {
        if (enchantment.getKey().isPresent()) {
            return isDisabled(enchantment.getKey().get().getValue());
        }

        return false;
    }

    public static boolean isDisabled(Identifier identifier) {
        return disabledEnchantments.contains(RegistryKey.of(RegistryKeys.ENCHANTMENT, identifier));
    }

    public static boolean isPotionDisabled(RegistryEntry<Potion> potion) {
        return disabledPotions.contains(potion);
    }

    public static int getFunctionalLevel(RegistryEntry<Enchantment> enchantment) {
        return FunctionalLevelEvent.EVENT.invoker().getFunctionalLevel(enchantment).orElseGet(() -> {
            if (enchantment.getKey().isPresent()) {
                RegistryKey<Enchantment> key = enchantment.getKey().get();
                if (key == Enchantments.PROTECTION) return 2;
                if (key == Enchantments.SHARPNESS) return 2;
                if (key == Enchantments.FEATHER_FALLING) return 2;
                if (key == Enchantments.BLAST_PROTECTION) return 2;
                if (key == Enchantments.KNOCKBACK) return 1;
                if (key == Enchantments.RIPTIDE) return 2;
            }

            return 1;
        });
    }

    public static boolean isGroundedOrAirborne(LivingEntity living, boolean allowWater) {
        if (living instanceof PlayerEntity player && player.getAbilities().flying) {
            return false;
        }
        if (!allowWater) {
            if (living.isTouchingWater() || living.isSwimming()) {
                return false;
            }
        }

        return !living.isFallFlying() && !living.hasVehicle() && !living.isClimbing();
    }
}
