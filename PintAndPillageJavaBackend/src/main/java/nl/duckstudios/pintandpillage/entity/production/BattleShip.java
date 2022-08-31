package nl.duckstudios.pintandpillage.entity.production;

import lombok.Getter;
import lombok.Setter;
import nl.duckstudios.pintandpillage.model.ResearchType;
import nl.duckstudios.pintandpillage.model.ResourceType;
import nl.duckstudios.pintandpillage.model.UnitType;

import javax.persistence.Entity;
import java.time.LocalTime;
import java.util.HashMap;

@Entity
public class BattleShip extends Unit implements ShipUnit {

    @Setter
    @Getter
    public ResearchType researchRequired = ResearchType.BattleShip;
    @Setter
    @Getter
    private long baseSecondsToProduce = 10;
    @Setter
    @Getter
    private UnitType unitName = UnitType.BattleShip;
    @Setter
    @Getter
    private int attack = 50;
    @Setter
    @Getter
    private int defence = 0;
    @Setter
    @Getter
    private int health = 10;
    @Setter
    @Getter
    private int speed = 50;
    @Setter
    @Getter
    private int plunderAmount = 500;
    @Setter
    private int shipCapacity = 50;
    @Setter
    @Getter
    private String description = "A viking longship that can carry many raiders";
    @Setter
    @Getter
    private int populationRequiredPerUnit = 3;


    public BattleShip() {
        LocalTime localTime = LocalTime.of(0, 0, 0);
        localTime = localTime.plusSeconds(this.baseSecondsToProduce);
        super.setBaseTimeToProduce(localTime);

        super.resourcesRequiredToProduce = new HashMap<>() {
            {
                put(ResourceType.Wood.name(), 500);
                put(ResourceType.Stone.name(), 200);
                put(ResourceType.Beer.name(), 100);
            }
        };
    }

    @Override
    public int getShipCapacity() {
        return this.shipCapacity;
    }
}
