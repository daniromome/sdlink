package me.hypherionmc.sdlink.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.hypherionmc.sdlink.server.ServerEvents;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

/**
 * @author HypherionSA
 * @date 29/10/2022
 */
public class ReloadModCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> discordCommand =
                Commands.literal("sdreload")
                        .requires((commandSource) -> commandSource.hasPermission(0))
                        .executes(context -> {
                            ServerEvents.reloadInstance(context.getSource().getServer());
                            return 0;
                        });

        dispatcher.register(discordCommand);
    }

}