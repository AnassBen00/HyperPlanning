package univ.tln.entities.salles;

import lombok.Getter;
import lombok.Setter;
import univ.tln.entities.creneaux.Creneau;

import java.util.List;

public class Salle {

    @Getter
    private int id;
    @Getter
    @Setter
    private String nomSalle;
    @Getter
    @Setter
    private String batiment;
    @Getter
    @Setter
    private boolean videoProjecteur;

    public Salle(int id, String nomSalle, String batiment, boolean videoProjecteur) {
        this.id = id;
        this.nomSalle = nomSalle;
        this.batiment = batiment;
        this.videoProjecteur = videoProjecteur;
    }
}
