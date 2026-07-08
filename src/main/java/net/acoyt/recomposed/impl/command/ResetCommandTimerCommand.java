package net.acoyt.recomposed.impl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.acoyt.recomposed.impl.cca.entity.CombatTimerComponent;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

/**
 * @author AcoYT
 */
public class ResetCommandTimerCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext ignoredAccess, Commands.CommandSelection ignoredDedicated) {
        dispatcher.register(literal("reset_combat_timer").requires(source -> source.hasPermission(2))
                .then(argument("targets", EntityArgument.players())
                        .executes(context -> {
                            List<ServerPlayer> entities = new ArrayList<>(EntityArgument.getPlayers(context, "targets"));
                            for (ServerPlayer player : entities) {
                                CombatTimerComponent.KEY.get(player).setRemaining(0);
                            }

                            context.getSource().sendSuccess(() -> Component.literal("Reset combat timer for " + (entities.size() == 1 ? entities.getFirst().getDisplayName().getString() : entities.size() + " entities")), false);

                            return Command.SINGLE_SUCCESS;
                        })
                )
        );
    }
}
