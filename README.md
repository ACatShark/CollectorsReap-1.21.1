# [Collector's Reap](https://www.curseforge.com/minecraft/mc-mods/collectors-reap)
Farmer's Delight addon with foods scavenged from the wild.

<img src="https://i.imgur.com/PumpIKL.png" width="50%" height="auto" alt="Pescatarian Update Promo">

<a href="https://www.curseforge.com/minecraft/mc-mods/collectors-reap">
  <img src="https://cf.way2muchnoise.eu/full_807101_downloads.svg" alt="Curseforge Downloads">
</a>

[![1.20.1](https://github.com/brnbrd/CollectorsReap/actions/workflows/build-1.20.1.yml/badge.svg?branch=1.20.1)](https://github.com/brnbrd/CollectorsReap/actions/workflows/build-1.20.1.yml)

## 1.21.1 NeoForge Port
This branch has been ported to **NeoForge 1.21.1** (Minecraft 1.21.1, loaders >= 21.1).

- Requires NeoForge `21.1.x`, Farmer's Delight `1.21.1-1.2.3+` and Blueprint `8.1.x+`.
- Optional integrations: Neapolitan, Boatload, Brewin' and Chewin', Jade, JEI, Farmer's Respite.
- Build with JDK 21: `./gradlew build` (or `gradlew.bat build` on Windows).
- Regenerate data (recipes/loot/tags/models) with `./gradlew runData` after building.
- Farmer's Respite 1.21.1 jar can be placed in `./libs/` and is picked up automatically.
