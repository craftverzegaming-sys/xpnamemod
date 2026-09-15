package com.xpnamemod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class XPNameModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(this::renderXpName);
    }

    private void renderXpName(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.player == null || client.world == null) return;
        if (client.options.hudHidden || client.textRenderer == null) return;

        if (!client.player.isCreative() && !client.player.isSpectator()) {
            TextRenderer textRenderer = client.textRenderer;
            String playerName = client.player.getName().getString();

            int textWidth = textRenderer.getWidth(playerName);
            int screenWidth = context.getScaledWindowWidth();
            int screenHeight = context.getScaledWindowHeight();

            // Screen position centered above the experience bar
            int x = (screenWidth - textWidth) / 2;
            int y = screenHeight - 36;

            int xpGreenColor = 0x80FF20;
            int outlineColor = 0x000000;

            // Black outline
            context.drawText(textRenderer, playerName, x - 1, y - 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x,     y - 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x + 1, y - 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x - 1, y,     outlineColor, false);
            context.drawText(textRenderer, playerName, x + 1, y,     outlineColor, false);
            context.drawText(textRenderer, playerName, x - 1, y + 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x,     y + 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x + 1, y + 1, outlineColor, false);

            // Green text
            context.drawText(textRenderer, playerName, x, y, xpGreenColor, false);
        }
    }
}
