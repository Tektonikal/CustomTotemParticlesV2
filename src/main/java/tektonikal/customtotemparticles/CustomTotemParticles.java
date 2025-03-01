package tektonikal.customtotemparticles;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.fabricmc.api.ModInitializer;
import tektonikal.customtotemparticles.annotation.Updatable;
import tektonikal.customtotemparticles.config.ParticleEnum;
import tektonikal.customtotemparticles.config.TimingMode;
import tektonikal.customtotemparticles.config.YACLConfig;

import java.awt.*;
import java.util.Arrays;

import static tektonikal.customtotemparticles.config.YACLConfig.*;


@SuppressWarnings({"deprecation", "unchecked"})
public class CustomTotemParticles implements ModInitializer {

    @Override
    public void onInitialize() {
        YACLConfig.CONFIG.load();
        armSecuritySystem();
        unleashHell();
    }

    private static void armSecuritySystem() {
        //can't add listeners while options are created for my use-case, since not everything is fully initialized

        Arrays.stream(YACLConfig.class.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Updatable.class))
                .forEach(field -> {
                    try {
                        ((Option<Boolean>) field.get(null)).addListener(YACLConfig::update);
                    } catch (Exception x) {
                        throw new RuntimeException(x);
                    }
                });
    }

    //i genuinely don't know why i have to do this or why it works, as long as it works i will not touch it
    @SuppressWarnings("rawtypes")
    public static void unleashHell() {
        Arrays.stream(YACLConfig.class.getDeclaredFields())
                .filter(field -> field.getName().startsWith("o_"))
                .forEach(field -> {
            try {
                Object value = field.get(null);
                ((Option) value).requestSet(((Option<?>) value).binding().getValue());
            } catch (IllegalAccessException e) {
                System.out.println("what the hell");
            }
        });
    }

    public static void randomizeOptions() {
        Arrays.stream(YACLConfig.class.getDeclaredFields())
                .filter(field -> field.getName().startsWith("o_"))
                .forEach(field -> {
                    try {
                        Object value = field.get(null);
                        if (value.equals(o_modEnabled)) {
                            o_modEnabled.requestSet(true);
                            return;
                        }
                        if (value.equals(YACLConfig.o_showOwnParticles)) {
                            return;
                        }
                        if (value.equals(YACLConfig.o_useColor)) {
                            o_useColor.requestSet(true);
                            return;
                        }
                        if (value.equals(YACLConfig.o_useScale)) {
                            o_useScale.requestSet(true);
                            return;
                        }
                        if (value.equals(YACLConfig.o_useMovement)) {
                            o_useMovement.requestSet(true);
                            return;
                        }
                        if (value.equals(o_mainColorList)) {
                            Color[] cols = new Color[Utils.SafeRandom(3, 5)];
                            for (int i = 0; i < cols.length; i++) {
                                cols[i] = new Color((int) (Math.random() * 0x1000000));
                            }
                            o_mainColorList.requestSet(Arrays.asList(cols));
                            return;
                        }
                        if (value.equals(o_particleType)) {
                            o_particleType.requestSet(ParticleEnum.TOTEM_OF_UNDYING);
                            return;
                        }
                        switch (((Option<?>) value).controller().getClass().getCanonicalName()) {
                            case "dev.isxander.yacl3.gui.controllers.TickBoxController":
                                ((Option<Boolean>) value).requestSet(Math.random() > 0.5);
                                break;
                            case "dev.isxander.yacl3.gui.controllers.ColorController":
                                ((Option<Color>) value).requestSet(new Color((int) (Math.random() * 0x1000000)));
                                break;
                            case "dev.isxander.yacl3.gui.controllers.slider.FloatSliderController":
                                ((Option<Float>) value).requestSet(Utils.SafeRandom((float) ((FloatSliderController) ((Option<Float>) value).controller()).min(), (float) ((FloatSliderController) ((Option<Float>) value).controller()).max()));
                                break;
                            case "dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController":
                                ((Option<Integer>) value).requestSet(Utils.SafeRandom((int) ((IntegerSliderController) ((Option<Integer>) value).controller()).min(), (int) ((IntegerSliderController) ((Option<Integer>) value).controller()).max()));
                                break;
                            case "dev.isxander.yacl3.gui.controllers.cycling.EnumController":
                                ((Option<TimingMode>) value).requestSet(TimingMode.values()[Utils.SafeRandom(0, TimingMode.values().length - 1)]);
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
        localization file - laterrrrrr
 */
