package nl.duckstudios.pintandpillage.combat;

import nl.duckstudios.pintandpillage.Exceptions.AttackingConditionsNotMetException;
import nl.duckstudios.pintandpillage.Exceptions.BuildingConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.VillageUnit;
import nl.duckstudios.pintandpillage.entity.production.*;
import nl.duckstudios.pintandpillage.model.UnitType;
import nl.duckstudios.pintandpillage.service.CombatService;
import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

    // TODO: attackingVillage.getUnitInVillage(attackingUnit.getUnit().getUnitName()).setAmount(unitInVillage.getAmount() - attackingUnit.getAmount());
    // FF een spy d'r op knallen om te zien of 't werkt.
}
