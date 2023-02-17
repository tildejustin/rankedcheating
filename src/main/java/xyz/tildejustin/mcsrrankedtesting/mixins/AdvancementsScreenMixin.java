package xyz.tildejustin.mcsrrankedtesting.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementsScreen.class)
public abstract class AdvancementsScreenMixin extends Screen {

    protected AdvancementsScreenMixin(Text title) {
        super(title);
    }

    @Overwrite
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.client.options.keyAdvancements.matchesKey(keyCode, scanCode)) {
            MinecraftClient.getInstance().openScreen(new CreditsScreen(true, () -> MinecraftClient.getInstance().player.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN))));
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
