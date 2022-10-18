package nl.duckstudios.pintandpillage.village.testHelpers;

import lombok.Getter;
import lombok.Setter;
import nl.duckstudios.pintandpillage.model.AttackUnitData;
import nl.duckstudios.pintandpillage.model.AttackVillageData;

import java.util.List;


public class AttackVillageDataHelper extends AttackVillageData {

    public List<AttackUnitData> units;
    public long fromVillageId;
    public long toVillageId;
    public AttackVillageDataHelper(List<AttackUnitData> units, long fromVillageId, long toVillageId) {
        this.units = units;
        this.fromVillageId = fromVillageId;
        this.toVillageId = toVillageId;
    }
}
