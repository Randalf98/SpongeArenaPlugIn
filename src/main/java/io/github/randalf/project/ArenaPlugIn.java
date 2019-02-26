package io.github.randalf.project;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.commands.*;
import io.github.randalf.project.manager.ArenaManager;
import io.github.randalf.project.serializer.AreaSerializer;
import io.github.randalf.project.serializer.Vector3dSerializer;
import io.github.randalf.project.serializer.Vector3iSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * An arena plugin for Sponge server to let the player fight monsters.
 *
 * @author Randalf
 * @version 1.0
 */
@Plugin(id = ArenaPlugIn.PLUGIN_ID, name = "Area", version = "1.0", description = "A little arena Plug-In")
@Singleton
public class ArenaPlugIn {

    public static final String PLUGIN_ID = "arena";
    private static ArenaPlugIn instance;

    @Inject
    Game game;

    @Inject
    Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {}

    /**
     * When the Plugin gets initialized the function gets executed.
     * @param event Default GameInitializationEvent from the server
     */
    @Listener
    public void onInitialization(GameInitializationEvent event) {
        instance = this;
        setupCommands();
        setupSerializer();
    }

    /**
     * Functionality to get the singleton instance
     * @return The instance of this Plugin
     */
    public static ArenaPlugIn getInstance(){
        return instance;
    }

    /**
     * Routine for setting up all commands
     */
    private void setupCommands() {
        //Command Spec for starting a Round
        CommandSpec arenaStartCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Start Command"))
                .permission("io.github.randalf.startArena")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("arenaName")))
                .executor(new ArenaStartCommand())
                .build();

        //Command Spec for stopping a Round
        CommandSpec arenaStopCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Stop Command"))
                .permission("io.github.randalf.stopArena")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("arenaName")))
                .executor(new ArenaStopCommand())
                .build();

        //Command Spec for creating a Area
        CommandSpec arenaCreateCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Create Command"))
                .permission("io.github.randalf.createArena")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("areaName")))
                .executor(new ArenaCreateCommand())
                .build();

        //Command Spec for creating a Area
        CommandSpec areaCreateCommandSpec = CommandSpec.builder()
                .description(Text.of("Area Create Command"))
                .permission("io.github.randalf.createArea")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaCreateCommand())
                .build();

        //Command Spec for adding a chunk to area
        CommandSpec areaAddChunkCommandSpec = CommandSpec.builder()
                .description(Text.of("Add Chunk Command"))
                .permission("io.github.randalf.addChunkToArea")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaAddChunkCommand())
                .build();

        //Command Spec for adding a spawnpoint to area
        CommandSpec areaAddSpawnPointCommandSpec = CommandSpec.builder()
                .description(Text.of("Add Spawnpoint Command"))
                .permission("io.github.randalf.addSpawnPointToArea")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaAddSpawnPointCommand())
                .build();

        //Command Spec for listing all areas
        CommandSpec listAreasCommandSpec = CommandSpec.builder()
                .description(Text.of("List Areas Command"))
                .permission("io.github.randalf.listAreas")
                .executor(new ListAreaCommand())
                .build();

        //Command Spec for listing all arenas
        CommandSpec listArenasCommandSpec = CommandSpec.builder()
                .description(Text.of("List Arenas Command"))
                .permission("io.github.randalf.listArenas")
                .executor(new ListArenaCommand())
                .build();

        //Command Spec for giving the information of an arena
        CommandSpec arenaInformationCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Information Command"))
                .permission("io.github.randalf.arenaInformation")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("arenaName")))
                .executor(new ArenaInformationCommand())
                .build();

        //Command Spec for setting the entity type for a flood arena
        CommandSpec arenaSetFloodModeEntityTypeCommandSpec = CommandSpec.builder()
                .description(Text.of("Sets Flood Mode Entity Type"))
                .permission("io.github.randalf.setEntityType")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("entityType")))
                .executor(new ArenaSetFloodModeEntityTypeCommand())
                .build();

        //Command Spec for setting the entity amount for a flood arena
        CommandSpec arenaSetFloodModeEntityAmountCommandSpec = CommandSpec.builder()
                .description(Text.of("Sets Flood Mode Entity Amount"))
                .permission("io.github.randalf.setEntityAmount")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("entityAmount")))
                .executor(new ArenaSetFloodModeEntityAmountCommand())
                .build();

        //Command Spec for adding an option to an arena
        CommandSpec arenaAddOptionCommandSpec = CommandSpec.builder()
                .description(Text.of("Adds an option to the arena"))
                .permission("io.github.randalf.addOption")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("arenaOption")))
                .executor(new ArenaAddOptionCommand())
                .build();

        //Command Spec for removing an option from an arena
        CommandSpec arenaRemoveOptionCommandSpec = CommandSpec.builder()
                .description(Text.of("Removes an option to the arena"))
                .permission("io.github.randalf.removeOption")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("arenaOption")))
                .executor(new ArenaRemoveOptionCommand())
                .build();

        //Adding all command Specs to the commang manager
        Sponge.getCommandManager().register(this, arenaStartCommandSpec, "startArena");
        Sponge.getCommandManager().register(this, arenaStopCommandSpec, "stopArena");
        Sponge.getCommandManager().register(this, arenaCreateCommandSpec, "createArena");
        Sponge.getCommandManager().register(this, areaCreateCommandSpec, "createArea");
        Sponge.getCommandManager().register(this, areaAddChunkCommandSpec, "addChunkToArea");
        Sponge.getCommandManager().register(this, areaAddSpawnPointCommandSpec, "addSpawnPointToArea");
        Sponge.getCommandManager().register(this, listAreasCommandSpec, "listAreas");
        Sponge.getCommandManager().register(this, listArenasCommandSpec, "listArenas");
        Sponge.getCommandManager().register(this, arenaInformationCommandSpec, "arenaInformation");
        Sponge.getCommandManager().register(this, arenaSetFloodModeEntityTypeCommandSpec, "setEntityType");
        Sponge.getCommandManager().register(this, arenaSetFloodModeEntityAmountCommandSpec, "setEntityAmount");
        Sponge.getCommandManager().register(this, arenaAddOptionCommandSpec, "addOption");
        Sponge.getCommandManager().register(this, arenaRemoveOptionCommandSpec, "removeOption");
    }

    /**
     * Routine for registring the Typetoken for Serialization and Deserialization
     */
    private void setupSerializer() {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Area.class), new AreaSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Vector3d.class), new Vector3dSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Vector3i.class), new Vector3iSerializer());
    }

    /**
     * Getter for the ArenaManager instance
     * @return
     */
    public ArenaManager getArenaManager() {
        return ArenaManager.getInstance();
    }
}
