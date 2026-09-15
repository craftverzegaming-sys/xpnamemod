package com.xpnamemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XPNameMod implements ModInitializer {
	public static final String MOD_ID = "xp-name-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("XP Name Mod loaded!");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
