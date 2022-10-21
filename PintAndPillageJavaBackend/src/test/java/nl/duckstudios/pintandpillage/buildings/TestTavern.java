package nl.duckstudios.pintandpillage.buildings;

import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Tavern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@Tag("Building")
public class TestTavern {

    @Mock
    Village villageMock;

    @Mock
    Tavern testingTavern;

    @BeforeEach
    void setup(){
        this.villageMock = new Village();
        this.villageMock.setResourceLimit(100000); // So we dont have to think about storage
        this.testingTavern = createTavern(1);
    }

    private Tavern createTavern(int level) {
        Tavern tavern = new Tavern();
        tavern.setLevel(level);
        tavern.setConstructionTimeSeconds(0);
        tavern.setVillage(villageMock);
        LocalDateTime now = LocalDateTime.now();
        tavern.setLastCollected(now);

        return tavern;
    }

    @Test
    public void should_convertHopIntoBeer_withTavern() {

        Map<String, Integer> resources = new HashMap<>();
        resources.put("Hop", 100);
        resources.put("Beer", 0);
        villageMock.setVillageResources(resources);


        LocalDateTime oneHourBack = LocalDateTime.now().minusHours(1);
        this.testingTavern.setLastCollected(oneHourBack);
        this.testingTavern.collectResources();

        testingTavern.updateBuilding();

        Map<String, Integer> villageResources = villageMock.getVillageResources();
        System.out.println(villageResources);
        int ACTUAL_BEER_PRODUCTION = villageResources.get("Beer");
        int EXPECTED_BEER_PRODUCTION = 10;

        assertThat(ACTUAL_BEER_PRODUCTION, is(EXPECTED_BEER_PRODUCTION));
    }


    @Test
    public void should_notConvertHopIntoBeer_withoutEnoughHop() {

        Map<String, Integer> resources = new HashMap<>();
        resources.put("Hop", 0);
        resources.put("Beer", 0);
        villageMock.setVillageResources(resources);


        LocalDateTime oneHourBack = LocalDateTime.now().minusHours(1);
        this.testingTavern.setLastCollected(oneHourBack);
        this.testingTavern.collectResources();

        testingTavern.updateBuilding();

        Map<String, Integer> villageResources = villageMock.getVillageResources();
        System.out.println(villageResources);
        int ACTUAL_BEER_PRODUCTION = villageResources.get("Beer");
        int EXPECTED_BEER_PRODUCTION = 0;

        assertThat(ACTUAL_BEER_PRODUCTION, is(EXPECTED_BEER_PRODUCTION));
    }

}
