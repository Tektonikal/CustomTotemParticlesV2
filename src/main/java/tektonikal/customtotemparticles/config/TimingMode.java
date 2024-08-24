package tektonikal.customtotemparticles.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.text.Text;

public enum TimingMode implements NameableEnum {
    ALL, START, MAIN, END, UNTIL_END, AFTER_START, EXCLUDING_MAIN;

    @Override
    public Text getDisplayName() {
        return switch (name()) {
            case "ALL" -> Text.literal("All");
            case "START" -> Text.literal("Start Only");
            case "MAIN" -> Text.literal("Main Only");
            case "END" -> Text.literal("End Only");
            case "UNTIL_END" -> Text.literal("Until End");
            case "AFTER_START" -> Text.literal("After Start");
            case "EXCLUDING_MAIN" -> Text.literal("Excluding Main");
            default -> Text.literal("Erm, I think you did something wrong...");
        };
    }
}
