# XP Name Mod

A Fabric client-side mod for Minecraft **1.21.11** (should also work on 1.21.5–1.21.10
with minor tweaks — see notes below).

## What it does
Replaces the number shown above your XP bar (your level number) with your
player name, drawn in the same classic XP green (`#80FF20`) with the same
black outline vanilla uses. It doesn't touch your actual XP or level — it
just changes what gets drawn — so it stays your name no matter your real
level or if you run `/xp` commands.

## Requirements to build/run this
- **Java Development Kit (JDK) 21** — install from [Adoptium](https://adoptium.net/)
- **An IDE** — IntelliJ IDEA (Community Edition is free) is the standard choice for Fabric modding
- A Minecraft **Java Edition** account (for the game itself) — Fabric mods only work with the Java Edition

## How to open & run this project
1. Unzip this project somewhere.
2. Open IntelliJ IDEA → **Open** → select the unzipped `xp-name-mod` folder.
3. Let Gradle sync (this downloads Minecraft, mappings, Fabric Loader, and
   Fabric API automatically — first sync can take a few minutes).
4. In IntelliJ's Gradle panel (right sidebar), run the task
   `xp-name-mod > Tasks > fabric > runClient`. This launches a dev instance
   of Minecraft with your mod loaded.
5. Once it works in the dev client, build a real jar with the Gradle task
   `build`. The finished mod jar shows up in `build/libs/xp-name-mod-1.0.0.jar`
   — drop that into your normal `.minecraft/mods` folder (with Fabric Loader
   + Fabric API installed) to use it in your real game.

## If the build fails (mixin target not found)
The one part of this mod that's sensitive to your exact Minecraft version is
`InGameHudMixin.java` — it hooks into a vanilla method called
`renderExperienceLevel` inside `InGameHud`. This method name has been stable
across recent 1.21.x versions, but if Loom complains it can't find it:

1. In IntelliJ, open `InGameHud.class` (Ctrl+Click into it from the mixin
   file, or search "InGameHud" with Ctrl+N/Ctrl+Shift+N).
2. Look for the method that draws the level number (search for something
   drawing text near the XP bar / a method with "Experience" in the name).
3. Update the `method = "renderExperienceLevel"` line in `InGameHudMixin.java`
   to match the real name if it's different.

## Using it on a different 1.21.x version (1.21.5–1.21.10)
Just change `minecraft_version` in `gradle.properties` to your target version,
and update `yarn_mappings` and `fabric_version` to match (check current values
at https://fabricmc.net/develop/ — pick "Yarn" and "Fabric API" for your MC
version). The Java code itself almost never needs to change between these
close 1.21.x versions.

## Project structure
```
xp-name-mod/
├── build.gradle              - build config
├── gradle.properties         - version numbers (edit these to retarget MC version)
├── settings.gradle
├── LICENSE
└── src/main/
    ├── java/com/xpnamemod/
    │   ├── XpNameModClient.java      - mod entrypoint
    │   └── mixin/InGameHudMixin.java - the actual name-swap logic
    └── resources/
        ├── fabric.mod.json           - mod metadata
        └── xpnamemod.mixins.json     - mixin config
```
