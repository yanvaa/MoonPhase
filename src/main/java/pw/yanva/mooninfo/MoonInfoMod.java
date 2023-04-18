package pw.yanva.mooninfo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import pw.yanva.mooninfo.config.ModConfig;
import pw.yanva.mooninfo.events.KeyInputHandler;

import java.io.IOException;

public class MoonInfoMod implements ModInitializer {
    public static final String MOD_ID = "yanva_moonphase";
    public static ModConfig config;
    PhaseIcon icon;

    @Override
    public void onInitialize(){
        config = new ModConfig();
        icon = new PhaseIcon();
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            try {
                config.load();
                icon.drawPhaseIcon(matrixStack, config.hudPosition);
            } catch (IOException ie) {
                return;
            }
        });
        KeyInputHandler.register();
    }
}