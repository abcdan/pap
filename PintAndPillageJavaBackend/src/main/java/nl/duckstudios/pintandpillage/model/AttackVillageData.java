package nl.duckstudios.pintandpillage.model;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

// TODO: Setters toegevoegd zodat ik dit object kan aanmaken in de test. Dit moet anders
public class AttackVillageData {

    @Setter
    public List<AttackUnitData> units;

    @Setter
    public long fromVillageId;

    @Setter
    public long toVillageId;
}
