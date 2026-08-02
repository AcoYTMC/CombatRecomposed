package net.acoyt.recomposed.data

import net.acoyt.recomposed.data.provider.CRDynamicRegistryGen
import net.acoyt.recomposed.data.provider.CRItemTagGen
import net.acoyt.recomposed.data.util.LangBuilder
import net.acoyt.recomposed.data.util.ModelBuilder
import net.acoyt.recomposed.impl.Recomposed
import net.acoyt.recomposed.impl.index.CRItems
import net.acoyt.recomposed.impl.index.tag.CRItemTags
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.data.models.model.ModelTemplates

class RecomposedDatagen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(dataGenerator: FabricDataGenerator) {
        val pack = dataGenerator.createPack()

        datagen(pack) {
            lang {
                genRegistrants { provider, builder ->
                    CRItems.ITEMS.registerLang(provider, builder)
                }

                genSubtitles { _, builder ->
                    builder.add("subtitles.entity.jump", "Entity Jumps")
                }

                genTags { provider, builder ->
                    CRItemTags.ITEMS.registerLang(provider, builder)
                }

                genTexts { _, builder ->
                    builder.add("trinkets.slot.misc.trinket", "Trinket")
                    builder.add("tooltip.recomposed.in_combat", "You cannot use this in combat!")
                    builder.add("tooltip.recomposed.hold_shift", $$"Hold %1$s for more info")

                    builder.add("item.recomposed.sapphire_crystal.tooltip", "Crouch while in water to refill charges!")
                }
            }
            models {
                items { generator ->
                    generator.generateFlatItem(CRItems.LIFE_VEST, ModelTemplates.FLAT_ITEM)
                    generator.generateFlatItem(CRItems.SAPPHIRE_CRYSTAL, ModelTemplates.FLAT_ITEM)
                }
            }
        }

        pack.addProvider(::CRItemTagGen)
        pack.addProvider(::CRDynamicRegistryGen)
    }

    override fun buildRegistry(builder: RegistrySetBuilder?) {
        builder?.add(Registries.ENCHANTMENT) { context ->
            context.register(Recomposed.EMPTY_KEY, Recomposed.EMPTY)
        }
    }

    internal class DataGeneratorBuilder {
        private var modelBuilder: ModelBuilder? = null
        private var langBuilder: LangBuilder? = null

        fun models(initializer: ModelBuilder.() -> Unit) {
            this.modelBuilder = ModelBuilder().apply(initializer)
        }

        fun lang(initializer: LangBuilder.() -> Unit) {
            this.langBuilder = LangBuilder().apply(initializer)
        }

        fun build(pack: FabricDataGenerator.Pack) {
            this.modelBuilder?.build()?.let {
                pack.addProvider(it)
            }

            this.langBuilder?.build()?.let {
                pack.addProvider(it)
            }
        }
    }

    internal fun datagen(pack: FabricDataGenerator.Pack, initializer: DataGeneratorBuilder.() -> Unit) {
        DataGeneratorBuilder()
            .apply(initializer)
            .build(pack)
    }
}
