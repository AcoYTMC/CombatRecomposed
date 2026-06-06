package net.acoyt.recomposed.data.provider.lang;

import net.acoyt.acornlib.api.template.OrganizedLanguageProvider;
import net.acoyt.recomposed.impl.index.CRItems;
import net.acoyt.recomposed.impl.index.tag.CRItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class CRLangGen extends OrganizedLanguageProvider {
    public CRLangGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateRegistrants(RegistryWrapper.WrapperLookup registries, TranslationBuilder builder) {
        CRItems.ITEMS.registerLang(registries, builder);
    }

    public void generateTags(RegistryWrapper.WrapperLookup registries, TranslationBuilder builder) {
        CRItemTags.ITEMS.registerLang(registries, builder);
    }
}
