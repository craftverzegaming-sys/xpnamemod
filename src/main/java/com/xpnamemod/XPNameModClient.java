package com.xpnamemod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;

public class XPNameModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            // Safety checks to ensure we are actually inside a world and HUD is visible
            if (client == null || client.player == null || client.world == null) return;
            if (client.textRenderer == null || client.options.hudHidden) return;

            try {
                TextRenderer textRenderer = client.textRenderer;
                String playerName = client.player.getName().getString();
                int textWidth = textRenderer.getWidth(playerName);

                int screenWidth = drawContext.getScaledWindowWidth();
                int screenHeight = drawContext.getScaledWindowHeight();

                // Calculate exact position above the hotbar/XP bar
                int x = (screenWidth - textWidth) / 2;
                int y = screenHeight - 36;

                int xpGreenColor = 0x80FF20;
                int outlineColor = 0x000000;

                // 1. Scrub/Clear any underlying vanilla level number by drawing a dark background spot
                int patchWidth = Math.max(textWidth, 24);
                int patchX1 = (screenWidth - patchWidth) / 2;
                int patchX2 = (screenWidth + patchWidth) / 2;
                drawContext.fill(patchX1, y - 1, patchX2, y + 9, 0xFF000000);

                // 2. Render 4-way black outline around the name
                drawContext.drawText(textRenderer, playerName, x - 1, y, outlineColor, false);
                drawContext.drawText(textRenderer, playerName, x + 1, y, outlineColor, false);
                drawContext.drawText(textRenderer, playerName, x, y - 1, outlineColor, false);
                drawContext.drawText(textRenderer, playerName, x, y + 1, outlineColor, false);

                // 3. Render Player Name in bright XP Green
                drawContext.drawText(textRenderer, playerName, x, y, xpGreenColor, false);

            } catch (Throwable ignored) {
                // Catch rendering glitches silently
            }
        });
    }
}
