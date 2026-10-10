package tektonikal.customtotemparticles.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public enum ParticleEnum implements NameableEnum {
    TOTEM_OF_UNDYING(Component.literal("Totem of Undying"), Identifier.parse("textures/particle/glitter_7.png")),
    CRIT(Component.literal("Crit"), Identifier.parse("textures/particle/critical_hit.png")),
    ENCHANTED_HIT(Component.literal("Enchanted Hit"), Identifier.parse("textures/particle/enchanted_hit.png"));

    private final @NotNull Component text;
    private final @NotNull Identifier identifier;

    ParticleEnum(@NotNull Component text, @NotNull Identifier identifier) {
        this.text = text;
        this.identifier = identifier;
    }

    @Override
    public Component getDisplayName() {
        return text;
    }

    public ParticleOptions getParticleTypes() {
        return switch (this) {
            case TOTEM_OF_UNDYING -> ParticleTypes.TOTEM_OF_UNDYING;
            case CRIT -> ParticleTypes.CRIT;
            case ENCHANTED_HIT -> ParticleTypes.ENCHANTED_HIT;
        };
    }

    public @NotNull Identifier getIdentifier() {
        return identifier;
    }
}

