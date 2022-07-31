package me.hypherionmc.sdlink;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.hypherionmc.sdlink.server.ServerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

import java.util.UUID;

/**
 * @author HypherionSA
 * @date 18/06/2022
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeEventHandler {

    private static final ServerEvents serverEvents = ServerEvents.getInstance();

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        serverEvents.onCommandRegister(event.getDispatcher());
    }

    @SubscribeEvent
    public void serverStartedEvent(FMLServerAboutToStartEvent event) {
        serverEvents.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public void serverStartedEvent(FMLServerStartingEvent event) {
        serverEvents.onServerStarted();
    }

    @SubscribeEvent
    public void serverStoppingEvent(FMLServerStoppingEvent event) {
        serverEvents.onServerStopping();
    }

    @SubscribeEvent
    public void serverStoppedEvent(FMLServerStoppedEvent event) {
        serverEvents.onServerStoppedEvent();
    }

    @SubscribeEvent
    public void serverChatEvent(ServerChatEvent event) {
        serverEvents.onServerChatEvent(event.getMessage(), event.getUsername(), event.getPlayer().getUUID());
    }

    @SubscribeEvent
    public void commandEvent(CommandEvent event) {
        String command = event.getParseResults().getReader().getString();
        UUID uuid = null;
        try {
            uuid = event.getParseResults().getContext().getLastChild().getSource().getPlayerOrException().getUUID();
        } catch (CommandSyntaxException ignored) {}
        serverEvents.commandEvent(
                command,
                event.getParseResults().getContext().getLastChild().getSource().getDisplayName().getString(),
                uuid
        );
    }

    @SubscribeEvent
    public void playerJoinEvent(PlayerEvent.PlayerLoggedInEvent event) {
        serverEvents.playerJoinEvent(event.getPlayer());
    }

    @SubscribeEvent
    public void playerLeaveEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        serverEvents.playerLeaveEvent(event.getPlayer());
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)event.getEntityLiving();
            serverEvents.onPlayerDeath(
                    player,
                    event.getSource().getLocalizedDeathMessage(event.getEntityLiving()).getString()
            );
        }
    }

    @SubscribeEvent
    public void onPlayerAdvancement(AdvancementEvent event) {
        if (event.getAdvancement() != null && event.getAdvancement().getDisplay() != null && event.getAdvancement().getDisplay().shouldAnnounceChat()) {
            serverEvents.onPlayerAdvancement(
                    event.getPlayer().getDisplayName().getString(),
                    TextFormatting.stripFormatting(event.getAdvancement().getDisplay().getTitle().getString()),
                    TextFormatting.stripFormatting(event.getAdvancement().getDisplay().getDescription().getString())
            );
        }
    }

}
