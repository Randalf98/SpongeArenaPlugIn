package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public interface SpawnMode {
    List<Entity> toBeSpawnedEntitiesList = new ArrayList<>();
    List<Entity> getNextEntities(Vector3d location);

    public default List<Entity> getEntitiesList(){
        return toBeSpawnedEntitiesList;
    }
}
