package net.acoyt.recomposed.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.acoyt.recomposed.impl.index.CRDataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

/**
 * @author AcoYT
 */
public record ChargesComponent(int charges, int maxCharges) {
    public static final ChargesComponent DEFAULT = new ChargesComponent(0, 5);

    public static final Codec<ChargesComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("charges", DEFAULT.charges).forGetter(ChargesComponent::charges),
            Codec.INT.optionalFieldOf("maxCharges", DEFAULT.maxCharges).forGetter(ChargesComponent::maxCharges)
    ).apply(instance, ChargesComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChargesComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ChargesComponent::charges,
            ByteBufCodecs.INT, ChargesComponent::maxCharges,
            ChargesComponent::new
    );

    public static ChargesComponent get(ItemStack stack) {
        return stack.getOrDefault(CRDataComponents.CHARGES, DEFAULT);
    }

    public static ChargesComponent.Builder getModifiable(ItemStack stack, boolean basedOff) {
        return new Builder(stack, basedOff);
    }

    public ChargesComponent withCharges(int charges) {
        return new ChargesComponent(charges, maxCharges);
    }

    public boolean isFullyCharged() {
        return charges >= maxCharges;
    }

    public static class Builder {
        private int charges = DEFAULT.charges;
        private int maxCharges = DEFAULT.maxCharges;
        private final ItemStack stack;

        private Builder(ItemStack stack, boolean basedOff) {
            this.stack = stack;
            if (basedOff && stack.has(CRDataComponents.CHARGES)) {
                ChargesComponent base = get(stack);
                charges = base.charges;
                maxCharges = base.maxCharges;
            }
        }

        public Builder charges(int amount) {
            charges = amount;
            return this;
        }

        public Builder increment(int amount) {
            charges = Mth.clamp(charges + amount, 0, maxCharges);
            return this;
        }

        public void apply() {
            stack.set(CRDataComponents.CHARGES, new ChargesComponent(charges, maxCharges));
        }
    }
}
