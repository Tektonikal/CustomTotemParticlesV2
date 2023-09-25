package tektonikal.customtotemparticles;

import net.fabricmc.api.ModInitializer;
import tektonikal.customtotemparticles.config.YACLConfig;

public class CustomTotemParticles implements ModInitializer {
    @Override
    public void onInitialize() {
        YACLConfig.INSTANCE.load();
    }
}
