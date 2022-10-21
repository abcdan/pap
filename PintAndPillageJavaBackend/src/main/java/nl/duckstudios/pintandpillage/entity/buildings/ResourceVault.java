package nl.duckstudios.pintandpillage.entity.buildings;

import lombok.Getter;
import lombok.Setter;

public class ResourceVault extends Building {

    @Getter
    @Setter
    public String name = "Vault";

    public ResourceVault() {
        this.updateBuilding();
    }

    @Override
    public void updateBuilding() {
        this.setConstructionTimeGivenLevel(this.getLevel());
    }

    private void setConstructionTimeGivenLevel(int level) {
        long seconds = 20L * level + 10;
        this.setConstructionTimeSeconds(seconds);

    }
}
