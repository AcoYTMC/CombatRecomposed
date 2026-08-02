package net.acoyt.recomposed.data.util

import net.acoyt.acornlib.api.template.OrganizedLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

@Suppress("unused")
class LangBuilder {
    private var generateAdvancements: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateBlocks: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateCommands: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateConfigs: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateEntities: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateItems: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateRegistrants: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateStatusEffects: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateSubtitles: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateTags: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null
    private var generateTexts: ((HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit)? = null

    fun genAdvancements(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateAdvancements = fn
    }

    fun genBlocks(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateBlocks = fn
    }

    fun genCommands(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateCommands = fn
    }

    fun genConfigs(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateConfigs = fn
    }

    fun genEntities(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateEntities = fn
    }

    fun genItems(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateItems = fn
    }

    fun genRegistrants(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateRegistrants = fn
    }

    fun genStatusEffects(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateStatusEffects = fn
    }

    fun genSubtitles(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateSubtitles = fn
    }

    fun genTags(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateTags = fn
    }

    fun genTexts(fn: (HolderLookup.Provider, FabricLanguageProvider.TranslationBuilder) -> Unit) {
        this.generateTexts = fn
    }

    fun addMultiline(builder: FabricLanguageProvider.TranslationBuilder, base: String, separator: Char, vararg values: String) {
        for ((index, value) in values.withIndex()) {
            builder.add(base + separator + index, value)
        }
    }

    fun build(): (output: FabricDataOutput, lookup: CompletableFuture<HolderLookup.Provider>) -> OrganizedLanguageProvider {
        return { output, lookup ->
            object : OrganizedLanguageProvider(output, lookup) {
                override fun generateAdvancements(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateAdvancements != null)
                        generateAdvancements?.invoke(lookup, builder)
                }

                override fun generateBlocks(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateBlocks != null)
                        generateBlocks?.invoke(lookup, builder)
                }

                override fun generateCommands(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateCommands != null)
                        generateCommands?.invoke(lookup, builder)
                }

                override fun generateConfigs(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateConfigs != null)
                        generateConfigs?.invoke(lookup, builder)
                }

                override fun generateEntities(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateEntities != null)
                        generateEntities?.invoke(lookup, builder)
                }

                override fun generateItems(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateItems != null)
                        generateItems?.invoke(lookup, builder)
                }

                override fun generateRegistrants(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateRegistrants != null)
                        generateRegistrants?.invoke(lookup, builder)
                }

                override fun generateStatusEffects(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateStatusEffects != null)
                        generateStatusEffects?.invoke(lookup, builder)
                }

                override fun generateSubtitles(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateSubtitles != null)
                        generateSubtitles?.invoke(lookup, builder)
                }

                override fun generateTags(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateTags != null)
                        generateTags?.invoke(lookup, builder)
                }

                override fun generateTexts(lookup: HolderLookup.Provider, builder: TranslationBuilder) {
                    if (generateTexts != null)
                        generateTexts?.invoke(lookup, builder)
                }
            }
        }
    }
}
