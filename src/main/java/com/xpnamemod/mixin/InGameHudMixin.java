package com.xpnamemod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
    private void renderPlayerNameInsteadOfXp(DrawContext context, float delta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client != null && client.player != null && client.textRenderer != null) {
            String playerName = client.player.getName().getString();
            TextRenderer textRenderer = client.textRenderer;

            int textWidth = textRenderer.getWidth(playerName);
            int screenWidth = context.getScaledWindowWidth();
            int screenHeight = context.getScaledWindowHeight();

            // Precise vanilla XP height calculation
            int x = (screenWidth - textWidth) / 2;
            int y = screenHeight - 36;

            int xpGreenColor = 0x80FF20;
            int outlineColor = 0x000000;

            // Authentic 8-way thick vanilla XP text outline
            context.drawText(textRenderer, playerName, x - 1, y - 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x,     y - 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x + 1, y - 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x - 1, y,     outlineColor, false);
            context.drawText(textRenderer, playerName, x + 1, y,     outlineColor, false);
            context.drawText(textRenderer, playerName, x - 1, y + 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x,     y + 1, outlineColor, false);
            context.drawText(textRenderer, playerName, x + 1, y + 1, outlineColor, false);

            // Green Player Name
            context.drawText(textRenderer, playerName, x, y, xpGreenColor, false);
        }

        // Stops vanilla from rendering the level numbers completely
        ci.cancel();
    }
}
