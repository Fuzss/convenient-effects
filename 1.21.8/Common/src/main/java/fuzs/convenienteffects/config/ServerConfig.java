package fuzs.convenienteffects.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

public class ServerConfig implements ConfigCore {
    @Config(description = "Blindness effect is affected by potion level and affects detection range of mobs.")
    public boolean strongerBlindness = true;
    @Config(description = "Fire resistance prevents the entity from keeping on burning after stepping out of the fire source.")
    public boolean noFireResistanceBurnTime = true;
    @Config(description = "Allow falling at normal speed while the slow falling effect is active when sneaking. The player will still not take any fall damage.")
    public boolean slowFallingQuickDescent = true;
}
