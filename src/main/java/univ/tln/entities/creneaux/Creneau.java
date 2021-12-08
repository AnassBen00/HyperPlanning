package univ.tln.entities.creneaux;

import lombok.Getter;
import lombok.Setter;

public class Creneau {

    @Getter
    private String id;
    @Getter
    @Setter
    private String dateDebut;
    @Getter
    @Setter
    private String dateFin;

    @Getter
    @Setter
    private String idGroupe;

    @Getter
    @Setter
    private String idCours;

    @Getter
    @Setter
    private String idSalle;


}
