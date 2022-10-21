package nl.duckstudios.pintandpillage.buildings;

import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
@Tag("Building")
public class TestStorage {

    @Mock
    public Village villageMock;

    @BeforeEach
    void setup(){
        this.villageMock = new Village();
    }

    @Test
    public void should_minimumResourceLimit_whenInitialized() {
        Storage storage = new Storage();
        storage.setResourceCapacity(0);
        storage.setConstructionTimeSeconds(0);

        villageMock.setResourceLimit(10000000);
        villageMock.createBuilding(storage);

        int EXPECTED_RESOURCE_LIMIT = 601; // 600 + 2500 * level + 1
        int ACTUAL_RESOURCE_LIMIT = villageMock.getResourceLimit();

        assertThat(ACTUAL_RESOURCE_LIMIT, is(EXPECTED_RESOURCE_LIMIT));
    }


    @Test
    public void should_increaseResourceLimit_whenLeveledUp() {
        Storage storage = new Storage();
        storage.setResourceCapacity(0);
        storage.setConstructionTimeSeconds(0);
        storage.setLevel(2);

        villageMock.setResourceLimit(10000000);
        villageMock.createBuilding(storage);

        int EXPECTED_RESOURCE_LIMIT = 5601; // 600 + 2500 * level + 1
        int ACTUAL_RESOURCE_LIMIT = villageMock.getResourceLimit();

        assertThat(ACTUAL_RESOURCE_LIMIT, is(EXPECTED_RESOURCE_LIMIT));
    }
}
