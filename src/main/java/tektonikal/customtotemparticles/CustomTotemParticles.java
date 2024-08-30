package tektonikal.customtotemparticles;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.fabricmc.api.ModInitializer;
import tektonikal.customtotemparticles.config.ParticleEnum;
import tektonikal.customtotemparticles.config.TimingMode;
import tektonikal.customtotemparticles.config.YACLConfig;

import java.awt.*;
import java.util.Arrays;

import static tektonikal.customtotemparticles.config.YACLConfig.*;


public class CustomTotemParticles implements ModInitializer {

    @Override
    public void onInitialize() {
        YACLConfig.CONFIG.load();
        armSecuritySystem();
        unleashHell();
    }

    private static void armSecuritySystem() {
        //can't add listeners while options are created for my use-case, since not everything is fully initialized
        //TODO: use reflection here
        YACLConfig.o_modEnabled.addListener(YACLConfig::update);
        YACLConfig.o_useEmitter.addListener(YACLConfig::update);
        YACLConfig.o_useColor.addListener(YACLConfig::update);
        YACLConfig.o_doStartColor.addListener(YACLConfig::update);
        YACLConfig.o_doOutColor.addListener(YACLConfig::update);
        YACLConfig.o_doRainbow.addListener(YACLConfig::update);
        YACLConfig.o_rainbowOverTime.addListener(YACLConfig::update);
        YACLConfig.o_useRainbowGradient.addListener(YACLConfig::update);
        YACLConfig.o_useAlpha.addListener(YACLConfig::update);
        YACLConfig.o_loseAlpha.addListener(YACLConfig::update);
        YACLConfig.o_fadeOnGround.addListener(YACLConfig::update);
        YACLConfig.o_useScale.addListener(YACLConfig::update);
        YACLConfig.o_scaleOverTime.addListener(YACLConfig::update);
        YACLConfig.o_scaleOnGround.addListener(YACLConfig::update);
        YACLConfig.o_useAge.addListener(YACLConfig::update);
        YACLConfig.o_useMovement.addListener(YACLConfig::update);
        YACLConfig.o_customVelocity.addListener(YACLConfig::update);
        YACLConfig.o_useGravity.addListener(YACLConfig::update);
        YACLConfig.o_gravityOverTime.addListener(YACLConfig::update);
        YACLConfig.o_useRotation.addListener(YACLConfig::update);
        YACLConfig.o_rotateOverTime.addListener(YACLConfig::update);
        YACLConfig.o_useGradients.addListener(YACLConfig::update);
    }

    //i genuinely don't know why i have to do this or why it works, as long as it works i will not touch it
    public static void unleashHell() {
        Arrays.stream(YACLConfig.class.getDeclaredFields()).filter(field -> field.getName().startsWith("o_")).forEach(field -> {
            try {
                ((Option) field.get(null)).requestSet(((Option<?>) field.get(null)).binding().getValue());
            } catch (IllegalAccessException e) {
                System.out.println("what the hell");
            }
        });
    }

    public static void randomizeOptions() {
        Arrays.stream(YACLConfig.class.getDeclaredFields()).filter(field -> field.getName().startsWith("o_")).forEach(field -> {
            try {
                if (field.get(null).equals(o_modEnabled)) {
                    o_modEnabled.requestSet(true);
                    return;
                }
                if (field.get(null).equals(YACLConfig.o_showOwnParticles)) {
                    return;
                }
                if (field.get(null).equals(YACLConfig.o_useColor)) {
                    o_useColor.requestSet(true);
                    return;
                }
                if (field.get(null).equals(YACLConfig.o_useScale)) {
                    o_useScale.requestSet(true);
                    return;
                }
                if (field.get(null).equals(YACLConfig.o_useMovement)) {
                    o_useMovement.requestSet(true);
                    return;
                }
                if (field.get(null).equals(o_mainColorList)) {
                    Color[] cols = new Color[Utils.SafeRandom(3, 5)];
                    for (int i = 0; i < cols.length; i++) {
                        cols[i] = new Color((int) (Math.random() * 0x1000000));
                    }
                    o_mainColorList.requestSet(Arrays.asList(cols));
                    return;
                }
                if (field.get(null).equals(o_particleType)) {
                    o_particleType.requestSet(ParticleEnum.TOTEM_OF_UNDYING);
                    return;
                }
                switch (((Option<?>) field.get(null)).controller().getClass().getCanonicalName()) {
                    case "dev.isxander.yacl3.gui.controllers.TickBoxController":
                        ((Option<Boolean>) field.get(null)).requestSet(Math.random() > 0.5);
                        break;
                    case "dev.isxander.yacl3.gui.controllers.ColorController":
                        ((Option<Color>) field.get(null)).requestSet(new Color((int) (Math.random() * 0x1000000)));
                        break;
                    case "dev.isxander.yacl3.gui.controllers.slider.FloatSliderController":
                        ((Option<Float>) field.get(null)).requestSet(Utils.SafeRandom((float) ((FloatSliderController) ((Option<Float>) field.get(null)).controller()).min(), (float) ((FloatSliderController) ((Option<Float>) field.get(null)).controller()).max()));
                        break;
                    case "dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController":
                        ((Option<Integer>) field.get(null)).requestSet(Utils.SafeRandom((int) ((IntegerSliderController) ((Option<Integer>) field.get(null)).controller()).min(), (int) ((IntegerSliderController) ((Option<Integer>) field.get(null)).controller()).max()));
                        break;
                    case "dev.isxander.yacl3.gui.controllers.cycling.EnumController":
                        ((Option<TimingMode>) field.get(null)).requestSet(TimingMode.values()[Utils.SafeRandom(0, TimingMode.values().length - 1)]);
                        break;
                    default:
                        System.out.println("huhhhhh????");
                }
            } catch (IllegalAccessException e) {
                System.out.println("Something has gone slightly less terribly, but still wrong!!!");
            } catch (UnsupportedOperationException e) {
                System.out.println("SOMETHING HAS GONE TERRIBLY WRONG!!!");
            }
        });
    }

}
/*
todo:
        add new particle types - V3
        gradients - V3
        improvements to rainbow - V3
        profiles - later
        replace texture instead of particle - later, this shit looks hard as fuck
        reflection for listeners - nahhhhh
        localization file - laterrrrrr
 */
