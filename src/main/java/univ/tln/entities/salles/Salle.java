package univ.tln.entities.salles;

import lombok.Getter;
import lombok.Setter;
import univ.tln.entities.creneaux.Creneau;

import java.util.List;

public class Salle {

    @Getter
    private String id;
    @Getter
    @Setter
    private String nomDuSalle;
    @Getter
    @Setter
    private boolean videoProjecteur;


}
