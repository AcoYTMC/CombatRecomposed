package net.acoyt.recomposed.impl;

import com.mojang.logging.LogUtils;
import net.acoyt.acornlib.api.event.FilterRecipesEvent;
import net.acoyt.recomposed.api.ItemMaxCountEvent;
import net.acoyt.recomposed.api.WindChimeUsableEvent;
import net.acoyt.recomposed.impl.command.ResetCommandTimerCommand;
import net.acoyt.recomposed.impl.event.CRCombatTimerEvent;
import net.acoyt.recomposed.impl.event.CRItemMaxCountEvent;
import net.acoyt.recomposed.impl.event.CRRemoveRecipesEvent;
import net.acoyt.recomposed.impl.index.CRDataComponents;
import net.acoyt.recomposed.impl.index.CRItems;
import net.acoyt.recomposed.impl.index.CRSounds;
import net.acoyt.recomposed.impl.networking.CRNetworking;
import net.acoyt.recomposed.impl.util.LootTableModifiers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
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
        CRSounds.init();

        CRNetworking.registerTypes();
        CRNetworking.registerC2SPackets();

        /* Loot Tables */
        LootTableModifiers.init();

        /* Commands */
        CommandRegistrationCallback.EVENT.register(ResetCommandTimerCommand::register);

        /* Events */
        WindChimeUsableEvent.EVENT.register(new CRCombatTimerEvent());
        ItemMaxCountEvent.EVENT.register(new CRItemMaxCountEvent());
        FilterRecipesEvent.EVENT.register(new CRRemoveRecipesEvent());
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
