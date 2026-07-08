package net.acoyt.recomposed.impl.event.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2i;

/**
 * @author AcoYT
 */
public class ChimeSwingEvent implements ClientTickEvents.EndTick {
    public static float swing = 0.0F;
    public static Vector2i prevPos = new Vector2i();

    public void onEndTick(Minecraft client) {
        if (client.level == null) return;
        swing = (float) Math.sin(client.level.getGameTime() * 0.13F);
    }

    public static <T extends AbstractContainerMenu> void render(AbstractContainerScreen<T> instance, GuiGraphics context, ItemStack stack, int x, int y, String amountText) {
        float tickDelta = MiscUtils.getTickDelta();
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return;
        prevPos = new Vector2i(x, y);

        PoseStack matrices = context.pose();

        float rotationSpeed = 0.18F;
        float rotationStrength = 0.35F;
        float rotation = (float) Math.sin(client.level.getGameTime() * rotationSpeed);

        Vector2i difference = new Vector2i(x - prevPos.x, y - prevPos.y);

        matrices.pushPose();
        context.pose().translate(0.0F, 0.0F, 232.0F);
        matrices.translate(-Mth.lerp(tickDelta, difference.x, 0.0F), -Mth.lerp(tickDelta, difference.y, 0.0F), 0.0F);
        matrices.translate(0.0F, 2.5F, 0.0F);
        matrices.mulPose(Axis.YP.rotationDegrees(rotation * rotationStrength + 1));
        matrices.mulPose(Axis.ZP.rotationDegrees(rotation * (rotationStrength * 2) + 1));

        context.renderItem(stack, x, y);
        context.renderItemDecorations(client.font, stack, x, y - 8, amountText);

        matrices.popPose();
    }
}
