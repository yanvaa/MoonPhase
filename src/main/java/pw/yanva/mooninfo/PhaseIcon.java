package pw.yanva.mooninfo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class PhaseIcon extends DrawableHelper {
    private static final Identifier MOONPHASE_OVERWORLD = new Identifier(MoonInfoMod.MOD_ID, "textures/moonphase_overworld.png");
    private static final Identifier MOONPHASE_NETHER = new Identifier(MoonInfoMod.MOD_ID, "textures/moonphase_nether.png");
    private static final Identifier MOONPHASE_END = new Identifier(MoonInfoMod.MOD_ID, "textures/moonphase_end.png");

    private static final Formatting[] FULLNESS_COLOR = new Formatting[]
            {Formatting.RED, Formatting.GOLD, Formatting.YELLOW, Formatting.GREEN, Formatting.DARK_GREEN};

    void drawPhaseIcon(MatrixStack matrixStack, int hudPosition) {
        if (hudPosition == 0) {
            hudPosition = 4;
        }
        MinecraftClient mc = MinecraftClient.getInstance();
        int windowWidth = mc.getWindow().getScaledWidth();
        int windowHeight = mc.getWindow().getScaledHeight();

        int phase = mc.world.getMoonPhase();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1, 1, 1,1);
        if (mc.world.getRegistryKey() == World.OVERWORLD) {
            RenderSystem.setShaderTexture(0, MOONPHASE_OVERWORLD);
        } else if (mc.world.getRegistryKey() == World.END) {
            RenderSystem.setShaderTexture(0, MOONPHASE_END);
        } else if (mc.world.getRegistryKey() == World.NETHER) {
            RenderSystem.setShaderTexture(0, MOONPHASE_NETHER);
        }
        switch (hudPosition) {
            case (1) -> DrawableHelper.drawTexture(matrixStack, 5, 14, (phase) * 24, 0, 24, 24, 192, 24);
            case (2) -> DrawableHelper.drawTexture(matrixStack, 5, windowHeight - 26, (phase) * 24, 0, 24, 24, 192, 24);
            case (3) -> DrawableHelper.drawTexture(matrixStack, windowWidth - 60, 54, (phase) * 24, 0, 24, 24, 192, 24);
            case (4) -> DrawableHelper.drawTexture(matrixStack, windowWidth - 60, windowHeight - 26, (phase) * 24, 0, 24, 24, 192, 24);
        }

        //fullness
        int sizePercent = (int)(mc.world.getMoonSize() * 100f);

        //time left
        long daySecondsLeft = (24000 - mc.world.getTimeOfDay() % 24000) / 20;
        long dayMinutesLeft = daySecondsLeft / 60;
        boolean atLeast60s = daySecondsLeft >= 60;

        switch (hudPosition) {
            case (1) -> {
                mc.textRenderer.drawWithShadow(matrixStack, String.format("%s%d%%%s",
                                FULLNESS_COLOR[sizePercent / 25],
                                sizePercent,
                                phase <= 3 ? Formatting.RED + "↓" : Formatting.DARK_GREEN + "↑"),
                        31, 17, 0xffffff);
                mc.textRenderer.drawWithShadow(matrixStack, atLeast60s ? dayMinutesLeft + "min" : daySecondsLeft + "s",
                        31, 17 + mc.textRenderer.fontHeight, Formatting.DARK_AQUA.getColorValue());
            }
            case (2) -> {
                mc.textRenderer.drawWithShadow(matrixStack, String.format("%s%d%%%s",
                                FULLNESS_COLOR[sizePercent / 25],
                                sizePercent,
                                phase <= 3 ? Formatting.RED + "↓" : Formatting.DARK_GREEN + "↑"),
                        31, windowHeight - 23, 0xffffff);
                mc.textRenderer.drawWithShadow(matrixStack, atLeast60s ? dayMinutesLeft + "min" : daySecondsLeft + "s",
                        31, windowHeight - 23 + mc.textRenderer.fontHeight, Formatting.DARK_AQUA.getColorValue());
            }
            case (3) -> {
                mc.textRenderer.drawWithShadow(matrixStack, String.format("%s%d%%%s",
                                FULLNESS_COLOR[sizePercent / 25],
                                sizePercent,
                                phase <= 3 ? Formatting.RED + "↓" : Formatting.DARK_GREEN + "↑"),
                        windowWidth - 34, 57, 0xffffff);
                mc.textRenderer.drawWithShadow(matrixStack, atLeast60s ? dayMinutesLeft + "min" : daySecondsLeft + "s",
                        windowWidth - 34, 57 + mc.textRenderer.fontHeight, Formatting.DARK_AQUA.getColorValue());
            }
            case (4) -> {
                mc.textRenderer.drawWithShadow(matrixStack, String.format("%s%d%%%s",
                                FULLNESS_COLOR[sizePercent / 25],
                                sizePercent,
                                phase <= 3 ? Formatting.RED + "↓" : Formatting.DARK_GREEN + "↑"),
                        windowWidth - 34, windowHeight - 23, 0xffffff);
                mc.textRenderer.drawWithShadow(matrixStack, atLeast60s ? dayMinutesLeft + "min" : daySecondsLeft + "s",
                        windowWidth - 34, windowHeight - 23 + mc.textRenderer.fontHeight, Formatting.DARK_AQUA.getColorValue());
            }
        }
    }
}