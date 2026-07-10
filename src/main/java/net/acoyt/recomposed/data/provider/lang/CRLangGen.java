package net.acoyt.recomposed.data.provider.lang;

import net.acoyt.acornlib.api.template.OrganizedLanguageProvider;
import net.acoyt.recomposed.impl.index.CRItems;
import net.acoyt.recomposed.impl.index.tag.CRItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class CRLangGen extends OrganizedLanguageProvider {
    public CRLangGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateRegistrants(HolderLookup.Provider registries, TranslationBuilder builder) {
        CRItems.ITEMS.registerLang(registries, builder);
    }

    public void generateSubtitles(HolderLookup.Provider registries, TranslationBuilder builder) {
        builder.add("subtitles.entity.jump", "Entity Jumps");
    }

    public void generateTags(HolderLookup.Provider registries, TranslationBuilder builder) {
        CRItemTags.ITEMS.registerLang(registries, builder);
    }

    public void generateTexts(HolderLookup.Provider registries, TranslationBuilder builder) {
        builder.add("trinkets.slot.misc.trinket", "Trinket");

        builder.add("tooltip.recomposed.in_combat", "You cannot use this in combat!");
    }
}
