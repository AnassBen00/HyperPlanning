package univ.tln.entities.creneaux;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Creneau {

    @Getter
    private String id;
    @Getter
    @Setter
    private Date dateDebut;
    @Getter
    @Setter
    private Date dateFin;

    @Getter
    @Setter
    private String idGroupe;

    @Getter
    @Setter
    private String idCours;

    @Getter
    @Setter
    private String idSalle;


    public Creneau(String id, Date dateDebut, Date dateFin, String idGroupe, String idCours, String idSalle) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idGroupe = idGroupe;
        this.idCours = idCours;
        this.idSalle = idSalle;
    }
}
