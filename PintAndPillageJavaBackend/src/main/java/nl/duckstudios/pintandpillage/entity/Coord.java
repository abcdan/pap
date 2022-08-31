package nl.duckstudios.pintandpillage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString
@Entity
public class Coord {

    @Getter @Setter
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    @Getter @Setter
    private int x;
    @Getter @Setter
    private int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coord() {
    }
}
