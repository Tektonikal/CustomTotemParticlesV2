package tektonikal.customtotemparticles.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public enum ParticleEnum implements NameableEnum {
    TOTEM_OF_UNDYING, CRIT, ENCHANTED_HIT, EFFECT;

    @Override
    public Text getDisplayName() {
        return switch (name()) {
            case "TOTEM_OF_UNDYING" -> Text.literal("Totem of Undying");
            case "CRIT" -> Text.literal("Crit");
            case "EFFECT" -> Text.literal("Potion Effect");
            case "ENCHANTED_HIT" -> Text.literal("Enchanted Hit");
            default -> Text.literal("Erm, I think you did something wrong...");
        };
    }

    public ParticleEffect getParticleTypes() {
        return switch (name()) {
            case "CRIT" -> ParticleTypes.CRIT;
            case "EFFECT" -> ParticleTypes.EFFECT;
            case "ENCHANTED_HIT" -> ParticleTypes.ENCHANTED_HIT;
            default -> ParticleTypes.TOTEM_OF_UNDYING;
        };
    }

    public Identifier getIdentifier() {
        return switch (name()) {
            case "CRIT" -> Identifier.of("textures/particle/critical_hit.png");
            case "EFFECT" -> Identifier.of("textures/particle/effect_5.png");
            case "ENCHANTED_HIT" -> Identifier.of("textures/particle/enchanted_hit.png");
            default -> Identifier.of("textures/particle/glitter_7.png");
        };
    }
}

