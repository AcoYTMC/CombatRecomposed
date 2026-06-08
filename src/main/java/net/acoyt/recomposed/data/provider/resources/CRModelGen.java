package net.acoyt.recomposed.data.provider.resources;

import net.acoyt.recomposed.impl.index.CRItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

/**
 * @author AcoYT
 */
public class CRModelGen extends FabricModelProvider {
    public CRModelGen(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        //
    }

    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(CRItems.WIND_CHIME, Models.GENERATED);
    }
}
