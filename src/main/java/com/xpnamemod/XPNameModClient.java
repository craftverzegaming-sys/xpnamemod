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

            if (client == null || client.player == null || client.world == null) return;
            if (client.textRenderer == null || client.options.hudHidden) return;

            // Hide vanilla level numbers when player is in survival/adventure
            if (!client.player.isCreative() && !client.player.isSpectator()) {
                int level = client.player.experienceLevel;

                // Temporarily mask experienceLevel to 0 so vanilla rendering skips drawing numbers
                client.player.experienceLevel = 0;

                try {
                    TextRenderer textRenderer = client.textRenderer;
                    String playerName = client.player.getName().getString();
                    int textWidth = textRenderer.getWidth(playerName);

                    int screenWidth = drawContext.getScaledWindowWidth();
                    int screenHeight = drawContext.getScaledWindowHeight();

                    // Perfect center coordinates right above the XP progress bar
                    int x = (screenWidth - textWidth) / 2;
                    int y = screenHeight - 36;

                    int xpGreenColor = 0x80FF20;
                    int outlineColor = 0x000000;

                    // 1. Classic 8-way thick black outline (corners + edges)
                    drawContext.drawText(textRenderer, playerName, x - 1, y - 1, outlineColor, false);
                    drawContext.drawText(textRenderer, playerName, x,     y - 1, outlineColor, false);
                    drawContext.drawText(textRenderer, playerName, x + 1, y - 1, outlineColor, false);
                    drawContext.drawText(textRenderer, playerName, x - 1, y,     outlineColor, false);
                    drawContext.drawText(textRenderer, playerName, x + 1, y,     outlineColor, false);
                    drawContext.drawText(textRenderer, playerName, x - 1, y + 1, outlineColor, false);
                    drawContext.drawText(textRenderer, playerName, x,     y + 1, outlineColor, false);
                    drawContext.drawText(textRenderer, playerName, x + 1, y + 1, outlineColor, false);

                    // 2. Bright XP Green player name
                    drawContext.drawText(textRenderer, playerName, x, y, xpGreenColor, false);

                } finally {
                    // Restore original experience level immediately so gameplay logic isn't impacted
                    client.player.experienceLevel = level;
                }
            }
        });
    }
}
