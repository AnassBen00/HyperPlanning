package univ.tln.entities.groupes;

import lombok.Getter;
import lombok.Setter;


public class Groupe {

    @Getter
    private int id;

    @Getter
    @Setter
    private String nomDuGroupe;

    public Groupe(int id) {
        this.id = id;
    }

    public Groupe(int id, String nomDuGroupe) {
        this.id = id;
        this.nomDuGroupe = nomDuGroupe;
    }
}
