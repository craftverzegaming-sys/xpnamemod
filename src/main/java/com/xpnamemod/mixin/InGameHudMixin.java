package com.xpnamemod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	private static final int XP_GREEN = 0x0080FF20 | 0xFF000000;

	@Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
	private void xpnamemod$replaceLevelWithName(DrawContext context, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) {
			return;
		}
		if (client.interactionManager == null || !client.interactionManager.hasStatusBars()) {
			return;
		}

		String name = client.getSession().getUsername();
		TextRenderer textRenderer = client.textRenderer;

		int screenWidth = context.getScaledWindowWidth();
		int screenHeight = context.getScaledWindowHeight();

		int textWidth = textRenderer.getWidth(name);
		int x = (screenWidth - textWidth) / 2;
		int y = screenHeight - 32 - 10;

		context.drawText(textRenderer, name, x + 1, y, 0xFF000000, false);
		context.drawText(textRenderer, name, x - 1, y, 0xFF000000, false);
		context.drawText(textRenderer, name, x, y + 1, 0xFF000000, false);
		context.drawText(textRenderer, name, x, y - 1, 0xFF000000, false);
		context.drawText(textRenderer, name, x, y, XP_GREEN, false);

		ci.cancel();
	}
}
