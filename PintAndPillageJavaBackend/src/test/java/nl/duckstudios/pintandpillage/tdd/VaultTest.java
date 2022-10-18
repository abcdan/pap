package nl.duckstudios.pintandpillage.tdd;

import nl.duckstudios.pintandpillage.entity.buildings.Vault;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class VaultTest {


    @Test
    public void should_vaultNameEqualsVault_whenVault() {
        // Arrange
        Vault vault = new Vault();

        // Act
        String ACTUAL_NAME = vault.getName();
        String EXPECTED_NAME = "Vault";

        // Assert
        assertThat(ACTUAL_NAME, is(EXPECTED_NAME));
    }

    @Test
    public void should_setConstructionTime_whenInitialized() {
        // Arrange
        Vault vault = new Vault();

        // Act
        LocalTime ACTUAL_TIME = vault.getConstructionTime();
        LocalTime EXPECTED_TIME = LocalTime.of(0, 0, 10);

        // Assert
        assertThat(ACTUAL_TIME, is(EXPECTED_TIME));


    }

    @Test
    public void should_setConstructionTimeWhenLevel1_whenInitialized() {
        // Arrange
        Vault vault = new Vault();
        vault.setLevel(3);
        vault.updateBuilding();

        // Act
        LocalTime ACTUAL_TIME = vault.getConstructionTime();
        LocalTime EXPECTED_TIME = LocalTime.of(0, 1, 10);

        // Assert
        assertThat(ACTUAL_TIME, is(EXPECTED_TIME));
    }

    @Test
    public void should_setWoodRequired_whenInitialized () {
        // Arrange
        Vault vault = new Vault();

        // Act
        Integer ACTUAL_WOOD = vault.getResourcesRequiredLevelUp().get("Wood");
        Integer EXPECTED_WOOD = 25;

        // Assert
        assertThat(ACTUAL_WOOD, is(EXPECTED_WOOD));
    }

    @Test
    public void should_setStoneRequired_whenInitialized () {
        // Arrange
        Vault vault = new Vault();

        // Act
        Integer ACTUAL_STONE = vault.getResourcesRequiredLevelUp().get("Stone");
        Integer EXPECTED_STONE = 25;

        // Assert
        assertThat(ACTUAL_STONE, is(EXPECTED_STONE));
    }


    @Test
    public void should_setWoodRequiredWhenLevel3_whenInitialized () {
        // Arrange
        Vault vault = new Vault();
        vault.setLevel(3);
        vault.updateBuilding();

        // Act
        Integer ACTUAL_WOOD = vault.getResourcesRequiredLevelUp().get("Wood");
        Integer EXPECTED_WOOD = 25;

        // Assert
        assertThat(ACTUAL_WOOD, is(EXPECTED_WOOD));
    }

    @Test
    public void should_setStoneRequiredWhenLevel3_whenInitialized () {
        // Arrange
        Vault vault = new Vault();
        vault.setLevel(3);
        vault.updateBuilding();

        // Act
        Integer ACTUAL_STONE = vault.getResourcesRequiredLevelUp().get("Stone");
        Integer EXPECTED_STONE = 25;

        // Assert
        assertThat(ACTUAL_STONE, is(EXPECTED_STONE));
    }

}
