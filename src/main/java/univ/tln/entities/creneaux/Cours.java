package univ.tln.entities.creneaux;

import lombok.Setter;

public class Cours {

    private String id;
    @Setter
    private int duree;
    @Setter
    private String nature;
    @Setter
    private String nomduCours;

    public Cours(String id, int duree, String nature, String nomduCours) {
        this.id = id;
        this.duree = duree;
        this.nature = nature;
        this.nomduCours = nomduCours;
    }

    public String getId() {
        return this.id;
    }

    public int getDuree() {
        return this.duree;
    }

    public String getNature() {
        return this.nature;
    }

    public String getNomduCours() {
        return this.nomduCours;
    }
}
