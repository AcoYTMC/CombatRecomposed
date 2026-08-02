package net.acoyt.recomposed.data.util

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class ItemTagBuilder {
    private var generateItemTags: ((HolderLookup.Provider) -> Unit)? = null

    fun addTags(fn: (HolderLookup.Provider) -> Unit) {
        this.generateItemTags = fn
    }

    fun build(): (output: FabricDataOutput, lookup: CompletableFuture<HolderLookup.Provider>) -> FabricTagProvider.ItemTagProvider {
        return { output, lookup ->
            object : FabricTagProvider.ItemTagProvider(output, lookup) {
                override fun addTags(lookup: HolderLookup.Provider) {
                    if (generateItemTags != null)
                        generateItemTags?.invoke(lookup)
                }
            }
        }
    }
}
