package net.acoyt.recomposed.data.provider;

import net.acoyt.recomposed.impl.index.tag.CRItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static net.acoyt.recomposed.impl.index.CRItems.*;
import static net.minecraft.world.item.Items.*;

/**
 * @author AcoYT
 */
public class CRItemTagGen extends FabricTagProvider.ItemTagProvider {
    public CRItemTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    public void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(CRItemTags.HAS_DURABILITY)
                .add(WOLF_ARMOR)
                .setReplace(false);

        this.getOrCreateTagBuilder(CRItemTags.STRONG)
                .add(GOLDEN_SWORD, GOLDEN_SHOVEL, GOLDEN_PICKAXE, GOLDEN_AXE, GOLDEN_HOE)
                .add(NETHERITE_SWORD, NETHERITE_SHOVEL, NETHERITE_PICKAXE, NETHERITE_AXE, NETHERITE_HOE)
                .add(GOLDEN_HELMET, GOLDEN_CHESTPLATE, GOLDEN_LEGGINGS, GOLDEN_BOOTS)
                .add(NETHERITE_HELMET, NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS, NETHERITE_BOOTS)
                .setReplace(false);

        this.getOrCreateTagBuilder(CRItemTags.TRINKETS)
                .add(WIND_CHIME, LIFE_VEST, SAPPHIRE_CRYSTAL)
                .setReplace(false);
    }
}
