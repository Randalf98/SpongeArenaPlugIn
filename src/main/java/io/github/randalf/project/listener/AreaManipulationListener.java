package io.github.randalf.project.listener;

import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.Area;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.filter.type.Include;

/**
 * Listener used to detect and prevent manipulation of the area
 */
public class AreaManipulationListener extends ArenaListener {

    private Area area;

    /**
     * Constructor for achieving the functionality of the listener
     * @param area spawner object of the arena
     */
    public AreaManipulationListener(Area area) {
        super();
        this.area = area;
    }

    /**
     * istener to react when an event of a ChangeBlockEvent occurs
     * @param event the SpawnEntityEvent containing all necessary information to evaluate if the block change should be ignored
     * @param player the Player is used to detect if the player is in the area of the arena
     */
    @Listener
    @Include({
            ChangeBlockEvent.Break.class,
            ChangeBlockEvent.Place.class
    })
    public void onBlockBreakingAndPlacing(ChangeBlockEvent event, @Root Player player) {
        Vector3i playerPosition = player.getLocation().getChunkPosition();
        for (Vector3i chunk: area.getAreaChunks()){
            if (chunk.equals(playerPosition)){
                event.setCancelled(true);
            }
        }
    }

}
