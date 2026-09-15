package com.xpnamemod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Replaces the vanilla "XP level number" HUD element with the player's
 * username instead, keeping the same classic green color + black outline
 * look vanilla uses for that number.
 *
 * NOTE ON VERSIONS: Mojang/Yarn occasionally rename methods and classes
 * between Minecraft versions. This targets the method commonly named
 * "renderExperienceLevel" in Yarn mappings for 1.21.x. If this mod fails
 * to build on your exact version (1.21.5 - 1.21.11), open the game's
 * mapped sources in your IDE (Loom -> genSources, or just ctrl-click into
 * InGameHud.class) and look for the method that draws the level number
 * above the XP bar - update the "method" string below to match its real
 * name if it's different.
 */
@Mixin(InGameHud.class)
public class InGameHudMixin {

	// Same green Minecraft uses for the XP level number (0x80FF20).
	private static final int XP_GREEN = 0x0080FF20 | 0xFF000000;

	@Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
	private void xpnamemod$replaceLevelWithName(DrawContext context, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) {
			return;
		}

		// Don't draw anything if the player can't gain XP right now
		// (e.g. spectator mode) - matches vanilla's own check.
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

		// Draw the black outline: 4 offset copies in black...
		context.drawText(textRenderer, name, x + 1, y, 0xFF000000, false);
		context.drawText(textRenderer, name, x - 1, y, 0xFF000000, false);
		context.drawText(textRenderer, name, x, y + 1, 0xFF000000, false);
		context.drawText(textRenderer, name, x, y - 1, 0xFF000000, false);
		// ...then the green name on top, exactly like vanilla's level number.
		context.drawText(textRenderer, name, x, y, XP_GREEN, false);

		// Stop the vanilla level number from also being drawn.
		ci.cancel();
	}
}
