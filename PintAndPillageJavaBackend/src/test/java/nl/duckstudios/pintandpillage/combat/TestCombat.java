package nl.duckstudios.pintandpillage.combat;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.duckstudios.pintandpillage.Exceptions.AttackingConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.VillageUnit;
import nl.duckstudios.pintandpillage.entity.production.*;
import nl.duckstudios.pintandpillage.model.AttackUnitData;
import nl.duckstudios.pintandpillage.model.AttackVillageData;
import nl.duckstudios.pintandpillage.model.UnitType;
import nl.duckstudios.pintandpillage.service.CombatService;
import nl.duckstudios.pintandpillage.testHelpers.AttackVillageDataHelper;
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
    public void setupAttackingVillage(int amountOfUnitsPerType) {

        attackingVillage.addUnit(new Spear(), amountOfUnitsPerType);
        attackingVillage.addUnit(new Axe(), amountOfUnitsPerType);
        attackingVillage.addUnit(new Jarl(), amountOfUnitsPerType);
        attackingVillage.addUnit(new Bow(), amountOfUnitsPerType);
        attackingVillage.addUnit(new DefenceShip(), amountOfUnitsPerType);
        attackingVillage.addUnit(new Shield(), amountOfUnitsPerType);
        attackingVillage.addUnit(new TransportShip(), amountOfUnitsPerType);
        attackingVillage.addUnit(new BattleShip(), amountOfUnitsPerType);

    }

    @Test
    public void hasEnoughUnitsToAttack() {
        setupAttackingVillage(100);

        Set<VillageUnit> attackedVillageUnits = new HashSet<>();
        attackedVillageUnits.add(new VillageUnit(new Spear(), 11));
        attackedVillageUnits.add(new VillageUnit(new Axe(), 11));
        attackedVillageUnits.add(new VillageUnit(new Jarl(), 11));
        attackedVillageUnits.add(new VillageUnit(new Bow(), 11));
        attackedVillageUnits.add(new VillageUnit(new DefenceShip(), 11));
        attackedVillageUnits.add(new VillageUnit(new Shield(), 11));
        attackedVillageUnits.add(new VillageUnit(new TransportShip(), 11));
        attackedVillageUnits.add(new VillageUnit(new BattleShip(), 11));

        List<VillageUnit> arr = new ArrayList<>(attackedVillageUnits);

        assertDoesNotThrow(() -> combatService.checkHasEnoughUnitsToAttack(arr, attackingVillage));

    }

    // TODO: Naming convention
    @Test
    public void test_attackVillageWithNoUnitsWithoutHavingAnyUnits() {
        setupAttackingVillage(0);

        List<VillageUnit> emptyUnitList = new ArrayList<>();

        assertDoesNotThrow(() -> combatService.checkHasEnoughUnitsToAttack(emptyUnitList, attackingVillage));
    }

    // TODO: Naming convention
    @Test
    public void throwErrorBecauseNotEnoughUnits() {
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

    @Test
    public void test_convertToVillageUnits() {

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

    // TODO: attackingVillage.getUnitInVillage(attackingUnit.getUnit().getUnitName()).setAmount(unitInVillage.getAmount() - attackingUnit.getAmount());
    // FF een spy d'r op knallen om te zien of 't werkt.
}
