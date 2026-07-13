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
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.slf4j.Logger;

/**
 * @author AcoYT
 */
public class Recomposed implements ModInitializer {
    public static final String MOD_ID = "recomposed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<Enchantment> EMPTY_KEY = ResourceKey.create(Registries.ENCHANTMENT, id("empty"));
    public static final Enchantment EMPTY = Enchantment.enchantment(
            Enchantment.definition(
                    HolderSet.empty(),
                    1, 1,
                    Enchantment.constantCost(0),
                    Enchantment.constantCost(0),
                    0
            )
    ).build(EMPTY_KEY.location());

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

        /* Resource Packs */
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(id("muted_wind_chimes"), container, Component.literal("Muted Wind Chimes"), ResourcePackActivationType.NORMAL);
        });
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
