# TempFly

TempFly is a highly configurable spigot/bukkit plugin for minecraft that introduces many flight related features to the game.
Players can be given flight time that is used when they fly and can be payed to other players like a currency.

# Developers:
TempFly can be hooked into using the TempFlyAPI. this can be aquired using TempFly.getAPI().
There is lots of documentation in the source code that explains what to do but if you still have problems i made a small tutorial

https://www.youtube.com/watch?v=wERiwqX-Wmw

## Paper 26.1.2 and newer

TempFly 3.1.8 targets Paper 26.1.2 and has also been compiled against Paper 26.2.
Build it with Java 25 from the `TempFly` directory using `mvn package`. The plugin jar
is `target/TempFly-3.1.8.jar`. Java 25 is also required by Paper 26.1+.

This update uses the public server API for action bars and titles. Existing player
data and config files are kept. An old `VILLAGER_HAPPY` particle setting or saved
trail is accepted as `HAPPY_VILLAGER`.

The old IridiumSkyblock 2.6.5 and MVdWPlaceholderAPI adapters are excluded from
this build because their APIs and Maven artifacts are obsolete. PlaceholderAPI,
Vault, WorldGuard, FactionsUUID, ASkyBlock, BentoBox, and SuperiorSkyblock hooks
remain optional. Test those integrations with the exact versions installed on your
server before using them in production.
