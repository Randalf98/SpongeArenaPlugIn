package io.github.randalf.project.commands;

import io.github.randalf.project.manager.ZoneManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

/**
 * CommandExecutor for adding a spawnpoint to an zone
 */
public class ZoneAddSpawnPointCommand implements CommandExecutor {

    /**
     * Get's the ZoneManager and adds a spawnpoint at the players location.
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String zoneName = args.<String>getOne("zoneName").get();
        Player player = (Player) src;
        ZoneManager.getInstance().addSpawnPointToZone(zoneName, player);
        return CommandResult.success();
    }
}