package io.github.randalf.project.commands;

import io.github.randalf.project.manager.AreaManager;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

/**
 * CommandExecutor for creating an arena
 */
public class ArenaCreateCommand implements CommandExecutor {

    /**
     * Get's the ArenaManager and create and saves the new arena with a given area.
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String arenaName = args.<String>getOne("arenaName").get();
        String areaName = args.<String>getOne("areaName").get();
        if(AreaManager.getInstance().mapContains(areaName)){
            src.sendMessage(Text.of("Arena " + arenaName + " will be created for the area " + areaName));
            ArenaManager.getInstance().createArena(arenaName,areaName);
        } else {
            src.sendMessage(Text.of("Area "  + areaName+  " doesn't exists. \n For a list of all areas type /arena list area"));
        }
        return CommandResult.success();
    }
}
