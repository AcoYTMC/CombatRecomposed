package net.acoyt.recomposed.impl.util;

import net.acoyt.recomposed.impl.index.CRItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
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
    public LootTable replaceLootTable(ResourceKey<LootTable> key, LootTable lootTable, LootTableSource source, HolderLookup.Provider registries) {
        if (key.equals(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE)) {
            return LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(3))
                            .add(LootItem.lootTableItem(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(3))
                            .add(LootItem.lootTableItem(Items.FLOW_BANNER_PATTERN).setWeight(2))
                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_CREATOR).setWeight(1))
                            .add(LootItem.lootTableItem(Items.HEAVY_CORE).setWeight(1))
                    ).build();
        }

        return lootTable;
    }

    public void modifyLootTable(ResourceKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, HolderLookup.Provider registries) {
        if (key.equals(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS)) {
            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0F, 1.0F))
                    .when(LootItemRandomChanceCondition.randomChance(0.25F))
                    .add(LootItem.lootTableItem(CRItems.WIND_CHIME));

            tableBuilder.withPool(poolBuilder);
        }

        if (key.equals(BuiltInLootTables.SHIPWRECK_TREASURE)) {
            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0F, 1.0F))
                    .when(LootItemRandomChanceCondition.randomChance(0.13F))
                    .add(LootItem.lootTableItem(CRItems.LIFE_VEST));

            tableBuilder.withPool(poolBuilder);
        }
    }
}
