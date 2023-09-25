package tektonikal.customtotemparticles.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.text.Text;

public enum ParticleEnum implements NameableEnum {
    TOTEM_OF_UNDYING, CRIT, ENCHANTED_HIT, EFFECT;

    @Override
    public Text getDisplayName() {
        return switch (name()) {
            case "TOTEM_OF_UNDYING" -> Text.literal("Totem of undying");
            case "CRIT" -> Text.literal("Critical");
            case "EFFECT" -> Text.literal("Potion effect");
            case "ENCHANTED_HIT" -> Text.literal("Enchanted hit");
            default -> Text.literal("Erm, I think you did something wrong...");
        };
    }
}