package net.acoyt.recomposed.impl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.acoyt.recomposed.impl.cca.entity.CombatTimerComponent;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author AcoYT
 */
public class ResetCommandTimerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess ignoredAccess, CommandManager.RegistrationEnvironment ignoredDedicated) {
        dispatcher.register(literal("reset_combat_timer").requires(source -> source.hasPermissionLevel(2))
                .then(argument("targets", EntityArgumentType.players())
                        .executes(context -> {
                            List<ServerPlayerEntity> entities = new ArrayList<>(EntityArgumentType.getPlayers(context, "targets"));
                            for (ServerPlayerEntity player : entities) {
                                CombatTimerComponent.KEY.get(player).setRemaining(0);
                            }

                            context.getSource().sendFeedback(() -> Text.literal("Reset combat timer for " + (entities.size() == 1 ? entities.getFirst().getDisplayName().getString() : entities.size() + " entities")), false);

                            return Command.SINGLE_SUCCESS;
                        })
                )
        );
    }
}
