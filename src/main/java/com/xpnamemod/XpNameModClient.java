package com.xpnamemod;

import net.fabricmc.api.ClientModInitializer;

/**
 * This mod doesn't actually need to register anything here right now,
 * since all the work happens in {@link com.xpnamemod.mixin.InGameHudMixin}.
 * This entrypoint exists so the mod shows up properly and gives us a place
 * to add config / commands / etc. later if you want to expand this.
 */
public class XpNameModClient implements ClientModInitializer {

	public static final String MOD_ID = "xpnamemod";

	@Override
	public void onInitializeClient() {
		// Nothing to do on startup yet - the mixin handles everything.
	}
}
