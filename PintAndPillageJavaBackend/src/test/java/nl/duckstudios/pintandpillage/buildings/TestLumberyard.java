package nl.duckstudios.pintandpillage.buildings;

import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Lumberyard;
import nl.duckstudios.pintandpillage.testHelpers.ResourceHelper;
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
public class TestLumberyard {

    @Mock
    Village villageMock;

    @Mock
    Lumberyard testingLumberyard;

    private final ResourceHelper resourceHelper = new ResourceHelper();
    @BeforeEach
    void setup(){
        this.villageMock = new Village();
        this.villageMock.setResourceLimit(100000); // So we dont have to think about storage
        this.testingLumberyard = createLumberyard(1);
    }

    private Lumberyard createLumberyard(int level) {
        Lumberyard lumberyard = new Lumberyard();
        lumberyard.setLevel(level);
        lumberyard.setConstructionTimeSeconds(0);
        lumberyard.setVillage(villageMock);
        LocalDateTime now = LocalDateTime.now();
        lumberyard.setLastCollected(now);

        return lumberyard;
    }

    @Test
    public void increasedWoodProductionWhenBuildingLumberyard() {


        villageMock.createBuilding(createLumberyard(1));

        Map<String, Integer> resourcesPerHour = villageMock.getResourcesPerHour();

        int ACTUAL_WOOD_PRODUCTION = resourcesPerHour.get("Wood");
        int EXPECTED_WOOD_PRODUCTION = 32;

        assertThat(ACTUAL_WOOD_PRODUCTION, is(EXPECTED_WOOD_PRODUCTION));
    }

    @Test
    public void increasedWoodProductionWithMultipleLumberyards() {

        villageMock.createBuilding(createLumberyard(1));
        villageMock.createBuilding(createLumberyard(1));
        villageMock.createBuilding(createLumberyard(1));

        Map<String, Integer> resourcesPerHour = villageMock.getResourcesPerHour();

        int ACTUAL_WOOD_PRODUCTION = resourcesPerHour.get("Wood");
        int EXPECTED_WOOD_PRODUCTION = 32*3;

        assertThat(ACTUAL_WOOD_PRODUCTION, is(EXPECTED_WOOD_PRODUCTION));
    }

    @Test
    public void collectAfterHourToSeeProduction() {

        LocalDateTime oneHourBack = LocalDateTime.now().minusHours(1);
        this.testingLumberyard.setLastCollected(oneHourBack);
        this.testingLumberyard.collectResources();

        int ACTUAL_WOOD_PRODUCTION = villageMock.getVillageResources().get("Wood");

        int EXPECTED_WOOD_PRODUCTION = 520;

        assertThat(ACTUAL_WOOD_PRODUCTION, is(EXPECTED_WOOD_PRODUCTION));

    }
    @Test
    public void noIncreasingWoodWithoutLumberyards() {
        villageMock.updateVillageState();
        int ACTUAL_WOOD_PRODUCTION = villageMock.getVillageResources().get("Wood");
        int EXPECTED_WOOD_PRODUCTION = 500; // 500 is the base value

        assertThat(ACTUAL_WOOD_PRODUCTION, is(EXPECTED_WOOD_PRODUCTION));

    }
}
