package pw.yanva.mooninfo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class MoonInfoMod implements ModInitializer {
    public static final String MOD_ID = "yanva_moonphase";
    PhaseIcon icon;

    @Override
    public void onInitialize() {
        icon = new PhaseIcon();
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            icon.drawPhaseIcon(matrixStack);
        });
    }
}