package net.acoyt.recomposed.data;

import net.acoyt.recomposed.data.provider.CRDynamicRegistryGen;
import net.acoyt.recomposed.data.provider.CRItemTagGen;
import net.acoyt.recomposed.data.provider.lang.CRLangGen;
import net.acoyt.recomposed.data.provider.resources.CRModelGen;
import net.acoyt.recomposed.impl.Recomposed;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

/**
 * @author AcoYT
 */
public class RecomposedDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(CRLangGen::new);

        pack.addProvider(CRModelGen::new);

        pack.addProvider(CRItemTagGen::new);

        pack.addProvider(CRDynamicRegistryGen::new);
    }

    public void buildRegistry(RegistrySetBuilder builder) {
        builder.add(Registries.ENCHANTMENT, registerable -> {
            registerable.register(Recomposed.EMPTY_KEY, Recomposed.EMPTY);
        });
    }
}
