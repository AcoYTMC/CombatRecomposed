package net.acoyt.recomposed.data.provider.resources;

import net.acoyt.recomposed.impl.index.CRItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

/**
 * @author AcoYT
 */
public class CRModelGen extends FabricModelProvider {
    public CRModelGen(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockModelGenerators generator) {
        //
    }

    public void generateItemModels(ItemModelGenerators generator) {
        generator.generateFlatItem(CRItems.LIFE_VEST, ModelTemplates.FLAT_ITEM);
    }
}
