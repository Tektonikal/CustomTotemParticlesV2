package tektonikal.customtotemparticles.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public enum TimingMode implements NameableEnum {
    ALL(Text.literal("All")),
    START(Text.literal("Start Only")),
    MAIN(Text.literal("Main Only")),
    END(Text.literal("End Only")),
    UNTIL_END(Text.literal("Until End")),
    AFTER_START(Text.literal("After Start")),
    EXCLUDING_MAIN(Text.literal("Excluding Main"));

    private final @NotNull Text text;

    TimingMode(@NotNull Text text) {
        this.text = text;
    }

    @Override
    public Text getDisplayName() {
        return text;
    }
}
