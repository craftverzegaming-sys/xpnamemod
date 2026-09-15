package com.xpnamemod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.InGameHudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class XPNameModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Registering with low priority / post-render guarantees drawing on top of HUD
        HudRenderCallback.EVENT.register(this::renderXpName);
    }

    private void renderXpName(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Guard clauses to ensure we only draw when playing inside a world
        if (client == null || client.player == null || client.world == null) return;
        if (client.options.hudHidden || client.textRenderer == null) return;

        // Render in Survival / Adventure mode (where XP bars exist)
        if (!client.player.isCreative() && !client.player.isSpectator()) {
            TextRenderer textRenderer = client.textRenderer;
            String playerName = client.player.getName().getString();

            int textWidth = textRenderer.getWidth(playerName);
            int screenWidth = context.getScaledWindowWidth();
            int screenHeight = context.getScaledWindowHeight();

            // Perfect center alignment directly above the XP bar
            int x = (screenWidth - textWidth) / 2;
            int y = screenHeight - 36;

            int xpGreenColor = 0x80FF20;
            int outlineColor = 0x000000;

            // Step 1: Draw black text outline (8-way thick shadow)
            context.drawText(textRenderer, playerName, x - 1, y - 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x,     y - 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x + 1, y - 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x - 1, y,     outlineColor, false);
            context.drawText(textRenderer, playerName, x + 1, y,     outlineColor, false);
            context.drawText(textRenderer, playerName, x - 1, y + 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x,     y + 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x + 1, y + 1, outlineColor, false);

            // Step 2: Draw central green text
            context.drawText(textRenderer, playerName, x, y, xpGreenColor, false);
        }
    }
}
