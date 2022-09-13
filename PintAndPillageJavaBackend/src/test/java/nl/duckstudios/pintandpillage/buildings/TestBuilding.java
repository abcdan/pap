package nl.duckstudios.pintandpillage.buildings;

import nl.duckstudios.pintandpillage.Exceptions.BuildingConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Coord;
import nl.duckstudios.pintandpillage.entity.buildings.House;
import nl.duckstudios.pintandpillage.helper.ResourceManager;
import nl.duckstudios.pintandpillage.testHelpers.ResourceHelper;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.model.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Building")
public class TestBuilding {

    @Spy
    private Village villageSpy;

    @Mock
    private Village villageMock;
    private MockBuilding buildingUnderTesting;
    private final ResourceHelper resourceHelper = new ResourceHelper();

    @Spy
    private ResourceManager spyResourceManger = new ResourceManager();

    @BeforeEach
    void initMockBuilding(){
        this.buildingUnderTesting = new MockBuilding();
    }

    void setupVillageStub(){
        this.buildingUnderTesting.setResourcesRequiredLevelUp(this.resourceHelper.generateResource(ResourceType.Stone, 1));
        this.buildingUnderTesting.setVillage(villageMock);
        this.buildingUnderTesting.setResourceManager(spyResourceManger);

        when(this.villageMock.getVillageResources()).thenReturn(this.resourceHelper.generateResource(ResourceType.Stone, 10));
        when(this.villageMock.hasEnoughPopulation(anyInt())).thenReturn(true);

    }


    @Test
    void should_setConstructionFinishedTime_when_levelUpIsCalled(){
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime expectedFinishTime = timeNow.plusSeconds(10);

        this.setupVillageStub();
        this.buildingUnderTesting.setConstructionTimeSeconds(10);


        this.buildingUnderTesting.levelUp();

        LocalDateTime actualFinishTime = this.buildingUnderTesting.getLevelupFinishedTime();

        assertThat(actualFinishTime.withNano(0), is(expectedFinishTime.withNano(0)));

    }

    @Test
    void should_underConstructionToTrue_when_levelUpIsCalled(){
        this.setupVillageStub();
        boolean expectedUnderConstruction = true;

        this.buildingUnderTesting.levelUp();
        boolean actualUnderConstruction = this.buildingUnderTesting.isUnderConstruction();

        assertThat(actualUnderConstruction, is(expectedUnderConstruction));
    }

    @Test
    void should_throwNotEnoughResourcesException_when_notEnoughResourcesInVillage(){
        this.buildingUnderTesting.setResourcesRequiredLevelUp(this.resourceHelper.generateResource(ResourceType.Stone, 100));
        this.buildingUnderTesting.setVillage(villageMock);

        when(this.villageMock.getVillageResources()).thenReturn(this.resourceHelper.generateResource(ResourceType.Stone, 10));

        BuildingConditionsNotMetException thrown = assertThrows( BuildingConditionsNotMetException.class,
                () -> this.buildingUnderTesting.levelUp());

        assertThat(thrown.getMessage(), is("Not enough resources available"));
    }

    @Test
    void should_throwNotEnoughPopulationException_when_notEnoughPopulationInVillage(){
        this.buildingUnderTesting.setResourcesRequiredLevelUp(this.resourceHelper.generateResource(ResourceType.Stone, 1));
        this.buildingUnderTesting.setVillage(villageMock);

        when(this.villageMock.getVillageResources()).thenReturn(this.resourceHelper.generateResource(ResourceType.Stone, 10));
        when(this.villageMock.hasEnoughPopulation(anyInt())).thenReturn(false);


        BuildingConditionsNotMetException thrown = assertThrows( BuildingConditionsNotMetException.class,
                () -> this.buildingUnderTesting.levelUp());

        assertThat(thrown.getMessage(), is("Not enough population available"));
    }

    @Test
    void should_callSubtractResourcesOnlyOnce_when_levelUpIsCalled(){
        this.setupVillageStub();
        this.buildingUnderTesting.levelUp();

        Mockito.verify(spyResourceManger, times(1)).subtractResources(any(), any());
    }


    // Waarom: Als een house geplaatst wordt, moet er gekeken worden of alles geupdate is.
    @Test
    void should_callUpdateVillageState_when_createBuildingIsCalled() {
        House house = new House();
        house.setName("House");
        house.setLevel(20);
        house.setVillage(villageSpy);
        house.setPosition(new Coord(2, 5));
        house.setUnderConstruction(false);
        this.villageSpy.createBuilding(house);

        Mockito.verify(villageSpy, times(1)).updateVillageState();
    }

//    // TODO: Change name of variable, it's not using the required format.
//    // Waarom: Volgens requirement 1 moeten gebouwen meer kosten om te upgraden naarmate ze hoger level zijn.
//    // TODO: Werkt nie
//    @Test
//    void should_costMoreWhenHigherLevel() {
//        MockBuilding building1 = new MockBuilding();
//        building1.setLevel(1);
//        Map<String, Integer> resourcesBuilding1 = building1.getResourcesRequiredLevelUp();
//
//        MockBuilding building2 = new MockBuilding();
//        building2.setLevel(8);
//        Map<String, Integer> resourcesBuilding2 = building2.getResourcesRequiredLevelUp();
//
//        assertThat(resourcesBuilding1.get("Stone"), is(8));
//        assertThat(resourcesBuilding2.get("Stone"), is(8));
//        assertThat(resourcesBuilding1.get("Wood"), is(10));
//        assertThat(resourcesBuilding2.get("Wood"), is(10));
//
//
//    }
}