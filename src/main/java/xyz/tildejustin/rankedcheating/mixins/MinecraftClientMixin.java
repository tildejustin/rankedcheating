package xyz.tildejustin.rankedcheating.mixins;

import com.mcsr.projectelo.EloWebSocket;
import com.mcsr.projectelo.MCSREloProject;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private boolean sent = false;

    @Inject(method = "render", at = @At("HEAD"))
    public void cheat(CallbackInfo ci) {

//        if (InGameTimer.getInstance().isStarted() && !InGameTimer.getInstance().isCompleted() && MCSREloProject.RUNNING_MATCH) {
//            MinecraftClient.getInstance().openScreen(new CreditsScreen(true, () -> {
//                assert MinecraftClient.getInstance().player != null;
//                MinecraftClient.getInstance().player.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
//            }));
//            System.out.println("Attempted credits screen open");
//        }

        if (MCSREloProject.RUNNING_MATCH && !sent && MinecraftClient.getInstance().player != null) {
            sent = true;
            EloWebSocket.getInstance().sendSingle("match_complete");
            System.out.println("Attempted sending match_complete message");
        }

        if (!MCSREloProject.RUNNING_MATCH && sent) {
            sent = false;
        }
    }
}
