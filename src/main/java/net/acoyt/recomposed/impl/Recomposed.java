package net.acoyt.recomposed.impl;

import com.mojang.logging.LogUtils;
import net.acoyt.recomposed.api.WindChimeUsableEvent;
import net.acoyt.recomposed.impl.cca.entity.CombatTimerComponent;
import net.acoyt.recomposed.impl.index.CRDataComponents;
import net.acoyt.recomposed.impl.index.CRItems;
import net.acoyt.recomposed.impl.index.CRNetworking;
import net.acoyt.recomposed.impl.util.LootTableModifiers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

/**
 * @author AcoYT
 */
public class Recomposed implements ModInitializer {
    public static final String MOD_ID = "recomposed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final RegistryKey<Enchantment> EMPTY_KEY = RegistryKey.of(RegistryKeys.ENCHANTMENT, id("empty"));
    public static final Enchantment EMPTY = Enchantment.builder(
            Enchantment.definition(
                    RegistryEntryList.empty(),
                    1, 1,
                    Enchantment.constantCost(0),
                    Enchantment.constantCost(0),
                    0
            )
    ).build(EMPTY_KEY.getValue());

    public void onInitialize() {
        /* Initialization */
        CRDataComponents.init();
        CRItems.init();

        CRNetworking.registerTypes();
        CRNetworking.registerC2SPackets();

        /* Loot Tables */
        LootTableModifiers.init();

        WindChimeUsableEvent.EVENT.register((player, world) -> {
            return CombatTimerComponent.KEY.get(player).getRemaining() <= 0;
        });
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
