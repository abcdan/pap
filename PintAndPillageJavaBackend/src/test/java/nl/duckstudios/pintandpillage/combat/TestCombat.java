package nl.duckstudios.pintandpillage.combat;

import nl.duckstudios.pintandpillage.Exceptions.AttackingConditionsNotMetException;
import nl.duckstudios.pintandpillage.Exceptions.BuildingConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.VillageUnit;
import nl.duckstudios.pintandpillage.entity.production.Axe;
import nl.duckstudios.pintandpillage.entity.production.Jarl;
import nl.duckstudios.pintandpillage.entity.production.Spear;
import nl.duckstudios.pintandpillage.service.CombatService;
import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Set<VillageUnit> attackingVillageUnits = new HashSet<>();
        attackingVillageUnits.add(new VillageUnit(new Spear(), amountOfUnitsPerType));
        attackingVillageUnits.add(new VillageUnit(new Axe(), amountOfUnitsPerType));
        attackingVillageUnits.add(new VillageUnit(new Jarl(), amountOfUnitsPerType));
//        when(attackingVillage.getUnitsInVillage()).thenReturn(attackingVillageUnits);
    }

    @Test
    public void hasEnoughUnitsToAttack() {
        setupAttackingVillage(1);

        Set<VillageUnit> enoughAttackingUnits = new HashSet<>();
        enoughAttackingUnits.add(new VillageUnit(new Spear(), 11));
        enoughAttackingUnits.add(new VillageUnit(new Axe(), 11));
        enoughAttackingUnits.add(new VillageUnit(new Jarl(), 11));

        List<VillageUnit> arr = new ArrayList<>(enoughAttackingUnits);

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
        setupAttackingVillage(12);

        Set<VillageUnit> notEnoughVillageUnits = new HashSet<>();
        notEnoughVillageUnits.add(new VillageUnit(new Spear(), 11));
        notEnoughVillageUnits.add(new VillageUnit(new Axe(), 11));
        notEnoughVillageUnits.add(new VillageUnit(new Jarl(), 11));

        List<VillageUnit> arr = new ArrayList<>(notEnoughVillageUnits);

        AttackingConditionsNotMetException thrown = assertThrows( AttackingConditionsNotMetException.class,
                () -> combatService.checkHasEnoughUnitsToAttack(arr, attackingVillage));

        assertThat(thrown.getMessage(), new StringContains("Not enough"));
    }

    // TODO: attackingVillage.getUnitInVillage(attackingUnit.getUnit().getUnitName()).setAmount(unitInVillage.getAmount() - attackingUnit.getAmount());
    // FF een spy d'r op knallen om te zien of 't werkt.
}
