package univ.tln.entities.utilisateurs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class Absence {
    @Getter
    @Setter
    String login;
    @Getter
    @Setter
    String date_d;
    @Getter
    @Setter
    String nomGroupe;
    @Getter
    @Setter
    String nomBatiment;
    @Getter
    @Setter
    String nomSalle;

    public Absence(String date_d, String nomBatiment, String nomSalle ,String nomGroupe,String login) {
        this.login = login;
        this.date_d = date_d;
        this.nomGroupe = nomGroupe;
        this.nomBatiment = nomBatiment;
        this.nomSalle = nomSalle;
    }
}
