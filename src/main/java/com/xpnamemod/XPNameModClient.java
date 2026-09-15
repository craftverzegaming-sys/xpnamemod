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
		HudRenderCallback.EVENT.register(this::onHudRender);
	}

	private void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();

		if (client.player == null || client.options.hudHidden) {
			return;
		}

		if (client.interactionManager != null && client.interactionManager.hasStatusBars()) {
			String username = client.player.getName().getString();
			TextRenderer textRenderer = client.textRenderer;

			int screenWidth = client.getWindow().getScaledWidth();
			int screenHeight = client.getWindow().getScaledHeight();

			int textWidth = textRenderer.getWidth(username);
			int x = (screenWidth - textWidth) / 2;
			int y = screenHeight - 32 - 10;

			int textColor = 0xFF80FF20;
			int outlineColor = 0xFF000000;

			// 8-way outline rendering
			drawContext.drawText(textRenderer, username, x - 1, y - 1, outlineColor, false);
			drawContext.drawText(textRenderer, username, x,     y - 1, outlineColor, false);
			drawContext.drawText(textRenderer, username, x + 1, y - 1, outlineColor, false);
			drawContext.drawText(textRenderer, username, x - 1, y,     outlineColor, false);
			drawContext.drawText(textRenderer, username, x + 1, y,     outlineColor, false);
			drawContext.drawText(textRenderer, username, x - 1, y + 1, outlineColor, false);
			drawContext.drawText(textRenderer, username, x,     y + 1, outlineColor, false);
			drawContext.drawText(textRenderer, username, x + 1, y + 1, outlineColor, false);

			// Main text
			drawContext.drawText(textRenderer, username, x, y, textColor, false);
		}
	}
}
