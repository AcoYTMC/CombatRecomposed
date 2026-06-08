package net.acoyt.recomposed.impl.event.client;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector2i;

/**
 * @author AcoYT
 */
public class ChimeSwingEvent implements ClientTickEvents.EndTick {
    public static float swing = 0.0F;
    public static Vector2i prevPos = new Vector2i();

    public void onEndTick(MinecraftClient client) {
        if (client.world == null) return;
        swing = (float) Math.sin(client.world.getTime() * 0.13F);
    }

    public static <T extends ScreenHandler> void render(HandledScreen<T> instance, DrawContext context, ItemStack stack, int x, int y, String amountText) {
        float tickDelta = MiscUtils.getTickDelta();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;
        prevPos = new Vector2i(x, y);

        MatrixStack matrices = context.getMatrices();

        float rotationSpeed = 0.18F;
        float rotationStrength = 0.35F;
        float rotation = (float) Math.sin(client.world.getTime() * rotationSpeed);

        Vector2i difference = new Vector2i(x - prevPos.x, y - prevPos.y);

        matrices.push();
        context.getMatrices().translate(0.0F, 0.0F, 232.0F);
        matrices.translate(-MathHelper.lerp(tickDelta, difference.x, 0.0F), -MathHelper.lerp(tickDelta, difference.y, 0.0F), 0.0F);
        matrices.translate(0.0F, 2.5F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation * rotationStrength + 1));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation * (rotationStrength * 2) + 1));

        context.drawItem(stack, x, y);
        context.drawItemInSlot(client.textRenderer, stack, x, y - 8, amountText);

        matrices.pop();
    }
}
