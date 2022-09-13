package nl.duckstudios.pintandpillage.buildings;

import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Mine;
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
public class TestMines {

    @Mock
    Village villageMock;

    @Mock
    Mine testingMine;

    @BeforeEach
    void setup(){
        this.villageMock = new Village();
        this.villageMock.setResourceLimit(100000); // So we dont have to think about storage
        this.testingMine = createMine(1);
    }

    private Mine createMine(int level) {
        Mine mine = new Mine();
        mine.setLevel(level);
        mine.setConstructionTimeSeconds(0);
        mine.setVillage(villageMock);
        LocalDateTime now = LocalDateTime.now();
        mine.setLastCollected(now);

        return mine;
    }

    @Test
    public void increasedStoneProductionWithMine() {
        villageMock.createBuilding(createMine(1));

        Map<String, Integer> resourcesPerHour = villageMock.getResourcesPerHour();

        int ACTUAL_WOOD_PRODUCTION = resourcesPerHour.get("Stone");
        int EXPECTED_WOOD_PRODUCTION = 32;

        assertThat(ACTUAL_WOOD_PRODUCTION, is(EXPECTED_WOOD_PRODUCTION));
    }

    @Test
    public void increasedStoneProductionWithMultipleMines() {
        villageMock.createBuilding(createMine(1));
        villageMock.createBuilding(createMine(1));
        villageMock.createBuilding(createMine(1));

        Map<String, Integer> resourcesPerHour = villageMock.getResourcesPerHour();

        int ACTUAL_STONE_PRODUCTION = resourcesPerHour.get("Stone");
        int EXPECTED_STONE_PRODUCTION = 32*3;

        assertThat(ACTUAL_STONE_PRODUCTION, is(EXPECTED_STONE_PRODUCTION));
    }

    @Test
    public void collectAfterHourToSeeProduction() {

        LocalDateTime oneHourBack = LocalDateTime.now().minusHours(1);
        this.testingMine.setLastCollected(oneHourBack);
        this.testingMine.collectResources();

        int ACTUAL_STONE_PRODUCTION = villageMock.getVillageResources().get("Stone");

        int EXPECTED_STONE_PRODUCTION = 520;

        assertThat(ACTUAL_STONE_PRODUCTION, is(EXPECTED_STONE_PRODUCTION));

    }
    @Test
    public void noIncreasingStoneWithoutLumberyards() {
        villageMock.updateVillageState();
        int ACTUAL_STONE_PRODUCTION = villageMock.getVillageResources().get("Stone");
        int EXPECTED_STONE_PRODUCTION = 500; // 500 is the base value

        assertThat(ACTUAL_STONE_PRODUCTION, is(EXPECTED_STONE_PRODUCTION));

    }
}
