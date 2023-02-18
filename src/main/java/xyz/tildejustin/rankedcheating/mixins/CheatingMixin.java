package xyz.tildejustin.rankedcheating.mixins;

import com.mcsr.projectelo.MCSREloProject;
import com.redlimerl.speedrunigt.timer.InGameTimer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public class CheatingMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void cheat(CallbackInfo ci) {
        if (InGameTimer.getInstance().isStarted() && !InGameTimer.getInstance().isCompleted() && MCSREloProject.RUNNING_MATCH) {
            MinecraftClient.getInstance().openScreen(new CreditsScreen(true, () -> {
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
            }));
            System.out.println("Attempted credits screen open");
        }
    }
}
