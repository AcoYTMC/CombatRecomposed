package net.acoyt.recomposed.impl.util;

import net.acoyt.recomposed.impl.index.CRItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

/**
 * @author AcoYT
 */
public class LootTableModifiers implements LootTableEvents.Replace, LootTableEvents.Modify {
    public static void init() {
        LootTableEvents.REPLACE.register(new LootTableModifiers());
        LootTableEvents.MODIFY.register(new LootTableModifiers());
    }

    @Nullable
    public LootTable replaceLootTable(RegistryKey<LootTable> key, LootTable lootTable, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        if (key.equals(LootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE_CHEST)) {
            return LootTable.builder()
                    .pool(LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(Items.ENCHANTED_GOLDEN_APPLE).weight(3))
                            .with(ItemEntry.builder(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE).weight(3))
                            .with(ItemEntry.builder(Items.FLOW_BANNER_PATTERN).weight(2))
                            .with(ItemEntry.builder(Items.MUSIC_DISC_CREATOR).weight(1))
                            .with(ItemEntry.builder(Items.HEAVY_CORE).weight(1))
                    ).build();
        }

        return lootTable;
    }

    public void modifyLootTable(RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        if (key.equals(LootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_CHEST)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(UniformLootNumberProvider.create(1.0F, 1.0F))
                    .conditionally(RandomChanceLootCondition.builder(0.25F))
                    .with(ItemEntry.builder(CRItems.WIND_CHIME));

            tableBuilder.pool(poolBuilder);
        }

        if (key.equals(LootTables.SHIPWRECK_TREASURE_CHEST)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(UniformLootNumberProvider.create(1.0F, 1.0F))
                    .conditionally(RandomChanceLootCondition.builder(0.13F))
                    .with(ItemEntry.builder(CRItems.LIFE_VEST));

            tableBuilder.pool(poolBuilder);
        }
    }
}
