package tektonikal.customtotemparticles.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.config.v2.impl.serializer.GsonConfigSerializer;
import dev.isxander.yacl3.impl.controller.ColorControllerBuilderImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import tektonikal.customtotemparticles.CustomTotemParticles;
import tektonikal.customtotemparticles.annotation.Updatable;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


public class YACLConfig {
    public static final ConfigClassHandler<YACLConfig> CONFIG = ConfigClassHandler.createBuilder(YACLConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("customtotemparticles.json"))
                    .build())
            .build();
    public static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .serializeNulls()
            .registerTypeHierarchyAdapter(Color.class, new GsonConfigSerializer.ColorTypeAdapter())
            .setPrettyPrinting()
            .create();
    //@formatter:off
    //General
    @SerialEntry public boolean modEnabled = true;
        @SerialEntry public ParticleEnum particleType = ParticleEnum.TOTEM_OF_UNDYING;
        @SerialEntry public float multiplier = 0.9F;
        @SerialEntry public boolean showOwnParticles = true;
    //Emitter
        @SerialEntry public boolean useEmitter = true;
            @SerialEntry public int emitterLifetime = 25;
            @SerialEntry public float emitterYOffset = -0.2F;
            @SerialEntry public boolean emitterMovesWithPlayer = false;
    //Miscellaneous
        @SerialEntry public boolean hideOnGround = false;
        @SerialEntry public boolean useCollisions = true;
        @SerialEntry public int lightLevel = 255;
    //Config
    //nothing here
    //Color
        @SerialEntry public boolean useColor = true;
            @SerialEntry public boolean blendColors = true;
    //Starting Color
            @SerialEntry public boolean doStartColor = true;
                @SerialEntry public Color startColor = java.awt.Color.decode("#FFFFFF");
                @SerialEntry public float fadeToSpeed = 0.3f;
                @SerialEntry public float fadeToTime = 0F;
    //Main Color
            @SerialEntry public List<Color> mainColorList = Arrays.asList(new Color(255, 0, 255), new Color(0, 0, 255));
    //Color Transition 2
            @SerialEntry public boolean doOutColor = true;
                @SerialEntry public float fadeOutSpeed = 0.2f;
                @SerialEntry public float fadeOutTime = 0.65F;
                @SerialEntry public Color outTargetColor = java.awt.Color.decode("#000000");
    //Rainbow
            @SerialEntry public boolean doRainbow = false;
                @SerialEntry public boolean startColorRainbow = true;
                @SerialEntry public boolean rainbowOverTime = true;
                    @SerialEntry public TimingMode rainbowMode = TimingMode.MAIN;
                    @SerialEntry public int rainbowSpeed = 2;
                    @SerialEntry public boolean syncRainbow = true;
                    @SerialEntry public boolean useRainbowGradient = true;
                        @SerialEntry public int rainbowGradientDelay = 150;
    //Gradients
            @SerialEntry public boolean useGradients = true;
                @SerialEntry public boolean gradientFromMainCols = true;
                @SerialEntry public Color variationAmount = new Color(64, 64, 64);
    //Alpha
            @SerialEntry public boolean useAlpha = true;
                @SerialEntry public float minAlpha = 0.75f;
                @SerialEntry public float maxAlpha = 1f;
    //Fade Out
                @SerialEntry public boolean loseAlpha = true;
                    @SerialEntry public float alphaOutSpeed = -0.03f;
                    @SerialEntry public float alphaOutTime = 0.50f;
    //Fade On Ground
                @SerialEntry public boolean fadeOnGround = true;
                    @SerialEntry public float onGroundFade = -0.05F;
    //Scale & Age
        @SerialEntry public boolean useScale = true;
            @SerialEntry public float minScale = 0.25f;
            @SerialEntry public float maxScale = 0.75f;
    //Scale Over Time
            @SerialEntry public boolean scaleOverTime = true;
                @SerialEntry public float scaleAmount = -0.02F;
                @SerialEntry public float scaleAtPercent = 0.75f;
    //Scale On Ground
            @SerialEntry public boolean scaleOnGround = true;
                @SerialEntry public float onGroundScale = -0.01F;
    //Age
        @SerialEntry public boolean useAge = true;
            @SerialEntry public int minAge = 40;
            @SerialEntry public int maxAge = 45;
    //Movement
        @SerialEntry public boolean useMovement = true;
            @SerialEntry public float minVelocityMultiplier = 0.25f;
            @SerialEntry public float maxVelocityMultiplier = 0.60f;
    //Custom Velocity
            @SerialEntry public boolean customVelocity = true;
                @SerialEntry public float minXVelocity = -0.65F;
                @SerialEntry public float maxXVelocity = 0.65F;
                @SerialEntry public float minYVelocity = 0.25F;
                @SerialEntry public float maxYVelocity = 1.5F;
                @SerialEntry public float minZVelocity = -0.65F;
                @SerialEntry public float maxZVelocity = 0.65F;
    //Gravity
            @SerialEntry public boolean useGravity = true;
                @SerialEntry public float minUpwardsAccel = -0.3f;
                @SerialEntry public float maxUpwardsAccel = 0.75f;
    //Gravity Over Time
                @SerialEntry public boolean gravityOverTime = true;
                    @SerialEntry public float changeGravityAtPercent = 0.65f;
                    @SerialEntry public float gravityOverTimeAmount = -0.5f;
    //Rotation
            @SerialEntry public boolean useRotation = true;
                @SerialEntry public int minStartRotation = -360;
                @SerialEntry public int maxStartRotation = 360;
    //Rotate Over Time
                @SerialEntry public boolean rotateOverTime = true;
                    @SerialEntry public float minRotationSpeed = -0.35f;
                    @SerialEntry public float maxRotationSpeed = 0.35f;
                    @SerialEntry public float rotateAtPercent = 0.7F;
                    @SerialEntry public float rotateOverTimeAmount = -0.15F;
                    @SerialEntry public boolean smartROT = true;
                    @SerialEntry public boolean rotateOnGround = false;
    //@formatter:on
    //begin programming war crime
    @Updatable
    public static Option<Boolean> o_modEnabled = Option.<Boolean>createBuilder()
            .name(Text.of("Mod Enabled"))
            .description(OptionDescription.of(Text.of("Enables the mod globally.")))
            .binding(true, () -> CONFIG.instance().modEnabled, newVal -> CONFIG.instance().modEnabled = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<ParticleEnum> o_particleType = Option.<ParticleEnum>createBuilder()
            .name(Text.of("Particle Type"))
            .description(particleEnum -> OptionDescription.createBuilder()
                    .text(Text.of("[DEPRECATED] Replaces the totem particle with a different one. The particles will retain their default properties. It's suggested to instead just provide your own textures."))
                    .image(particleEnum.getIdentifier(), 1, 1)
                    .build())
            .binding(ParticleEnum.TOTEM_OF_UNDYING, () -> CONFIG.instance().particleType, newVal -> CONFIG.instance().particleType = newVal)
            .controller(option -> EnumControllerBuilder.create(option).enumClass(ParticleEnum.class))
            .build();
    public static Option<Float> o_multiplier = Option.<Float>createBuilder()
            .name(Text.of("Multiplier"))
            .description(OptionDescription.of(Text.of("Multiplier for the amount of particles emitted.")))
            .binding(0.9f, () -> CONFIG.instance().multiplier, newVal -> CONFIG.instance().multiplier = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 5f).step(0.1f).formatValue(val -> {
                if (val == 0) {
                    return Text.of("Just One");
                }
                return Text.of(String.format("%.1f", val) + "x");
            }))
            .build();
    public static Option<Boolean> o_showOwnParticles = Option.<Boolean>createBuilder()
            .name(Text.of("Show Own Particles"))
            .description(OptionDescription.of(Text.of("Whether you can see totem particles coming from you.")))
            .binding(true, () -> CONFIG.instance().showOwnParticles, newVal -> CONFIG.instance().showOwnParticles = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    @Updatable
    public static Option<Boolean> o_useEmitter = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Enables this category.")))
            .binding(true, () -> CONFIG.instance().useEmitter, newVal -> CONFIG.instance().useEmitter = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Integer> o_emitterLifetime = Option.<Integer>createBuilder()
            .name(Text.of("Emitter Lifetime"))
            .description(OptionDescription.of(Text.of("How many ticks particles are emitted for. Use this if you want a lot of particles over a long period of time.")))
            .binding(25, () -> CONFIG.instance().emitterLifetime, newVal -> CONFIG.instance().emitterLifetime = newVal)
            .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption).range(0, 50).step(5).formatValue(value -> Text.of(value + " Ticks")))
            .build();
    public static Option<Float> o_emitterYOffset = Option.<Float>createBuilder()
            .name(Text.of("Vertical Offset"))
            .description(OptionDescription.of(Text.of("Offsets the emitter vertically.")))
            .binding(-0.2f, () -> CONFIG.instance().emitterYOffset, newVal -> CONFIG.instance().emitterYOffset = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.8f, 2f).step(0.1f).formatValue(val -> Text.of(String.format("%.1f", val) + " Blocks")))
            .build();
    public static Option<Boolean> o_emitterMovesWithPlayer = Option.<Boolean>createBuilder()
            .name(Text.of("Follow"))
            .description(OptionDescription.of(Text.of("Whether the emitter follows the entity bound to it.")))
            .binding(false, () -> CONFIG.instance().emitterMovesWithPlayer, newVal -> CONFIG.instance().emitterMovesWithPlayer = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Integer> o_lightLevel = Option.<Integer>createBuilder()
            .name(Text.of("Light Level"))
            .description(OptionDescription.of(Text.of("How brightly the particle is rendered. If set to -1, it will render based on the world's light level.")))
            .binding(255, () -> CONFIG.instance().lightLevel, newVal -> CONFIG.instance().lightLevel = newVal)
            .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption).range(-1, 255).step(1))
            .build();
    public static Option<Boolean> o_hideOnGround = Option.<Boolean>createBuilder()
            .name(Text.of("Hide On Ground"))
            .description(OptionDescription.of(Text.of("Hides the particle when it touches the ground")))
            .binding(false, () -> CONFIG.instance().hideOnGround, newVal -> CONFIG.instance().hideOnGround = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Boolean> o_useCollisions = Option.<Boolean>createBuilder()
            .name(Text.of("Particle Collisions"))
            .description(OptionDescription.of(Text.of("Whether the particle will collide with blocks.")))
            .binding(true, () -> CONFIG.instance().useCollisions, newVal -> CONFIG.instance().useCollisions = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    @Updatable
    public static Option<Boolean> o_useColor = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Enables this category.")))
            .binding(true, () -> CONFIG.instance().useColor, newVal -> CONFIG.instance().useColor = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Boolean> o_blendColors = Option.<Boolean>createBuilder()
            .name(Text.of("Blend Colors"))
            .description(OptionDescription.of(Text.of("Enable the starting color to be a random color between the 2 starting colors.")))
            .binding(true, () -> CONFIG.instance().blendColors, newVal -> CONFIG.instance().blendColors = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    @Updatable
    public static Option<Boolean> o_doStartColor = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Enables a start color, that will fade into the main one.")))
            .binding(true, () -> CONFIG.instance().doStartColor, newVal -> CONFIG.instance().doStartColor = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Color> o_StartColor = Option.<Color>createBuilder()
            .name(Text.of("Starting Color"))
            .description(OptionDescription.of(Text.of("The color to fade to.")))
            .binding(Color.decode("#FFFFFF"), () -> CONFIG.instance().startColor, newVal -> CONFIG.instance().startColor = newVal)
            .controller(ColorControllerBuilder::create)
            .build();
    public static Option<Float> o_fadeToSpeed = Option.<Float>createBuilder()
            .name(Text.of("Speed"))
            .description(OptionDescription.of(Text.of("How fast to fade to the color.")))
            .binding(0.3f, () -> CONFIG.instance().fadeToSpeed, newVal -> CONFIG.instance().fadeToSpeed = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0.1f, 1F).step(0.1f).formatValue(val -> Text.of(String.format("%.1f", val) + "x")))
            .build();
    public static Option<Float> o_fadeToTime = Option.<Float>createBuilder()
            .name(Text.of("Fade At %"))
            .description(OptionDescription.of(Text.of("At what percentage of the particle's lifetime to fade to the main color.")))
            .binding(0f, () -> CONFIG.instance().fadeToTime, newVal -> CONFIG.instance().fadeToTime = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 0.5F).step(0.05f).formatValue(val -> Text.of(String.format("%.0f", val * 100) + "%")))
            .build();
    public static ListOption<Color> o_mainColorList = ListOption.<Color>createBuilder()
            .name(Text.of("Main Color"))
            .description(OptionDescription.of(Text.of("The main color for the particle. If \"Blend colors\" is enabled, only the first 2 colors will be used. Otherwise, a random color will be picked.")))
            .controller(ColorControllerBuilder::create)
            .binding(Arrays.asList(new Color(255, 0, 255), new Color(0, 0, 255)), () -> CONFIG.instance().mainColorList, newVal -> CONFIG.instance().mainColorList = newVal)
            .initial(() -> new Color((int) (Math.random() * 0x1000000)))
            .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useColor)
            .build();
    @Updatable
    public static Option<Boolean> o_doOutColor = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Enable a fade out into the final color.")))
            .binding(true, () -> CONFIG.instance().doOutColor, newVal -> CONFIG.instance().doOutColor = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Color> o_outTargetColor = Option.<Color>createBuilder()
            .name(Text.of("Fade To Color"))
            .description(OptionDescription.of(Text.of("The final color the particle will change its color to.")))
            .binding(Color.decode("#000000"), () -> CONFIG.instance().outTargetColor, newVal -> CONFIG.instance().outTargetColor = newVal)
            .controller(ColorControllerBuilder::create)
            .build();
    public static Option<Float> o_fadeOutSpeed = Option.<Float>createBuilder()
            .name(Text.of("Speed"))
            .description(OptionDescription.of(Text.of("The speed at which to fade to the final color at.")))
            .binding(0.2f, () -> CONFIG.instance().fadeOutSpeed, newVal -> CONFIG.instance().fadeOutSpeed = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 1F).step(0.1f).formatValue(val -> Text.of(String.format("%.1f", val) + "x")))
            .build();
    public static Option<Float> o_fadeOutTime = Option.<Float>createBuilder()
            .name(Text.of("Fade At %"))
            .description(OptionDescription.of(Text.of("At what percentage of the particle's lifetime to fade to the final color.")))
            .binding(0.65f, () -> CONFIG.instance().fadeOutTime, newVal -> CONFIG.instance().fadeOutTime = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0.5f, 1F).step(0.05f).formatValue(val -> Text.of(String.format("%.0f", val * 100) + "%")))
            .build();
    @Updatable
    public static Option<Boolean> o_doRainbow = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Enables this category.")))
            .binding(false, () -> CONFIG.instance().doRainbow, newVal -> CONFIG.instance().doRainbow = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Boolean> o_startColorRainbow = Option.<Boolean>createBuilder()
            .name(Text.of("Start Color"))
            .description(OptionDescription.of(Text.of("Sets the starting color of the particle to be a random color of the rainbow.")))
            .binding(false, () -> CONFIG.instance().startColorRainbow, newVal -> CONFIG.instance().startColorRainbow = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    @Updatable
    public static Option<Boolean> o_rainbowOverTime = Option.<Boolean>createBuilder()
            .name(Text.of("Rainbow Over Time"))
            .description(OptionDescription.of(Text.of("Makes the particle change its color over time with rainbow colors.")))
            .binding(false, () -> CONFIG.instance().rainbowOverTime, newVal -> CONFIG.instance().rainbowOverTime = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<TimingMode> o_rainbowMode = Option.<TimingMode>createBuilder()
            .name(Text.of("Rainbow Mode"))
            .description(OptionDescription.of(Text.of("When to enable the rainbow.")))
            .binding(TimingMode.MAIN, () -> CONFIG.instance().rainbowMode, newVal -> CONFIG.instance().rainbowMode = newVal)
            .controller(option -> EnumControllerBuilder.create(option).enumClass(TimingMode.class))
            .build();
    public static Option<Integer> o_rainbowSpeed = Option.<Integer>createBuilder()
            .name(Text.of("Speed"))
            .description(OptionDescription.of(Text.of("How fast the particle changes color.")))
            .binding(2, () -> CONFIG.instance().rainbowSpeed, newVal -> CONFIG.instance().rainbowSpeed = newVal)
            .controller(intOption -> IntegerSliderControllerBuilder.create(intOption).range(1, 10).step(1).formatValue(val -> Text.of(val + "x")))
            .build();
    public static Option<Boolean> o_syncRainbow = Option.<Boolean>createBuilder()
            .name(Text.of("Sync"))
            .description(OptionDescription.of(Text.of("Synchronizes the rainbow state of all particles.")))
            .binding(false, () -> CONFIG.instance().syncRainbow, newVal -> CONFIG.instance().syncRainbow = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    @Updatable
    public static Option<Boolean> o_useRainbowGradient = Option.<Boolean>createBuilder()
            .name(Text.of("Gradient"))
            .description(OptionDescription.of(Text.of("Adds a rainbow gradient to your particles.")))
            .binding(true, () -> CONFIG.instance().useRainbowGradient, newVal -> CONFIG.instance().useRainbowGradient = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Integer> o_rainbowGradientDelay = Option.<Integer>createBuilder()
            .name(Text.of("Delay"))
            .description(OptionDescription.of(Text.of("Rainbow gradient's delay")))
            .binding(150, () -> CONFIG.instance().rainbowGradientDelay, newVal -> CONFIG.instance().rainbowGradientDelay = newVal)
            .controller(intOption -> IntegerSliderControllerBuilder.create(intOption).range(0, 500).step(5))
            .build();
    @Updatable
    public static Option<Boolean> o_useGradients = Option.<Boolean>createBuilder()
            .name(Text.of("Use Gradients"))
            .description(OptionDescription.of(Text.of("Enable gradients.")))
            .binding(true, () -> CONFIG.instance().useGradients, newVal -> CONFIG.instance().useGradients = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Boolean> o_gradientFromMainCols = Option.<Boolean>createBuilder()
            .name(Text.of("Pick From Starting Colors"))
            .binding(true, () -> CONFIG.instance().gradientFromMainCols, newVal -> CONFIG.instance().gradientFromMainCols = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Color> o_variationAmount = Option.<Color>createBuilder()
            .name(Text.of("Variation"))
            .binding(new Color(64, 64, 64), () -> CONFIG.instance().variationAmount, newVal -> CONFIG.instance().variationAmount = newVal)
            .controller(ColorControllerBuilderImpl::new)
            .build();
    @Updatable
    public static Option<Boolean> o_useAlpha = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Whether to modify the particle's alpha value.")))
            .binding(true, () -> CONFIG.instance().useAlpha, newVal -> CONFIG.instance().useAlpha = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_minAlpha = Option.<Float>createBuilder()
            .name(Text.of("Minimum Alpha"))
            .description(OptionDescription.of(Text.of("The minimum starting opacity of the particle.")))
            .binding(0.75f, () -> CONFIG.instance().minAlpha, newVal -> CONFIG.instance().minAlpha = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 1f).step(0.05f).formatValue(val -> Text.of((int) (val * 100) + "%")))
            .build();
    public static Option<Float> o_maxAlpha = Option.<Float>createBuilder()
            .name(Text.of("Maximum alpha"))
            .description(OptionDescription.of(Text.of("The maximum starting opacity of the particle.")))
            .binding(1f, () -> CONFIG.instance().maxAlpha, newVal -> CONFIG.instance().maxAlpha = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 1f).step(0.05f).formatValue(val -> Text.of((int) (val * 100) + "%")))
            .build();
    @Updatable
    public static Option<Boolean> o_loseAlpha = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Whether the particle loses opacity over time.")))
            .binding(true, () -> CONFIG.instance().loseAlpha, newVal -> CONFIG.instance().loseAlpha = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_alphaOutSpeed = Option.<Float>createBuilder()
            .name(Text.of("Speed"))
            .description(OptionDescription.of(Text.of("How fast the particle will fade out.")))
            .binding(-0.03f, () -> CONFIG.instance().alphaOutSpeed, newVal -> CONFIG.instance().alphaOutSpeed = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.1f, 0.1f).step(0.01f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_alphaOutTime = Option.<Float>createBuilder()
            .name(Text.of("Fade At %"))
            .description(OptionDescription.of(Text.of("At what percentage of the particle's lifetime to fade out.")))
            .binding(0.5f, () -> CONFIG.instance().alphaOutTime, newVal -> CONFIG.instance().alphaOutTime = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0F, 1F).step(0.05f).formatValue(val -> Text.of(String.format("%.0f", val * 100) + "%")))
            .build();
    @Updatable
    public static Option<Boolean> o_fadeOnGround = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Whether the particle will change its opacity when it lands on the ground.")))
            .binding(true, () -> CONFIG.instance().fadeOnGround, newVal -> CONFIG.instance().fadeOnGround = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_onGroundFade = Option.<Float>createBuilder()
            .name(Text.of("Speed"))
            .description(OptionDescription.of(Text.of("How fast the particle will fade when on the ground.")))
            .binding(-0.05f, () -> CONFIG.instance().onGroundFade, newVal -> CONFIG.instance().onGroundFade = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.1f, 0.1f).step(0.01f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    @Updatable
    public static Option<Boolean> o_useScale = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Enables this category.")))
            .binding(true, () -> CONFIG.instance().useScale, newVal -> CONFIG.instance().useScale = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_minScale = Option.<Float>createBuilder()
            .name(Text.of("Minimum Scale"))
            .description(OptionDescription.of(Text.of("The minimum starting scale of the particle.")))
            .binding(0.25f, () -> CONFIG.instance().minScale, newVal -> CONFIG.instance().minScale = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0.05f, 2f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_maxScale = Option.<Float>createBuilder()
            .name(Text.of("Maximum Scale"))
            .description(OptionDescription.of(Text.of("The maximum starting scale of the particle.")))
            .binding(0.75f, () -> CONFIG.instance().maxScale, newVal -> CONFIG.instance().maxScale = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0.05f, 2f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    @Updatable
    public static Option<Boolean> o_scaleOverTime = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Change the particle's scale at a certain speed at a certain time.")))
            .binding(true, () -> CONFIG.instance().scaleOverTime, newVal -> CONFIG.instance().scaleOverTime = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_scaleAmount = Option.<Float>createBuilder()
            .name(Text.of("Amount"))
            .description(OptionDescription.of(Text.of("How much to scale the particle.")))
            .binding(-0.02f, () -> CONFIG.instance().scaleAmount, newVal -> CONFIG.instance().scaleAmount = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.1f, 0.1f).step(0.01f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_scaleAtPercent = Option.<Float>createBuilder()
            .name(Text.of("Scale At %"))
            .description(OptionDescription.of(Text.of("At what percent of the particle's lifetime to scale at.")))
            .binding(0.75f, () -> CONFIG.instance().scaleAtPercent, newVal -> CONFIG.instance().scaleAtPercent = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 1F).step(0.05f).formatValue(val -> Text.of(String.format("%.0f", val * 100) + "%")))
            .build();
    @Updatable
    public static Option<Boolean> o_scaleOnGround = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Whether to scale the particle when it collides with the ground.")))
            .binding(true, () -> CONFIG.instance().scaleOnGround, newVal -> CONFIG.instance().scaleOnGround = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_onGroundScale = Option.<Float>createBuilder()
            .name(Text.of("Amount"))
            .description(OptionDescription.of(Text.of("By how much to scale the particle when it collides with the ground.")))
            .binding(-0.01f, () -> CONFIG.instance().onGroundScale, newVal -> CONFIG.instance().onGroundScale = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.2f, 0.2f).step(0.01f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    @Updatable
    public static Option<Boolean> o_useAge = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Whether to enable this category.")))
            .binding(true, () -> CONFIG.instance().useAge, newVal -> CONFIG.instance().useAge = newVal).controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Integer> o_minAge = Option.<Integer>createBuilder()
            .name(Text.of("Minimum Age"))
            .description(OptionDescription.of(Text.of("The particle's minimum age.")))
            .binding(40, () -> CONFIG.instance().minAge, newVal -> CONFIG.instance().minAge = newVal)
            .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption).range(10, 200).step(5).formatValue(val -> Text.of(val + " ticks")))
            .build();
    public static Option<Integer> o_maxAge = Option.<Integer>createBuilder()
            .name(Text.of("Maximum Age"))
            .description(OptionDescription.of(Text.of("The particle's maximum age.")))
            .binding(45, () -> CONFIG.instance().maxAge, newVal -> CONFIG.instance().maxAge = newVal)
            .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption).range(10, 200).step(5).formatValue(val -> Text.of(val + " ticks")))
            .build();
    @Updatable
    public static Option<Boolean> o_useMovement = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Enable this category.")))
            .binding(true, () -> CONFIG.instance().useMovement, newVal -> CONFIG.instance().useMovement = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_minVelocityMultiplier = Option.<Float>createBuilder()
            .name(Text.of("Minimum Velocity Multiplier"))
            .description(OptionDescription.of(Text.of("The minimum starting velocity multiplier of the particle.")))
            .binding(0.25f, () -> CONFIG.instance().minVelocityMultiplier, newVal -> CONFIG.instance().minVelocityMultiplier = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 1f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_maxVelocityMultiplier = Option.<Float>createBuilder()
            .name(Text.of("Maximum Velocity Multiplier"))
            .description(OptionDescription.of(Text.of("The maximum starting velocity multiplier of the particle.")))
            .binding(0.6f, () -> CONFIG.instance().maxVelocityMultiplier, newVal -> CONFIG.instance().maxVelocityMultiplier = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 1f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    @Updatable
    public static Option<Boolean> o_customVelocity = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Whether to apply the velocity parameters below to the particle.")))
            .binding(true, () -> CONFIG.instance().customVelocity, newVal -> CONFIG.instance().customVelocity = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_minXVelocity = Option.<Float>createBuilder()
            .name(Text.of("Minimum X Velocity"))
            .description(OptionDescription.of(Text.of("The minimum starting X velocity multiplier of the particle.")))
            .binding(-0.65f, () -> CONFIG.instance().minXVelocity, newVal -> CONFIG.instance().minXVelocity = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_maxXVelocity = Option.<Float>createBuilder()
            .name(Text.of("Maximum X Velocity"))
            .description(OptionDescription.of(Text.of("The maximum starting X velocity multiplier of the particle.")))
            .binding(0.65f, () -> CONFIG.instance().maxXVelocity, newVal -> CONFIG.instance().maxXVelocity = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_minYVelocity = Option.<Float>createBuilder()
            .name(Text.of("Minimum Y Velocity"))
            .description(OptionDescription.of(Text.of("The minimum starting Y velocity multiplier of the particle.")))
            .binding(0.25f, () -> CONFIG.instance().minYVelocity, newVal -> CONFIG.instance().minYVelocity = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_maxYVelocity = Option.<Float>createBuilder()
            .name(Text.of("Maximum Y Velocity"))
            .description(OptionDescription.of(Text.of("The maximum starting Y velocity multiplier of the particle.")))
            .binding(1.5f, () -> CONFIG.instance().maxYVelocity, newVal -> CONFIG.instance().maxYVelocity = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_minZVelocity = Option.<Float>createBuilder()
            .name(Text.of("Minimum Z Velocity"))
            .description(OptionDescription.of(Text.of("The minimum starting Z velocity multiplier of the particle.")))
            .binding(-0.65f, () -> CONFIG.instance().minZVelocity, newVal -> CONFIG.instance().minZVelocity = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_maxZVelocity = Option.<Float>createBuilder()
            .name(Text.of("Maximum Z Velocity"))
            .description(OptionDescription.of(Text.of("The maximum starting Z velocity multiplier of the particle.")))
            .binding(0.65f, () -> CONFIG.instance().maxZVelocity, newVal -> CONFIG.instance().maxZVelocity = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    @Updatable
    public static Option<Boolean> o_useGravity = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Whether to modify the particle's gravity.")))
            .binding(true, () -> CONFIG.instance().useGravity, newVal -> CONFIG.instance().useGravity = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_minUpwardsAccel = Option.<Float>createBuilder()
            .name(Text.of("Minimum Gravity Multiplier"))
            .description(OptionDescription.of(Text.of("The minimum starting gravity of the particle.")))
            .binding(-0.3f, () -> CONFIG.instance().minUpwardsAccel, newVal -> CONFIG.instance().minUpwardsAccel = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_maxUpwardsAccel = Option.<Float>createBuilder()
            .name(Text.of("Maximum Gravity Multiplier"))
            .description(OptionDescription.of(Text.of("The maximum starting gravity of the particle.")))
            .binding(0.75f, () -> CONFIG.instance().maxUpwardsAccel, newVal -> CONFIG.instance().maxUpwardsAccel = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    @Updatable
    public static Option<Boolean> o_gravityOverTime = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Whether gravity will change over time.")))
            .binding(true, () -> CONFIG.instance().gravityOverTime, newVal -> CONFIG.instance().gravityOverTime = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Float> o_changeGravityAtPercent = Option.<Float>createBuilder()
            .name(Text.of("Change At %"))
            .description(OptionDescription.of(Text.of("At what percentage of the particle's lifetime to change the gravity at.")))
            .binding(0.65f, () -> CONFIG.instance().changeGravityAtPercent, newVal -> CONFIG.instance().changeGravityAtPercent = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 1F).step(0.05f).formatValue(val -> Text.of(String.format("%.0f", val * 100) + "%")))
            .build();
    public static Option<Float> o_gravityOverTimeAmount = Option.<Float>createBuilder()
            .name(Text.of("Amount"))
            .description(OptionDescription.of(Text.of("How much to change the gravity of the particle.")))
            .binding(-0.5f, () -> CONFIG.instance().gravityOverTimeAmount, newVal -> CONFIG.instance().gravityOverTimeAmount = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.5f, 0.5f).step(0.01f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    @Updatable
    public static Option<Boolean> o_useRotation = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Whether to modify the particle's rotation.")))
            .binding(true, () -> CONFIG.instance().useRotation, newVal -> CONFIG.instance().useRotation = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Integer> o_minStartRotation = Option.<Integer>createBuilder()
            .name(Text.of("Minimum Start Rotation"))
            .description(OptionDescription.of(Text.of("The minimum starting rotation of the particle.")))
            .binding(-360, () -> CONFIG.instance().minStartRotation, newVal -> CONFIG.instance().minStartRotation = newVal)
            .controller(intOption -> IntegerSliderControllerBuilder.create(intOption).range(-360, 360).step(5))
            .build();
    public static Option<Integer> o_maxStartRotation = Option.<Integer>createBuilder()
            .name(Text.of("Maximum Start Rotation"))
            .description(OptionDescription.of(Text.of("The maximum starting rotation of the particle.")))
            .binding(360, () -> CONFIG.instance().maxStartRotation, newVal -> CONFIG.instance().maxStartRotation = newVal)
            .controller(intOption -> IntegerSliderControllerBuilder.create(intOption).range(-360, 360).step(5))
            .build();
    @Updatable
    public static Option<Boolean> o_rotateOverTime = Option.<Boolean>createBuilder()
            .name(Text.of("Enabled"))
            .description(OptionDescription.of(Text.of("Enables the particle to rotate over time.")))
            .controller(TickBoxControllerBuilder::create)
            .binding(true, () -> CONFIG.instance().rotateOverTime, newVal -> CONFIG.instance().rotateOverTime = newVal)
            .build();
    public static Option<Float> o_minRotationSpeed = Option.<Float>createBuilder()
            .name(Text.of("Minimum Rotation Speed"))
            .description(OptionDescription.of(Text.of("The minimum starting rotation speed of the particle.")))
            .binding(-0.35f, () -> CONFIG.instance().minRotationSpeed, newVal -> CONFIG.instance().minRotationSpeed = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-1f, 1f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_maxRotationSpeed = Option.<Float>createBuilder()
            .name(Text.of("Maximum Rotation Speed"))
            .description(OptionDescription.of(Text.of("The maximum starting rotation speed of the particle.")))
            .binding(0.35f, () -> CONFIG.instance().maxRotationSpeed, newVal -> CONFIG.instance().maxRotationSpeed = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-1f, 1f).step(0.05f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Float> o_RotateAtPercent = Option.<Float>createBuilder()
            .name(Text.of("Change At %"))
            .description(OptionDescription.of(Text.of("At what percentage of the particle's lifetime to begin changing the rotation speed.")))
            .binding(0.70F, () -> CONFIG.instance().rotateAtPercent, newVal -> CONFIG.instance().rotateAtPercent = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0F, 1F).step(0.05f).formatValue(val -> Text.of(String.format("%.0f", val * 100) + "%")))
            .build();
    public static Option<Float> o_rotateOverTimeAmount = Option.<Float>createBuilder()
            .name(Text.of("Amount"))
            .description(OptionDescription.of(Text.of("How much the rotation speed of the particle is changed by.")))
            .binding(-0.15F, () -> CONFIG.instance().rotateOverTimeAmount, newVal -> CONFIG.instance().rotateOverTimeAmount = newVal)
            .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.5f, 0.5f).step(0.01f).formatValue(val -> Text.of(String.format("%.2f", val))))
            .build();
    public static Option<Boolean> o_smartROT = Option.<Boolean>createBuilder()
            .name(Text.of("Smart Mode"))
            .description(OptionDescription.of(Text.of("If the amount is set to a negative value, particles will slow down until they stop, otherwise they will gain speed.")))
            .binding(true, () -> CONFIG.instance().smartROT, newVal -> CONFIG.instance().smartROT = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();
    public static Option<Boolean> o_rotateOnGround = Option.<Boolean>createBuilder()
            .name(Text.of("Rotate On Ground"))
            .description(OptionDescription.of(Text.of("Whether the particle will rotate on the ground.")))
            .binding(false, () -> CONFIG.instance().rotateOnGround, newVal -> CONFIG.instance().rotateOnGround = newVal)
            .controller(TickBoxControllerBuilder::create)
            .build();

    public static void update(Option<Boolean> booleanOption, Boolean aBoolean) {
        //This is extremely cursed, but it works! The options trigger their listeners upon being set to unavailable, and that pretty much recursively disables everything in their category.
        //I could just get the option groups, then set everything except the "Enabled" option to be disabled, but that approach has its own fair share of problems.
        //Either way, I've spoken to isXander about this, and he says he'll be implementing better ways to achieve results like this.
        if (booleanOption.equals(o_modEnabled)) {
            o_useEmitter.setAvailable(aBoolean);
            o_useColor.setAvailable(aBoolean);
            o_doStartColor.setAvailable(aBoolean);
            o_doOutColor.setAvailable(aBoolean);
            o_useAlpha.setAvailable(aBoolean);
            o_loseAlpha.setAvailable(aBoolean);
            o_fadeOnGround.setAvailable(aBoolean);
            o_useScale.setAvailable(aBoolean);
            o_scaleOverTime.setAvailable(aBoolean);
            o_scaleOnGround.setAvailable(aBoolean);
            o_useAge.setAvailable(aBoolean);
            o_useMovement.setAvailable(aBoolean);
            o_customVelocity.setAvailable(aBoolean);
            o_useGravity.setAvailable(aBoolean);
            o_gravityOverTime.setAvailable(aBoolean);
            o_useRotation.setAvailable(aBoolean);
            o_rotateOverTime.setAvailable(aBoolean);

            o_particleType.setAvailable(aBoolean);
            o_multiplier.setAvailable(aBoolean);
            o_showOwnParticles.setAvailable(aBoolean);
            o_hideOnGround.setAvailable(aBoolean);
            o_useCollisions.setAvailable(aBoolean);
            o_lightLevel.setAvailable(aBoolean);
        } else if (booleanOption.equals(o_useEmitter)) {
            o_emitterLifetime.setAvailable(o_useEmitter.available() && aBoolean);
            o_emitterYOffset.setAvailable(o_useEmitter.available() && aBoolean);
            o_emitterMovesWithPlayer.setAvailable(o_useEmitter.available() && aBoolean);
        } else if (booleanOption.equals(o_useColor)) {
            o_blendColors.setAvailable(o_useColor.available() && aBoolean);
            o_mainColorList.setAvailable(o_useColor.available() && aBoolean);
            o_doStartColor.setAvailable(o_useColor.available() && aBoolean);
            o_doOutColor.setAvailable(o_useColor.available() && aBoolean);
            o_useAlpha.setAvailable(o_useColor.available() && aBoolean);
            o_useGradients.setAvailable(o_useColor.available() && aBoolean);
        } else if (booleanOption.equals(o_doStartColor)) {
            o_StartColor.setAvailable(o_doStartColor.available() && aBoolean);
            o_fadeToSpeed.setAvailable(o_doStartColor.available() && aBoolean);
            o_fadeToTime.setAvailable(o_doStartColor.available() && aBoolean);
        } else if (booleanOption.equals(o_doOutColor)) {
            o_fadeOutSpeed.setAvailable(o_doOutColor.available() && aBoolean);
            o_fadeOutTime.setAvailable(o_doOutColor.available() && aBoolean);
            o_outTargetColor.setAvailable(o_doOutColor.available() && aBoolean);
        } else if (booleanOption.equals(o_useAlpha)) {
            o_minAlpha.setAvailable(o_useAlpha.available() && aBoolean);
            o_maxAlpha.setAvailable(o_useAlpha.available() && aBoolean);
            o_loseAlpha.setAvailable(o_useAlpha.available() && aBoolean);
            o_fadeOnGround.setAvailable(o_useAlpha.available() && aBoolean);
        } else if (booleanOption.equals(o_loseAlpha)) {
            o_alphaOutSpeed.setAvailable(o_loseAlpha.available() && aBoolean);
            o_alphaOutTime.setAvailable(o_loseAlpha.available() && aBoolean);
        } else if (booleanOption.equals(o_fadeOnGround)) {
            o_onGroundFade.setAvailable(o_fadeOnGround.available() && aBoolean);
        } else if (booleanOption.equals(o_useScale)) {
            o_minScale.setAvailable(o_useScale.available() && aBoolean);
            o_maxScale.setAvailable(o_useScale.available() && aBoolean);
            o_scaleOverTime.setAvailable(o_useScale.available() && aBoolean);
            o_scaleOnGround.setAvailable(o_useScale.available() && aBoolean);
        } else if (booleanOption.equals(o_scaleOverTime)) {
            o_scaleAmount.setAvailable(o_scaleOverTime.available() && aBoolean);
            o_scaleAtPercent.setAvailable(o_scaleOverTime.available() && aBoolean);
        } else if (booleanOption.equals(o_scaleOnGround)) {
            o_onGroundScale.setAvailable(o_scaleOnGround.available() && aBoolean);
        } else if (booleanOption.equals(o_useAge)) {
            o_minAge.setAvailable(o_useAge.available() && aBoolean);
            o_maxAge.setAvailable(o_useAge.available() && aBoolean);
        } else if (booleanOption.equals(o_useMovement)) {
            o_minVelocityMultiplier.setAvailable(o_useMovement.available() && aBoolean);
            o_maxVelocityMultiplier.setAvailable(o_useMovement.available() && aBoolean);
            o_customVelocity.setAvailable(o_useMovement.available() && aBoolean);
            o_useGravity.setAvailable(o_useMovement.available() && aBoolean);
            o_useRotation.setAvailable(o_useMovement.available() && aBoolean);
        } else if (booleanOption.equals(o_customVelocity)) {
            o_minXVelocity.setAvailable(o_customVelocity.available() && aBoolean);
            o_maxXVelocity.setAvailable(o_customVelocity.available() && aBoolean);
            o_minYVelocity.setAvailable(o_customVelocity.available() && aBoolean);
            o_maxYVelocity.setAvailable(o_customVelocity.available() && aBoolean);
            o_minZVelocity.setAvailable(o_customVelocity.available() && aBoolean);
            o_maxZVelocity.setAvailable(o_customVelocity.available() && aBoolean);
        } else if (booleanOption.equals(o_useGravity)) {
            o_minUpwardsAccel.setAvailable(o_useGravity.available() && aBoolean);
            o_maxUpwardsAccel.setAvailable(o_useGravity.available() && aBoolean);
            o_gravityOverTime.setAvailable(o_useGravity.available() && aBoolean);
        } else if (booleanOption.equals(o_gravityOverTime)) {
            o_changeGravityAtPercent.setAvailable(o_gravityOverTime.available() && aBoolean);
            o_gravityOverTimeAmount.setAvailable(o_gravityOverTime.available() && aBoolean);
        } else if (booleanOption.equals(o_useRotation)) {
            o_minStartRotation.setAvailable(o_useRotation.available() && aBoolean);
            o_maxStartRotation.setAvailable(o_useRotation.available() && aBoolean);
            o_rotateOverTime.setAvailable(o_useRotation.available() && aBoolean);
        } else if (booleanOption.equals(o_rotateOverTime)) {
            o_minRotationSpeed.setAvailable(o_rotateOverTime.available() && aBoolean);
            o_maxRotationSpeed.setAvailable(o_rotateOverTime.available() && aBoolean);
            o_RotateAtPercent.setAvailable(o_rotateOverTime.available() && aBoolean);
            o_rotateOverTimeAmount.setAvailable(o_rotateOverTime.available() && aBoolean);
            o_smartROT.setAvailable(o_rotateOverTime.available() && aBoolean);
            o_rotateOnGround.setAvailable(o_rotateOverTime.available() && aBoolean);
        } else if (booleanOption.equals(o_doRainbow)) {
            o_startColorRainbow.setAvailable(o_doRainbow.available() && aBoolean);
            o_rainbowOverTime.setAvailable(o_doRainbow.available() && aBoolean);
        } else if (booleanOption.equals(o_rainbowOverTime)) {
            o_rainbowMode.setAvailable(o_rainbowOverTime.available() && aBoolean);
            o_rainbowSpeed.setAvailable(o_rainbowOverTime.available() && aBoolean);
            o_syncRainbow.setAvailable(o_rainbowOverTime.available() && aBoolean);
            o_useRainbowGradient.setAvailable(o_rainbowOverTime.available() && aBoolean);
        } else if (booleanOption.equals(o_useRainbowGradient)) {
            o_rainbowGradientDelay.setAvailable(o_useRainbowGradient.available() && aBoolean);
        } else if (booleanOption.equals(o_useGradients)) {
            o_gradientFromMainCols.setAvailable(o_useGradients.available() && aBoolean);
            o_variationAmount.setAvailable(o_useGradients.available() && aBoolean);
        }
    }

    public static Screen getConfigScreen(Screen parent) {
        //JESUS FUCKING CHRIST I JUST WANT TO FINISH WITH THE UPDATE PLEASE I THOUGHT IT WOULDN'T BE THIS HARD
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder//LIES! LIES! THIS TITLE SERVES NO PURPOSE! IT'S ONLY USED FOR NARRATION! AAAAAAAAAAAAAAAHH NIGHTMARE NIGHTMARE NIGHTMARE!!!!! I HATE THE ANTICHRIST!!
                        //ISXANDER WHY WOULD YOU DO THIS TO ME
                        //GOD WHY HAVE YOU ALLOWED ME TO LEARN COMPUTER SCIENCE
                        //AND WHY DID YOU CURSE ME WITH SUCH STUPIDITY
                        .title(Text.of("Custom Totem Particles"))
                        .category(ConfigCategory.createBuilder()
                                .name(Text.of("Global"))
                                .tooltip(Text.of("General Configuration"))
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("General"))
                                        .collapsed(!CONFIG.instance().modEnabled)
                                        .option(o_modEnabled)
                                        .option(o_particleType)
                                        .option(o_multiplier)
                                        .option(o_showOwnParticles)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Emitter Settings"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useEmitter)
                                        .option(o_useEmitter)
                                        .option(o_emitterLifetime)
                                        .option(o_emitterYOffset)
                                        .option(o_emitterMovesWithPlayer)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Miscellaneous"))
                                        .collapsed(!CONFIG.instance().modEnabled)
                                        .option(o_hideOnGround)
                                        //might want to move this option later?
                                        .option(o_useCollisions)
                                        .option(o_lightLevel)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Config"))
                                        .option(ButtonOption.createBuilder()
                                                .name(Text.of("Copy Current Config"))
                                                .description(OptionDescription.of(Text.of("Copies the current configuration as text to your clipboard. Go share your configs with your buddies! (Make sure to save the config first.)")))
                                                .action((yaclScreen, buttonOption) -> MinecraftClient.getInstance().keyboard.setClipboard(gson.toJson(CONFIG.instance())))
                                                .build())
                                        .option(ButtonOption.createBuilder()
                                                .name(Text.literal("Load Config From Clipboard").formatted(Formatting.DARK_RED, Formatting.BOLD))
                                                .description(OptionDescription.of(Text.of("Loads a configuration from your clipboard if it's valid. WARNING: LOADING A VALID CONFIGURATION WILL OVERWRITE YOUR CONFIGURATION FILE.")))
                                                .action((yaclScreen, buttonOption) -> {
                                                    //this sucks but it works!!
                                                    try {
                                                        gson.fromJson(MinecraftClient.getInstance().keyboard.getClipboard(), YACLConfig.class);
                                                    } catch (JsonSyntaxException e) {
                                                        System.out.println("invalid config!!!");
                                                        return;
                                                    }
                                                    try {
                                                        Path path = FabricLoader.getInstance().getConfigDir().resolve("customtotemparticles.json");
                                                        Files.delete(path);
                                                        Files.createFile(path);
                                                        Files.writeString(path, MinecraftClient.getInstance().keyboard.getClipboard(), StandardCharsets.UTF_8);
                                                        YACLConfig.CONFIG.load();
                                                        CustomTotemParticles.unleashHell();
                                                    } catch (IOException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                })
                                                .build())
                                        .option(ButtonOption.createBuilder()
                                                .name(Text.of("Randomize All Values"))
                                                .description(OptionDescription.of(Text.of("Can't come up with a good configuration? Randomize every option and see what happens!")))
                                                .action((yaclScreen, buttonOption) -> CustomTotemParticles.randomizeOptions())
                                                .build())
                                        .build())
                                .build())
                        .category(ConfigCategory.createBuilder()
                                .name(Text.of("Color"))
                                .tooltip(Text.of("Color Configuration"))
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("General"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useColor)
                                        .option(o_useColor)
                                        .option(o_blendColors)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Start Color"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useColor || !CONFIG.instance().doStartColor)
                                        .option(o_doStartColor)
                                        .option(o_StartColor)
                                        .option(o_fadeToSpeed)
                                        .option(o_fadeToTime)
                                        .build())
                                //🤓☝ "List options must not be added as an option but a group!"
                                .option(o_mainColorList)
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("End Color"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useColor || !CONFIG.instance().doOutColor)
                                        .option(o_doOutColor)
                                        .option(o_outTargetColor)
                                        .option(o_fadeOutSpeed)
                                        .option(o_fadeOutTime)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Gradients"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useColor)
                                        .option(o_useGradients)
                                        .option(o_gradientFromMainCols)
                                        .option(o_variationAmount)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Rainbow"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useColor || !CONFIG.instance().doRainbow)
                                        .option(o_doRainbow)
                                        .option(o_startColorRainbow)
                                        .option(o_rainbowOverTime)
                                        .option(o_rainbowMode)
                                        .option(o_rainbowSpeed)
                                        .option(o_syncRainbow)
                                        .option(o_useRainbowGradient)
                                        .option(o_rainbowGradientDelay)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Alpha / Transparency"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useColor || !CONFIG.instance().useAlpha)
                                        .option(o_useAlpha)
                                        .option(o_minAlpha)
                                        .option(o_maxAlpha)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Fade Out"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useColor || !CONFIG.instance().useAlpha || !CONFIG.instance().loseAlpha)
                                        .option(o_loseAlpha)
                                        .option(o_alphaOutSpeed)
                                        .option(o_alphaOutTime)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Fade On Ground"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useColor || !CONFIG.instance().useAlpha || !CONFIG.instance().fadeOnGround)
                                        .option(o_fadeOnGround)
                                        .option(o_onGroundFade)
                                        .build())
                                .build())
                        .category(ConfigCategory.createBuilder()
                                .name(Text.of("Scale & Age"))
                                .tooltip(Text.of("Scaling Configuration"))
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Scale"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useScale)
                                        .option(o_useScale)
                                        .option(o_minScale)
                                        .option(o_maxScale)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Scale Over Time"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useScale || !CONFIG.instance().scaleOverTime)
                                        .option(o_scaleOverTime)
                                        .option(o_scaleAmount)
                                        .option(o_scaleAtPercent)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Scale On Ground"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useScale || !CONFIG.instance().scaleOnGround)
                                        .option(o_scaleOnGround)
                                        .option(o_onGroundScale)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Age"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useAge)
                                        .option(o_useAge)
                                        .option(o_minAge)
                                        .option(o_maxAge)
                                        .build())
                                .build())
                        .category(ConfigCategory.createBuilder()
                                .name(Text.of("Movement"))
                                .tooltip(Text.of("Movement Configuration"))
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("General"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useMovement)
                                        .option(o_useMovement)
                                        .option(o_minVelocityMultiplier)
                                        .option(o_maxVelocityMultiplier)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Custom velocity"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useMovement || !CONFIG.instance().customVelocity)
                                        .option(o_customVelocity)
                                        .option(o_minXVelocity)
                                        .option(o_maxXVelocity)
                                        .option(o_minYVelocity)
                                        .option(o_maxYVelocity)
                                        .option(o_minZVelocity)
                                        .option(o_maxZVelocity)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Gravity"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useMovement || !CONFIG.instance().useGravity)
                                        .option(o_useGravity)
                                        .option(o_minUpwardsAccel)
                                        .option(o_maxUpwardsAccel)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Gravity Over Time"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useMovement || !CONFIG.instance().useGravity || !CONFIG.instance().gravityOverTime)
                                        .option(o_gravityOverTime)
                                        .option(o_changeGravityAtPercent)
                                        .option(o_gravityOverTimeAmount)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Rotation"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useMovement || !CONFIG.instance().useRotation)
                                        .option(o_useRotation)
                                        .option(o_minStartRotation)
                                        .option(o_maxStartRotation)
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.of("Rotate Over Time"))
                                        .collapsed(!CONFIG.instance().modEnabled || !CONFIG.instance().useMovement || !CONFIG.instance().useRotation || !CONFIG.instance().rotateOverTime)
                                        .option(o_rotateOverTime)
                                        .option(o_minRotationSpeed)
                                        .option(o_maxRotationSpeed)
                                        .option(o_RotateAtPercent)
                                        .option(o_rotateOverTimeAmount)
                                        .option(o_smartROT)
                                        .option(o_rotateOnGround)
                                        .build())
                                .build())))
                .generateScreen(parent);
    }
}
//huh. Maybe YACL isn't so bad after all.