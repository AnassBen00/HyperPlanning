package univ.tln.entities.filieres;

import lombok.Getter;
import lombok.Setter;

public class Filiere {

    @Getter
    private int id;

    @Getter
    @Setter
    private String nomDuFiliere;

    public Filiere(int id, String nomDuFiliere) {
        this.id = id;
        this.nomDuFiliere = nomDuFiliere;
    }
}
