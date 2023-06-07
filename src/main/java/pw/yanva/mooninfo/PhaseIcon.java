package pw.yanva.mooninfo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class PhaseIcon {
    private static final Identifier MOONPHASE_OVERWORLD = new Identifier(MoonInfoMod.MOD_ID, "textures/moonphase_overworld.png");
    private static final Identifier MOONPHASE_NETHER = new Identifier(MoonInfoMod.MOD_ID, "textures/moonphase_nether.png");
    private static final Identifier MOONPHASE_END = new Identifier(MoonInfoMod.MOD_ID, "textures/moonphase_end.png");

    private static final Formatting[] FULLNESS_COLOR = new Formatting[]
            {Formatting.RED, Formatting.GOLD, Formatting.YELLOW, Formatting.GREEN, Formatting.DARK_GREEN};

    void drawPhaseIcon(DrawContext context, int hudPosition) {
        if (hudPosition == 0) {
            hudPosition = 4;
        }
        MinecraftClient mc = MinecraftClient.getInstance();
        int windowWidth = mc.getWindow().getScaledWidth();
        int windowHeight = mc.getWindow().getScaledHeight();

        int phase = mc.world.getMoonPhase();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1, 1, 1,1);
        Identifier textureId = MOONPHASE_OVERWORLD;
        if (mc.world.getRegistryKey() == World.OVERWORLD) {
            textureId = MOONPHASE_OVERWORLD;
        } else if (mc.world.getRegistryKey() == World.END) {
            textureId = MOONPHASE_END;
        } else if (mc.world.getRegistryKey() == World.NETHER) {
            textureId = MOONPHASE_NETHER;
        }
        switch (hudPosition) {
            case (1) -> context.drawTexture(textureId, 5, 14, (phase) * 24, 0, 24, 24, 192, 24);
            case (2) -> context.drawTexture(textureId, 5, windowHeight - 26, (phase) * 24, 0, 24, 24, 192, 24);
            case (3) -> context.drawTexture(textureId, windowWidth - 60, 54, (phase) * 24, 0, 24, 24, 192, 24);
            case (4) -> context.drawTexture(textureId, windowWidth - 60, windowHeight - 26, (phase) * 24, 0, 24, 24, 192, 24);
        }

        //fullness
        int sizePercent = (int)(mc.world.getMoonSize() * 100f);

        //time left
        long daySecondsLeft = (24000 - mc.world.getTimeOfDay() % 24000) / 20;
        long dayMinutesLeft = daySecondsLeft / 60;
        boolean atLeast60s = daySecondsLeft >= 60;

        int x = 0;
        int y = 0;

        switch (hudPosition) {
            case (1) -> { x = 31; y = 17; }
            case (2) -> { x = 31; y = windowHeight - 23; }
            case (3) -> { x = windowWidth - 34; y = 57; }
            case (4) -> { x = windowWidth - 34; y = windowHeight - 23; }
        }

        context.drawTextWithShadow(mc.textRenderer, String.format("%s%d%%%s",
                        FULLNESS_COLOR[sizePercent / 25],
                        sizePercent,
                        phase <= 3 ? Formatting.RED + "↓" : Formatting.DARK_GREEN + "↑"),
                x, y, 0xffffff);
        context.drawTextWithShadow(mc.textRenderer, atLeast60s ? dayMinutesLeft + "min" : daySecondsLeft + "s",
                x, y + mc.textRenderer.fontHeight, Formatting.DARK_AQUA.getColorValue());
    }
}