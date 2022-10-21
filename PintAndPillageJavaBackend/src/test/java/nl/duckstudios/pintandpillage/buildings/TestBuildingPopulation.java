package nl.duckstudios.pintandpillage.buildings;

import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.House;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@Tag("Building")
public class TestBuildingPopulation {

    @Mock
    Village villageMock;

    @BeforeEach
    void setup(){
        this.villageMock = new Village();
    }

    @Test
    public void should_notIncreasePopulation_whenUnderConstruction() {
        House house = new House();
        house.setLevel(1);
        house.setConstructionTimeSeconds(0);


        villageMock.createBuilding(house);

        house.updateBuilding();

        int ACTUAL_POPULATION = villageMock.getPopulation();
        int EXPECTED_POPULATION = 21;

        assertThat(ACTUAL_POPULATION, is(EXPECTED_POPULATION));

    }
}
