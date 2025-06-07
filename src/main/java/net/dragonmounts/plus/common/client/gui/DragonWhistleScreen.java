package net.dragonmounts.plus.common.client.gui;

import net.dragonmounts.plus.common.init.DMSounds;
import net.dragonmounts.plus.common.network.c2s.TeleportDragonPayload;
import net.dragonmounts.plus.common.network.c2s.ToggleFollowingPayload;
import net.dragonmounts.plus.common.network.c2s.ToggleSittingByUUIDPayload;
import net.dragonmounts.plus.compat.platform.ClientNetworkHandler;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DragonWhistleScreen extends Screen {
    private static final int BUTTON_SPACING = 8;
    private static final int BUTTON_WIDTH = 210;
    private static final Component TITLE = Component.translatable("gui.dragonmounts.plus.dragon_whistle");
    private static final Component TELEPORT_TO_PLAYER = Component.translatable("button.dragonmounts.plus.teleport_to_player");
    private static final Component TOGGLE_SITING = Component.translatable("button.dragonmounts.plus.toggle_siting");
    private static final Component TOGGLE_FOLLOWING = Component.translatable("button.dragonmounts.plus.toggle_following");
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    public final UUID uuid;

    public DragonWhistleScreen(UUID uuid) {
        super(TITLE);
        this.uuid = uuid;
    }

    @Override
    protected void init() {
        super.init();
        this.layout.addTitleHeader(TITLE, this.font);
        LinearLayout linear = this.layout.addToContents(LinearLayout.vertical()).spacing(BUTTON_SPACING);
        linear.defaultCellSetting().alignHorizontallyCenter();
        linear.addChild(Button.builder(
                TELEPORT_TO_PLAYER,
                this::teleportDragon
        ).width(BUTTON_WIDTH).build());
        linear.addChild(Button.builder(
                TOGGLE_SITING,
                this::toggleSiting
        ).width(BUTTON_WIDTH).build());
        linear.addChild(Button.builder(
                TOGGLE_FOLLOWING,
                this::toggleFollowing
        ).width(BUTTON_WIDTH).build());
        this.layout.addToFooter(Button.builder(
                CommonComponents.GUI_CANCEL,
                button -> this.onClose()
        ).width(Button.BIG_WIDTH).build());
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    public void teleportDragon(@Nullable Button ignored) {
        assert this.minecraft != null;
        if (this.minecraft.hitResult instanceof BlockHitResult hit) {
            ClientNetworkHandler.send(new TeleportDragonPayload(this.uuid, hit.getBlockPos()));
        } else {
            this.minecraft.gui.setOverlayMessage(Component.translatable("message.dragonmounts.plus.whistle.invalid_pos"), false);
            var player = this.minecraft.player;
            if (player != null) {
                player.clientLevel.playLocalSound(player, DMSounds.WHISTLE_BLOW_LONG, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
        this.onClose();
    }

    public void toggleSiting(@Nullable Button ignored) {
        ClientNetworkHandler.send(new ToggleSittingByUUIDPayload(this.uuid));
        this.onClose();
    }

    public void toggleFollowing(@Nullable Button ignored) {
        ClientNetworkHandler.send(new ToggleFollowingPayload(this.uuid));
        this.onClose();
    }
}
