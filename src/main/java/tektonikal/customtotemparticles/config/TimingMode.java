package tektonikal.customtotemparticles.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public enum TimingMode implements NameableEnum {
    ALL(Component.literal("All")),
    START(Component.literal("Start Only")),
    MAIN(Component.literal("Main Only")),
    END(Component.literal("End Only")),
    UNTIL_END(Component.literal("Until End")),
    AFTER_START(Component.literal("After Start")),
    EXCLUDING_MAIN(Component.literal("Excluding Main"));

    private final @NotNull Component text;

    TimingMode(@NotNull Component text) {
        this.text = text;
    }

    @Override
    public Component getDisplayName() {
        return text;
    }
}
