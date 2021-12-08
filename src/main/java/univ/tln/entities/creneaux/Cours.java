package univ.tln.entities.creneaux;

import lombok.Getter;
import lombok.Setter;

public class Cours {

    @Getter
    private String id;
    @Getter
    @Setter
    private int duree;
    @Getter
    @Setter
    private String nature;
    @Getter
    @Setter
    private String nomduCours;

}
