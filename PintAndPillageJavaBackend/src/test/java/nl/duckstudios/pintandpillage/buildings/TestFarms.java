package nl.duckstudios.pintandpillage.buildings;

import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Farm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@Tag("Building")
public class TestFarms {

    @Mock
    Village villageMock;

    @Mock
    Farm testingFarm;

    @BeforeEach
    void setup(){
        this.villageMock = new Village();
        this.villageMock.setResourceLimit(100000); // So we dont have to think about storage
        this.testingFarm = createFarm(1);
    }

    private Farm createFarm(int level) {
        Farm farm = new Farm();
        farm.setLevel(level);
        farm.setConstructionTimeSeconds(0);
        farm.setVillage(villageMock);
        LocalDateTime now = LocalDateTime.now();
        farm.setLastCollected(now);

        return farm;
    }

    @Test
    public void increasedHopProductionWhenBuildingFarm() {
        villageMock.createBuilding(createFarm(1));

        Map<String, Integer> resourcesPerHour = villageMock.getResourcesPerHour();

        int ACTUAL_HOP_PRODUCTION = resourcesPerHour.get("Hop");
        int EXPECTED_HOP_PRODUCTION = 32;

        assertThat(ACTUAL_HOP_PRODUCTION, is(EXPECTED_HOP_PRODUCTION));
    }

    @Test
    public void increasedHopProductionWithMultipleFarms() {
        villageMock.createBuilding(createFarm(1));
        villageMock.createBuilding(createFarm(1));
        villageMock.createBuilding(createFarm(1));

        Map<String, Integer> resourcesPerHour = villageMock.getResourcesPerHour();

        int ACTUAL_HOP_PRODUCTION = resourcesPerHour.get("Hop");
        int EXPECTED_HOP_PRODUCTION = 32*3;

        assertThat(ACTUAL_HOP_PRODUCTION, is(EXPECTED_HOP_PRODUCTION));
    }

    @Test
    public void collectAfterHourToSeeProduction() {

        LocalDateTime oneHourBack = LocalDateTime.now().minusHours(1);
        this.testingFarm.setLastCollected(oneHourBack);
        this.testingFarm.collectResources();

        int ACTUAL_HOP_PRODUCTION = villageMock.getVillageResources().get("Hop");

        int EXPECTED_HOP_PRODUCTION = 20;

        assertThat(ACTUAL_HOP_PRODUCTION, is(EXPECTED_HOP_PRODUCTION));

    }
    @Test
    public void noIncreasingHopWithoutFarms() {
        villageMock.updateVillageState();
        int ACTUAL_HOP_PRODUCTION = villageMock.getVillageResources().get("Hop");
        int EXPECTED_HOP_PRODUCTION = 0; // 0 is the base value

        assertThat(ACTUAL_HOP_PRODUCTION, is(EXPECTED_HOP_PRODUCTION));

    }
}
