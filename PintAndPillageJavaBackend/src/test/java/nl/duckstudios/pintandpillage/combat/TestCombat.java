package nl.duckstudios.pintandpillage.combat;

import nl.duckstudios.pintandpillage.Exceptions.AttackingConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.VillageUnit;
import nl.duckstudios.pintandpillage.entity.production.*;
import nl.duckstudios.pintandpillage.model.AttackUnitData;
import nl.duckstudios.pintandpillage.model.AttackVillageData;
import nl.duckstudios.pintandpillage.model.UnitType;
import nl.duckstudios.pintandpillage.service.CombatService;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Tag("Combat")
public class TestCombat {

    @Mock
    Village defendingVillage = new Village();

    @Mock
    Village attackingVillage = new Village();

    @Spy
    CombatService combatService = new CombatService();

    @BeforeEach
    void setup(){
        this.combatService = new CombatService();
        this.attackingVillage = new Village();
        this.defendingVillage = new Village();
    }
    public void setDefendingVillage(int amountOfUnitsPerType) {

        defendingVillage.addUnit(new Spear(), amountOfUnitsPerType);
        defendingVillage.addUnit(new Axe(), amountOfUnitsPerType);
        defendingVillage.addUnit(new Jarl(), amountOfUnitsPerType);
        defendingVillage.addUnit(new Bow(), amountOfUnitsPerType);
        defendingVillage.addUnit(new DefenceShip(), amountOfUnitsPerType);
        defendingVillage.addUnit(new Shield(), amountOfUnitsPerType);

    }
    public void setupAttackingVillage(int amountOfUnitsPerType) {

        attackingVillage.addUnit(new Spear(), amountOfUnitsPerType);
        attackingVillage.addUnit(new Axe(), amountOfUnitsPerType);
        attackingVillage.addUnit(new Jarl(), amountOfUnitsPerType);
        attackingVillage.addUnit(new Bow(), amountOfUnitsPerType);
        attackingVillage.addUnit(new DefenceShip(), amountOfUnitsPerType);
        attackingVillage.addUnit(new Shield(), amountOfUnitsPerType);

    }

    @Test
    public void should_beAbleToAttack_whenEnoughResources() {
        setupAttackingVillage(100);

        Set<VillageUnit> attackedVillageUnits = new HashSet<>();
        attackedVillageUnits.add(new VillageUnit(new Spear(), 11));
        attackedVillageUnits.add(new VillageUnit(new Axe(), 11));
        attackedVillageUnits.add(new VillageUnit(new Jarl(), 11));
        attackedVillageUnits.add(new VillageUnit(new Bow(), 11));
        attackedVillageUnits.add(new VillageUnit(new DefenceShip(), 11));
        attackedVillageUnits.add(new VillageUnit(new Shield(), 11));

        List<VillageUnit> arr = new ArrayList<>(attackedVillageUnits);

        assertDoesNotThrow(() -> combatService.checkHasEnoughUnitsToAttack(arr, attackingVillage));

    }

    @Test
    public void should_notBeAbleToAttack_withoutAnyUnits() {
        setupAttackingVillage(0);

        List<VillageUnit> emptyUnitList = new ArrayList<>();

        assertDoesNotThrow(() -> combatService.checkHasEnoughUnitsToAttack(emptyUnitList, attackingVillage));
    }

    @Test
    public void should_notBeAbleToAttack_withoutEnoughResources() {
        setupAttackingVillage(10);

        Set<VillageUnit> attackedVillageUnits = new HashSet<>();
        attackedVillageUnits.add(new VillageUnit(new Spear(), 11));
        attackedVillageUnits.add(new VillageUnit(new Axe(), 11));
        attackedVillageUnits.add(new VillageUnit(new Jarl(), 11));

        List<VillageUnit> arr = new ArrayList<>(attackedVillageUnits);

        AttackingConditionsNotMetException thrown = assertThrows( AttackingConditionsNotMetException.class,
                () -> combatService.checkHasEnoughUnitsToAttack(arr, attackingVillage));

        assertThat(thrown.getMessage(), new StringContains("Not enough"));
    }

    /**
     * Village unit tests
     */
    @Test
    public void should_convertUnitsToAttackData_whenEnoughUnits() {

        List<AttackUnitData> attackUnitDataList = new ArrayList<>();
        attackUnitDataList.add(new AttackUnitData(UnitType.Spear, 10));
        attackUnitDataList.add(new AttackUnitData(UnitType.Axe, 10));
        attackUnitDataList.add(new AttackUnitData(UnitType.Bow, 10));

//        AttackVillageData attackVillageDataHelper = new AttackVillageDataHelper(attackUnitDataList, 10, 10);

        AttackVillageData avd = new AttackVillageData();
        avd.setToVillageId(1);
        avd.setUnits(attackUnitDataList);
        avd.setFromVillageId(2);

        List<VillageUnit> villageUnits = combatService.convertToVillageUnits(avd);

        assertThat(villageUnits.get(0).getUnit().getUnitName().toString(), new StringContains("Spear"));
    }

    @Test
    public void should_notConvertUnitsToAttackData_withoutEnoughUnits() {

        List<AttackUnitData> attackUnitDataList = new ArrayList<>();

        AttackVillageData avd = new AttackVillageData();
        avd.setToVillageId(1);
        avd.setUnits(attackUnitDataList);
        avd.setFromVillageId(2);


        AttackingConditionsNotMetException thrown = assertThrows( AttackingConditionsNotMetException.class,
                () ->combatService.convertToVillageUnits(avd));

        assertThat(thrown.getMessage(), is("To attack you need to send at least one unit"));

    }

    /**
     * Ship tests
     */
    @Test
    public void should_beAbleToSendShip_whenEnoughCapacity() {
        setDefendingVillage(10);

        TransportShip transportship = new TransportShip();
        transportship.setShipCapacity(100);
        defendingVillage.addUnit(transportship, 1);

        Set<VillageUnit> villageUnits = defendingVillage.getUnitsInVillage();
        List<VillageUnit> arr = new ArrayList<>(villageUnits);

        assertDoesNotThrow(() -> combatService.checkHasEnoughShipsToSendUnits(arr));
    }

    @Test
    public void should_notBeAbleToSendShip_withoutEnoughCapacity() {
        setDefendingVillage(10);

        TransportShip transportship = new TransportShip();
        transportship.setShipCapacity(0);
        defendingVillage.addUnit(transportship, 1);
        Set<VillageUnit> villageUnits = defendingVillage.getUnitsInVillage();
        List<VillageUnit> arr = new ArrayList<>(villageUnits);

        assertDoesNotThrow(() -> combatService.checkHasEnoughShipsToSendUnits(arr));
    }
}
