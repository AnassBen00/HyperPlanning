package univ.tln.entities.creneaux;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
public class Creneau {

    @Getter
    @Setter
    private Date dateDebut;

    @Getter
    @Setter
    private Date dateFin;

    @Getter
    @Setter
    private int idGroupe;

    @Getter
    @Setter
    private int idCours;

    @Getter
    @Setter
    private int idSalle;


    public Creneau(Date dateDebut, Date dateFin, int idGroupe, int idCours, int idSalle) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idGroupe = idGroupe;
        this.idCours = idCours;
        this.idSalle = idSalle;
    }
}
