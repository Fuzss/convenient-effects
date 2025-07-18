package fuzs.convenienteffects.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

public class CommonConfig implements ConfigCore {
    @Config(description = "Additionally jump boost will allow entities to walk up blocks without jumping, including full blocks at a given effect strength.", gameRestart = true)
    public boolean jumpBoostIncreasesStepHeight = true;
}
