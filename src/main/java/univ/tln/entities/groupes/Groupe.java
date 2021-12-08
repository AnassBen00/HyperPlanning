package univ.tln.entities.groupes;

import lombok.Getter;
import lombok.Setter;
import univ.tln.entities.creneaux.Creneau;

import java.util.List;

public class Groupe {

    @Getter
    private String id;
    @Getter
    @Setter
    private String nomDuGroupe;
    @Getter
    @Setter
    private List<Creneau> creneaux;

    private String idFiliere;

}
