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
    String nom;
    @Getter
    @Setter
    String nature;
    @Getter
    @Setter
    String nomGroupe;
    @Getter
    @Setter
    String nomBatiment;
    @Getter
    @Setter
    String nomSalle;
    @Getter
    @Setter
    String id_s;
    @Getter
    @Setter
    String id_g;

    public Absence(String date_d, String nomBatiment, String nomSalle ,String nomGroupe,String login) {
        this.login = login;
        this.date_d = date_d;
        this.nomGroupe = nomGroupe;
        this.nomBatiment = nomBatiment;
        this.nomSalle = nomSalle;
    }

    public Absence(String date_d, String nom, String nature,String id_s,String id_g,String login) {
        this.date_d=date_d;
        this.nom=nom;
        this.nature=nature;
        this.id_s=id_s;
        this.id_g=id_g;
        this.login=login;
    }
}
