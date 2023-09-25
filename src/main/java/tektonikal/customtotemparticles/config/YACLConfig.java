package tektonikal.customtotemparticles.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.GsonConfigInstance;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


@SuppressWarnings("all")
public class YACLConfig {
    public static final GsonConfigInstance<YACLConfig> INSTANCE = GsonConfigInstance.createBuilder(YACLConfig.class).setPath(FabricLoader.getInstance().getConfigDir().resolve("customtotemparticles.json")).build();
    @ConfigEntry
    public boolean useAlpha = false; //done
    @ConfigEntry
    public int EmitterAge = 30; //done
    @ConfigEntry
    public boolean rotateOnGround = false; //done
    @ConfigEntry
    public boolean useRotation = true; //done
    @ConfigEntry
    public float minRotationSpeed = 0.1f; //done
    @ConfigEntry
    public int MinStartRotation = 0; //done
    @ConfigEntry
    public int MaxStartRotation = 360; //done
    @ConfigEntry
    public float maxRotationSpeed = 1f; //done
    @ConfigEntry
    public boolean UseAngle = true; //done
    @ConfigEntry
    public float EmitterYOffset = 0; //done

    @ConfigEntry
    public float minAlpha = 1f; //done
    @ConfigEntry
    public float maxAlpha = 1f; //done
    @ConfigEntry
    public boolean useScale = false; //done
    @ConfigEntry
    public float minScale = 0.75f; //done
    @ConfigEntry
    public float maxScale = 0.75f; //done
    @ConfigEntry
    public boolean useMovement = false; //done
    @ConfigEntry
    public float MinVelocityMultiplier = 0.6f; //done
    @ConfigEntry
    public float MaxVelocityMultiplier = 0.6f; //done
    @ConfigEntry
    public float MinUpwardsAccel = 0.6f; //done
    @ConfigEntry
    public float MaxUpwardsAccel = 0.6f; //done
    @ConfigEntry
    public boolean useCollisions = true; //done
    @ConfigEntry
    public boolean useColor = true; //done
    @ConfigEntry
    public boolean modEnabled = true; //done
    @ConfigEntry
    public boolean BlendColors = false;//done
    //IN COLOR
    @ConfigEntry
    public boolean DoInColor = false;//done
    @ConfigEntry
    public float FadeToSpeed = 0.2f;//done
    @ConfigEntry
    public float FadeToTime;//done
    @ConfigEntry
    public Color InTargetColor = java.awt.Color.decode("#ffffff");//done
    //OUT COLOR
    @ConfigEntry
    public boolean DoOutColor = false;//done
    @ConfigEntry
    public float FadeOutSpeed = 0.2f;//done
    @ConfigEntry
    public float FadeOutTime;//done
    @ConfigEntry
    public Color OutTargetColor = java.awt.Color.decode("#ffffff");//done
    @ConfigEntry
    public boolean LoseAlpha = true;//done
    @ConfigEntry
    public float AlphaOutSpeed = 0.05f;//done
    @ConfigEntry
    public float AlphaOutTime = 0.90f;//done
    @ConfigEntry
    public boolean useAge = false;//done
    @ConfigEntry
    public int minAge = 60;//done
    @ConfigEntry
    public int maxAge = 72;//done
    @ConfigEntry
    public float Multiplier = 1;//done
    @ConfigEntry
    public float GlobalMultiplier = 1;//done
    @ConfigEntry
    public boolean showOwnParticles = true;//done
    @ConfigEntry
    public List<Color> StartColorList = Arrays.asList(new Color(255, 255, 255), new Color(0, 0, 0));//done
    @ConfigEntry
    public ParticleEnum Particles = ParticleEnum.TOTEM_OF_UNDYING;//done
    @ConfigEntry
    public boolean scaleOverTime = true;//done
    @ConfigEntry
    public float ScaleAmount = 0;//done
    @ConfigEntry
    public float scaleAtPercent = 0.56f;//done
    @ConfigEntry
    public boolean HideOnGround;//done
    @ConfigEntry
    public boolean scaleOnGround;//done
    @ConfigEntry
    public boolean fadeOnGround;//done
    @ConfigEntry
    public float onGroundFade;//done
    @ConfigEntry
    public float onGroundScale;//done
    @ConfigEntry
    public boolean gravityOverTime;
    @ConfigEntry
    public float changeGravityAtPercent = 0.90f;
    @ConfigEntry
    public float gravityOverTimeAmount = 0.01f;
    @ConfigEntry
    public boolean customVelocity = false;
    @ConfigEntry
    public float minXVelocity = 1;
    @ConfigEntry
    public float maxXVelocity = 2;
    @ConfigEntry
    public float minYVelocity = 3;
    @ConfigEntry
    public float maxYVelocity = 5;
    @ConfigEntry
    public float minZVelocity = 0.5f;
    @ConfigEntry
    public float maxZVelocity = 1;
    @ConfigEntry
    public boolean EmitterMovesWithPlayer;
    @ConfigEntry
    public boolean useEmitter = true;
    @ConfigEntry
    public boolean useGravity;

    /*
    Default values in game:
        upwardsAccel = 1.25
        velocity multiplier = 0.6f
        scale *= 0.75f
        maxAge = 60-72
        color1 = 0.6-0.8, 0.6-0.9, 0-0.2
        color2 = 0.1-0.3, 0.4-0.7, 0-0.2
     */
    public static Screen getConfigScreen(Screen parent) {
        return YetAnotherConfigLib.create(INSTANCE, ((defaults, config, builder) -> builder
                        //LIES! LIES! THIS SERVES NO PURPOSE! SIMPLY USED FOR NARRATION! AAAAAAAAAAAAAAAHH NIGHTMARE!!!! NIGHTMARE NIGHTMARE NIGHTMARE!!!!! I HATE THE ANTICHRIST!
                        .title(Text.literal("Custom Totem Particles"))
                        .category(ConfigCategory.createBuilder()
                                .name(Text.literal("Global"))
                                .tooltip(Text.literal("General configuration"))
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("General"))
                                        .option(Option.createBuilder(boolean.class).description(OptionDescription.of(Text.literal("Enable the mod globally.")))
                                                .name(Text.literal("Mod enabled"))
                                                .binding(true, () -> config.modEnabled, newVal -> config.modEnabled = newVal)
                                                .controller(TickBoxControllerBuilder::create).build())
                                        .option(Option.createBuilder(ParticleEnum.class)
                                                .name(Text.literal("Particle type"))
                                                .description(OptionDescription.of(Text.literal("Replaces the totem particle with a different one. Most options do not apply when using a different type.")))
                                                .binding(ParticleEnum.TOTEM_OF_UNDYING, () -> config.Particles, newVal -> config.Particles = newVal)
                                                .controller(option -> EnumControllerBuilder.create(option).enumClass(ParticleEnum.class)).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Totem particle multiplier"))
                                                .description(OptionDescription.of(Text.literal("The multiplier for the totem particles only.")))
                                                .binding(1.5f, () -> config.Multiplier, newVal -> config.Multiplier = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0.1f, 5f).step(0.1f).valueFormatter(val -> Text.literal(String.format("%.1f", val) + "x")))
                                                .build())
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Show own totem particles"))
                                                .description(OptionDescription.of(Text.literal("Whether or not you can see totem particles coming from yourself."))
                                                ).binding(true, () -> config.showOwnParticles, newVal -> config.showOwnParticles = newVal)
                                                .controller(TickBoxControllerBuilder::create).build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Emitter Settings"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Category enabled"))
                                                .description(OptionDescription.of(Text.literal("Whether or not the emitter particle will be modified.")))
                                                .binding(true, () -> config.useEmitter, newVal -> config.useEmitter = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(int.class)
                                                .name(Text.literal("Emitter lifetime"))
                                                .description(OptionDescription.of(Text.literal("How long particles are created for.")))
                                                .binding(15, () -> config.EmitterAge, newVal -> config.EmitterAge = newVal)
                                                .controller(intOption -> IntegerSliderControllerBuilder.create(intOption).range(1, 100).step(1)).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Vertical offset"))
                                                .description(OptionDescription.of(Text.literal("The vertical offset of the emitter.")))
                                                .binding(-0.2f, () -> config.EmitterYOffset, newVal -> config.EmitterYOffset = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-2f, 2f).step(0.1f))
                                                .build())
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Emitter moves with player"))
                                                .description(OptionDescription.of(Text.literal("Whether or not the emitter moves with the player emitting it.")))
                                                .binding(false, () -> config.EmitterMovesWithPlayer, newVal -> config.EmitterMovesWithPlayer = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Miscallaneous"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Hide on ground"))
                                                .description(OptionDescription.of(Text.literal("Whether or not to hide the particle when it touches the ground")))
                                                .binding(false, () -> config.HideOnGround, newVal -> config.HideOnGround = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Particle collisions"))
                                                .description(OptionDescription.of(Text.literal("Whether or not the particle will collide with blocks.")))
                                                .binding(true, () -> config.useCollisions, newVal -> config.useCollisions = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .build())
                                .build())
                        .category(ConfigCategory.createBuilder()
                                .name(Text.literal("Color"))
                                .tooltip(Text.literal("Color configuration"))
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("General"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Enable category"))
                                                .description(OptionDescription.of(Text.literal("Whether or not to modify the totem particle's colors.")))
                                                .binding(true, () -> config.useColor, newVal -> config.useColor = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Blend colors"))
                                                .description(OptionDescription.of(Text.literal("Enable the starting color to be a random color between the 2 starting colors.")))
                                                .binding(true, () -> config.BlendColors, newVal -> config.BlendColors = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build()).build())
                                .option(ListOption.<Color>createBuilder()
                                        .name(Text.literal("Starting color"))
                                        .description(OptionDescription.of(Text.literal("The starting colors for the particle. If \"Blend colors\" is enabled, only the first 2 colors will be used. Otherwise, a random color will be picked")))
                                        .controller(ColorControllerBuilder::create)
                                        .binding(Arrays.asList(new Color(255, 0, 0), new Color(255, 255, 0)), () -> config.StartColorList, newVal -> config.StartColorList = newVal)
                                        .initial(new Color(0, 0, 0)
                                        ).build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Color transition 1"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Fade from starting color"))
                                                .description(OptionDescription.of(Text.literal("Whether or not the particle will fade from the first color to the second color.")))
                                                .binding(true, () -> config.DoInColor, newVal -> config.DoInColor = newVal)
                                                .controller(TickBoxControllerBuilder::create).build())
                                        .option(Option.createBuilder(Color.class)
                                                .name(Text.literal("Fade to color"))
                                                .description(OptionDescription.of(Text.literal("The color to fade to first.")))
                                                .binding(Color.decode("#777777"), () -> config.InTargetColor, newVal -> config.InTargetColor = newVal)
                                                .controller(ColorControllerBuilder::create).build())
                                        .option(Option.createBuilder(float.class).name(Text.literal("Fade in speed"))
                                                .description(OptionDescription.of(Text.literal("The speed at which to fade.")))
                                                .binding(0.2f, () -> config.FadeToSpeed, newVal -> config.FadeToSpeed = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range((float) 0.1f, 1F).step(0.1f).valueFormatter(val -> Text.literal(String.format("%.1f", val))))
                                                .build())
                                        .option(Option.createBuilder(float.class).name(Text.literal("Fade at %"))
                                                .description(OptionDescription.of(Text.literal("At what percentage of the particle's lifetime to fade to the next color.")))
                                                .binding(0.5f, () -> config.FadeToTime, newVal -> config.FadeToTime = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range((float) 0.01f, 1F).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.0f", val * 100) + "%")))
                                                .build()).build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Color transition 2"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Fade to out color"))
                                                .description(OptionDescription.of(Text.literal("Whether or not the particle fades out to it's last color.")))
                                                .binding(false, () -> config.DoOutColor, newVal -> config.DoOutColor = newVal)
                                                .controller(TickBoxControllerBuilder::create).build())
                                        .option(Option.createBuilder(Color.class)
                                                .name(Text.literal("Fade out color"))
                                                .description(OptionDescription.of(Text.literal("The final color the particle will change its color to.")))
                                                .binding(Color.decode("#000000"), () -> config.OutTargetColor, newVal -> config.OutTargetColor = newVal)
                                                .controller(ColorControllerBuilder::create).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Fade speed"))
                                                .description(OptionDescription.of(Text.literal("The speed at which to fade to the final color at.")))
                                                .binding(0.2f, () -> config.FadeOutSpeed, newVal -> config.FadeOutSpeed = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range((float) 0.1f, 1F).step(0.1f).valueFormatter(val -> Text.literal(String.format("%.1f", val)))).build())
                                        .option(Option.createBuilder(float.class).name(Text.literal("Fade at %"))
                                                .description(OptionDescription.of(Text.literal("At what percentage of the particle's lifetime to fade to the final color.")))
                                                .binding(0.75f, () -> config.FadeOutTime, newVal -> config.FadeOutTime = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range((float) 0.01f, 1F).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.0f", val * 100) + "%")))
                                                .build()).build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Alpha / Transparency"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Use alpha"))
                                                .description(OptionDescription.of(Text.literal("Whether or not to modify the particle's alpha.")))
                                                .binding(true, () -> config.useAlpha, newVal -> config.useAlpha = newVal)
                                                .controller(TickBoxControllerBuilder::create).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Minimum Alpha"))
                                                .description(OptionDescription.of(Text.literal("The minimum starting opacity of the particle.")))
                                                .binding(0.25f, () -> config.minAlpha, newVal -> config.minAlpha = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 1f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val)))).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Maximum Alpha"))
                                                .description(OptionDescription.of(Text.literal("The maximum starting opacity of the particle.")))
                                                .binding(1f, () -> config.maxAlpha, newVal -> config.maxAlpha = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0f, 1f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Fade out"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Fade out"))
                                                .description(OptionDescription.of(Text.literal("Whether or not the particle loses opacity over time.")))
                                                .binding(true, () -> config.LoseAlpha, newVal -> config.LoseAlpha = newVal)
                                                .controller(TickBoxControllerBuilder::create).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Fade speed"))
                                                .description(OptionDescription.of(Text.literal("How fast the particle will fade out.")))
                                                .binding(-0.05f, () -> config.AlphaOutSpeed, newVal -> config.AlphaOutSpeed = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.1f, 0.1f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val)))).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Fade at %"))
                                                .description(OptionDescription.of(Text.literal("At what percentage of the particle's lifetime to fade out.")))
                                                .binding(0.9f, () -> config.AlphaOutTime, newVal -> config.AlphaOutTime = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range((float) 0, 1F).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.0f", val * 100) + "%")))
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Fade on ground"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Fade on ground"))
                                                .description(OptionDescription.of(Text.literal("Whether or not the particle will change its opacity when it lands on the ground.")))
                                                .binding(true, () -> config.fadeOnGround, newVal -> config.fadeOnGround = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Fade speed"))
                                                .description(OptionDescription.of(Text.literal("How fast the particle will fade when on the ground.")))
                                                .binding(-0.05f, () -> config.onGroundFade, newVal -> config.onGroundFade = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.1f, 0.1f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .build())
                                .build())
                        .category(ConfigCategory.createBuilder()
                                .name(Text.literal("Scale & Age"))
                                .tooltip(Text.literal("Scaling configuration"))
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("General"))
                                        .option(Option.createBuilder(boolean.class)
                                                .description(OptionDescription.of(Text.literal("Whether or not to modify the totem particle's colors.")))
                                                .name(Text.literal("Enable category"))
                                                .binding(true, () -> config.useScale, newVal -> config.useScale = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Minimum Scale"))
                                                .description(OptionDescription.of(Text.literal("The minimum starting scale of the particle.")))
                                                .binding(0.25f, () -> config.minScale, newVal -> config.minScale = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0.01f, 2f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val)))).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Maximum Scale"))
                                                .description(OptionDescription.of(Text.literal("The maximum starting scale of the particle.")))
                                                .binding(0.5f, () -> config.maxScale, newVal -> config.maxScale = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0.01f, 2f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Scale over time"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Scale over time"))
                                                .description(OptionDescription.of(Text.literal("Change the particles scale at a certain speed at a certain time.")))
                                                .binding(true, () -> config.scaleOverTime, newVal -> config.scaleOverTime = newVal)
                                                .controller(TickBoxControllerBuilder::create).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Scale amount"))
                                                .description(OptionDescription.of(Text.literal("How much to scale the particle.")))
                                                .binding(-0.05f, () -> config.ScaleAmount, newVal -> config.ScaleAmount = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.1f, 0.1f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val)))).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Scale at %"))
                                                .description(OptionDescription.of(Text.literal("At what percent of the particle's lifetime to scale at.")))
                                                .binding(0.9f, () -> config.scaleAtPercent, newVal -> config.scaleAtPercent = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range((float) 0.01f, 1F).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.0f", val * 100) + "%")))
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Scale on ground"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Scale on ground"))
                                                .description(OptionDescription.of(Text.literal("Whether or not to scale the particle when it collides with the ground.")))
                                                .binding(true, () -> config.scaleOnGround, newVal -> config.scaleOnGround = newVal)
                                                .controller(TickBoxControllerBuilder::create).build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Scale on ground amount"))
                                                .description(OptionDescription.of(Text.literal("By how much to scale the particle when it collides with the ground.")))
                                                .binding(-0.05f, () -> config.onGroundScale, newVal -> config.onGroundScale = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.2f, 0.2f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Age"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Enable category"))
                                                .description(OptionDescription.of(Text.literal("Whether or not to enable this category.")))
                                                .binding(true, () -> config.useAge, newVal -> config.useAge = newVal).controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(int.class)
                                                .name(Text.literal("Minimum age"))
                                                .description(OptionDescription.of(Text.literal("The particle's minimum age.")))
                                                .binding(40, () -> config.minAge, newVal -> config.minAge = newVal)
                                                .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption).range(10, 200).step(1).valueFormatter(val -> Text.literal(val + " ticks"))).build())
                                        .option(Option.createBuilder(int.class)
                                                .name(Text.literal("Maximum age"))
                                                .description(OptionDescription.of(Text.literal("The particle's maximum age.")))
                                                .binding(60, () -> config.maxAge, newVal -> config.maxAge = newVal)
                                                .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption).range(10, 200).step(1).valueFormatter(val -> Text.literal(val + " ticks")))
                                                .build())
                                        .build())
                                .build())
                        .category(ConfigCategory.createBuilder()
                                .name(Text.literal("Movement"))
                                .tooltip(Text.literal("Movement configuration"))
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("General"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Enable category"))
                                                .description(OptionDescription.of(Text.literal("Whether or not to modify the movement of the particles.")))
                                                .binding(true, () -> config.useMovement, newVal -> config.useMovement = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Minimum velocity multiplier"))
                                                .description(OptionDescription.of(Text.literal("The minimum starting velocity multiplier of the particle.")))
                                                .binding(0.25f, () -> config.MinVelocityMultiplier, newVal -> config.MinVelocityMultiplier = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0.01f, 1f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Maximum velocity multiplier"))
                                                .description(OptionDescription.of(Text.literal("The maximum starting velocity multiplier of the particle.")))
                                                .binding(0.75f, () -> config.MaxVelocityMultiplier, newVal -> config.MaxVelocityMultiplier = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(0.01f, 1f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Custom velocity"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Use custom velocity"))
                                                .description(OptionDescription.of(Text.literal("Whether or not to apply the velocity parameters below to the particle.")))
                                                .binding(true, () -> config.customVelocity, newVal -> config.customVelocity = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Minimum X velocity"))
                                                .description(OptionDescription.of(Text.literal("The minimum starting X velocity multiplier of the particle.")))
                                                .binding(-0.65f, () -> config.minXVelocity, newVal -> config.minXVelocity = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Maximum X velocity"))
                                                .description(OptionDescription.of(Text.literal("The maximum starting X velocity multiplier of the particle.")))
                                                .binding(0.65f, () -> config.maxXVelocity, newVal -> config.maxXVelocity = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Minimum Y velocity"))
                                                .description(OptionDescription.of(Text.literal("The minimum starting Y velocity multiplier of the particle.")))
                                                .binding(0.5f, () -> config.minYVelocity, newVal -> config.minYVelocity = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Maximum Y velocity"))
                                                .description(OptionDescription.of(Text.literal("The maximum starting Y velocity multiplier of the particle.")))
                                                .binding(1.5f, () -> config.maxYVelocity, newVal -> config.maxYVelocity = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Minimum Z velocity"))
                                                .description(OptionDescription.of(Text.literal("The minimum starting Z velocity multiplier of the particle.")))
                                                .binding(-0.65f, () -> config.minZVelocity, newVal -> config.minZVelocity = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Maximum Z velocity"))
                                                .description(OptionDescription.of(Text.literal("The maximum starting Z velocity multiplier of the particle.")))
                                                .binding(0.65f, () -> config.maxZVelocity, newVal -> config.maxZVelocity = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Gravity"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Modify gravity"))
                                                .description(OptionDescription.of(Text.literal("Whether or not to modify the particle's gravity.")))
                                                .binding(true, () -> config.useGravity, newVal -> config.useGravity = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Minimum gravity multiplier"))
                                                .description(OptionDescription.of(Text.literal("The minimum starting gravity of the particle.")))
                                                .binding(-0.3f, () -> config.MinUpwardsAccel, newVal -> config.MinUpwardsAccel = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Maximum gravity multiplier"))
                                                .description(OptionDescription.of(Text.literal("The maximum starting gravity of the particle.")))
                                                .binding(1.3f, () -> config.MaxUpwardsAccel, newVal -> config.MaxUpwardsAccel = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-3f, 3f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())

                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Gravity over time"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Enabled"))
                                                .description(OptionDescription.of(Text.literal("Whether or not gravity will change over time.")))
                                                .binding(true, () -> config.gravityOverTime, newVal -> config.gravityOverTime = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Change at %"))
                                                .description(OptionDescription.of(Text.literal("At what percentage of the particle's lifetime to change the gravity at.")))
                                                .binding(0.5f, () -> config.changeGravityAtPercent, newVal -> config.changeGravityAtPercent = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range((float) 0.01f, 1F).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.0f", val * 100) + "%")))
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Gravity over time amount"))
                                                .description(OptionDescription.of(Text.literal("How much to change the gravity of the particle.")))
                                                .binding(-0.2f, () -> config.gravityOverTimeAmount, newVal -> config.gravityOverTimeAmount = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-0.2f, 0.2f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Rotation"))
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Modify rotation"))
                                                .description(OptionDescription.of(Text.literal("Whether or not to modify the particle's rotation.")))
                                                .binding(true, () -> config.useRotation, newVal -> config.useRotation = newVal)
                                                .controller(TickBoxControllerBuilder::create).build())
                                        .option(Option.createBuilder(int.class)
                                                .name(Text.literal("Minimum start rotation"))
                                                .description(OptionDescription.of(Text.literal("The minimum starting rotation of the particle.")))
                                                .binding(-360, () -> config.MinStartRotation, newVal -> config.MinStartRotation = newVal)
                                                .controller(intOption -> IntegerSliderControllerBuilder.create(intOption).range(-360, 360).step(1)).build())
                                        .option(Option.createBuilder(int.class)
                                                .name(Text.literal("Maximum start rotation"))
                                                .description(OptionDescription.of(Text.literal("The maximum starting rotation of the particle.")))
                                                .binding(360, () -> config.MaxStartRotation, newVal -> config.MaxStartRotation = newVal)
                                                .controller(intOption -> IntegerSliderControllerBuilder.create(intOption).range(-360, 360).step(1)).build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.literal("Rotate over time"))
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Minimum rotation speed"))
                                                .description(OptionDescription.of(Text.literal("The minimum starting rotation speed of the particle.")))
                                                .binding(-5f, () -> config.minRotationSpeed, newVal -> config.minRotationSpeed = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-15f, 15f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.createBuilder(float.class)
                                                .name(Text.literal("Maximum rotation speed"))
                                                .description(OptionDescription.of(Text.literal("The maximum starting rotation speed of the particle.")))
                                                .binding(5f, () -> config.maxRotationSpeed, newVal -> config.maxRotationSpeed = newVal)
                                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption).range(-15f, 15f).step(0.01f).valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.createBuilder(boolean.class)
                                                .name(Text.literal("Rotate on ground"))
                                                .description(OptionDescription.of(Text.literal("Whether or not the particle will rotate on the ground.")))
                                                .binding(false, () -> config.rotateOnGround, newVal -> config.rotateOnGround = newVal)
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .build())
                                .build())))
                .generateScreen(parent);
    }
}
//isxander, i will never forgive you.