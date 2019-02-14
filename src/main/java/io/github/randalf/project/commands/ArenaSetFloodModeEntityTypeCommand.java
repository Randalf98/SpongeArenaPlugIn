package io.github.randalf.project.commands;

import io.github.randalf.project.arenaparts.spawner.FloodMode;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class ArenaSetFloodModeEntityTypeCommand implements CommandExecutor {

    /**
     * Command implementing CommandExecutor.
     * Sets EntityType of FloodMode
     *
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String arenaName = args.<String>getOne("arenaName").get();
        String entityType = args.<String>getOne("entityType").get();
        ((FloodMode)ArenaManager.getInstance().getArena(arenaName).getMode()).setEntityType(entityType);
        return CommandResult.success();
    }
}
