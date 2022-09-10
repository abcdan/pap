package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.entity.buildings.Building;
import nl.duckstudios.pintandpillage.helper.Manager;

public class MockBuilding extends Building
{

    @Override
    public void updateBuilding() {
        super.setConstructionTimeSeconds(10);
    }

    public void setResourceManager(Manager resourceManager){
        super.resourceManager = resourceManager;
    }
}
