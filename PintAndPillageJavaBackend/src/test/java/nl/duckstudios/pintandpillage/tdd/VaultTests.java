package nl.duckstudios.pintandpillage.tdd;

import nl.duckstudios.pintandpillage.entity.buildings.ResourceVault;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class VaultTests {

    @Test
    public void should_vaultNameEqualsVault_whenVault() {
        // Arrange
        ResourceVault vault = new ResourceVault();

        // Act
        String ACTUAL_NAME = vault.getName();
        String EXPECTED_NAME = "Vault";

        // Assert
        assertThat(ACTUAL_NAME, is(EXPECTED_NAME));
    }

    @Test
    public void should_setConstructionTime_whenInitialized() {
        // Arrange
        ResourceVault vault = new ResourceVault();

        // Act
        LocalTime ACTUAL_TIME = vault.getConstructionTime();
        LocalTime EXPECTED_TIME = LocalTime.of(0, 0, 10);

        // Assert
        assertThat(ACTUAL_TIME, is(EXPECTED_TIME));
    }

    @Test
    public void should_setConstructionTime_whenLevelThree() {
        // Arrange
        ResourceVault vault = new ResourceVault();
        vault.setLevel(3);
        vault.updateBuilding();

        // Act
        LocalTime ACTUAL_TIME = vault.getConstructionTime();
        LocalTime EXPECTED_TIME = LocalTime.of(0, 1, 10);

        // Assert
        assertThat(ACTUAL_TIME, is(EXPECTED_TIME));
    }


}