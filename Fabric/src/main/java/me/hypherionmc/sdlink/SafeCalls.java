package me.hypherionmc.sdlink;

import me.hypherionmc.sdlink.server.ServerEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.samo_lego.fabrictailor.casts.TailoredPlayer;

/**
 * @author HypherionSA
 * @date 29/10/2022
 */
public class SafeCalls {

    public static void tailorPlayerCommand(ServerPlayer player, String command) {
        if (player instanceof TailoredPlayer tp) {
            ServerEvents.getInstance().commandEvent(
                    command,
                    player.getDisplayName(),
                    tp.getSkinId()
            );
        }
    }

    public static void tailerPlayerMessage(ServerPlayer player, Component message) {
        if (player instanceof TailoredPlayer tp) {
            ServerEvents.getInstance().onServerChatEvent(
                    message,
                    player.getDisplayName(),
                    tp.getSkinId()
            );
        }
    }

}
