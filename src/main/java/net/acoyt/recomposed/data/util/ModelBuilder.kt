package net.acoyt.recomposed.data.util

import net.acoyt.recomposed.impl.Recomposed
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators

class ModelBuilder {
    private var generateBlockStateModels: ((BlockModelGenerators) -> Unit)? = null
    private var generateItemModels: ((ItemModelGenerators) -> Unit)? = null

    fun blocks(fn: (BlockModelGenerators) -> Unit) {
        this.generateBlockStateModels = fn
    }

    fun items(fn: (ItemModelGenerators) -> Unit) {
        this.generateItemModels = fn
    }

    fun build(): (FabricDataOutput) -> FabricModelProvider {
        return {
            object : FabricModelProvider(it) {
                override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators) {
                    if (generateItemModels != null) {
                        generateBlockStateModels?.invoke(blockStateModelGenerator)
                    }
                }

                override fun generateItemModels(itemModelGenerator: ItemModelGenerators) {
                    if (generateItemModels != null) {
                        generateItemModels?.invoke(itemModelGenerator)
                    }
                }

                override fun getName(): String {
                    return "${Recomposed.MOD_ID} ${super.name}"
                }
            }
        }
    }
}
