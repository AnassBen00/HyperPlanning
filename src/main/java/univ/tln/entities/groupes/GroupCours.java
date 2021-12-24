package univ.tln.entities.groupes;

import lombok.Getter;
import lombok.Setter;

public class GroupCours extends Groupe{

    @Getter
    @Setter
    private String batiment;

    @Getter
    @Setter
    private int idFiliere;

    @Getter
    @Setter
    private int idCours;

    @Getter
    @Setter
    private float tauxH;


    public GroupCours(int id, String nomDuGroupe, String batiment, int idFiliere, int idCours, float tauxH) {
        super(id, nomDuGroupe);
        this.batiment = batiment;
        this.idFiliere = idFiliere;
        this.idCours = idCours;
        this.tauxH = tauxH;
    }
}