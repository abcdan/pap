package nl.duckstudios.pintandpillage.entity.buildings;

import lombok.Getter;
import lombok.Setter;
import nl.duckstudios.pintandpillage.model.ResourceType;

import java.util.HashMap;

public class VaultExample extends Building {
    @Getter
    @Setter
    public String name = "Vault";

    public VaultExample() {
        this.updateBuilding();

    }

    @Override
    public void updateBuilding() {
       this.setConstructionTimeGivenLevel(this.getLevel());

    }

    private void setConstructionTimeGivenLevel(int level) {
        long TIME_INCREMENTAL_PER_LEVEL = 20L;
        int MINIMUM_CONSTRUCTION_TIME = 10;
        this.setConstructionTimeSeconds(TIME_INCREMENTAL_PER_LEVEL * level + MINIMUM_CONSTRUCTION_TIME);
        super.setResourcesRequiredLevelUp(new HashMap<>() {
            {
                int level = VaultExample.super.getLevel();
                put(ResourceType.Wood.name(), level * 25 + 25);
                put(ResourceType.Stone.name(), level * 25 + 25);
            }
        });
    }
}
