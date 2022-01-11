package univ.tln.entities.creneaux;

import lombok.Getter;
import lombok.Setter;

public class Cours {

    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    private String nature;
    @Setter
    @Getter
    private String nomduCours;
    @Setter
    @Getter
    private String login;


    public Cours(String nature, String nomduCours, String login) {
        this.nature = nature;
        this.nomduCours = nomduCours;
        this.login = login;
    }

}
