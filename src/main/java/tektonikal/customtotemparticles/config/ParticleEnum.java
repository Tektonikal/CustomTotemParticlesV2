package tektonikal.customtotemparticles.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public enum ParticleEnum implements NameableEnum {
    TOTEM_OF_UNDYING(Text.literal("Totem of Undying"), Identifier.of("textures/particle/glitter_7.png")),
    CRIT(Text.literal("Crit"), Identifier.of("textures/particle/critical_hit.png")),
    ENCHANTED_HIT(Text.literal("Potion Effect"), Identifier.of("textures/particle/enchanted_hit.png")),
    EFFECT(Text.literal("Enchanted Hit"), Identifier.of("textures/particle/effect_5.png"));

    private final @NotNull Text text;
    private final @NotNull Identifier identifier;

    ParticleEnum(@NotNull Text text, @NotNull Identifier identifier) {
        this.text = text;
        this.identifier = identifier;
    }

    @Override
    public Text getDisplayName() {
        return text;
    }

    public ParticleEffect getParticleTypes() {
        return switch (this) {
            case TOTEM_OF_UNDYING -> ParticleTypes.TOTEM_OF_UNDYING;
            case CRIT -> ParticleTypes.CRIT;
            case ENCHANTED_HIT -> ParticleTypes.ENCHANTED_HIT;
            case EFFECT -> ParticleTypes.EFFECT;
        };
    }

    public @NotNull Identifier getIdentifier() {
        return identifier;
    }
}

