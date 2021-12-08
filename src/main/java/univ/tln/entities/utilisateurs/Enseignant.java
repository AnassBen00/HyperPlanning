package univ.tln.entities.utilisateurs;

import lombok.Getter;
import univ.tln.entities.creneaux.Creneau;

import java.util.List;

public class Enseignant extends Utilisateur{
    @Getter
    private String id;

    private List<Creneau> creneaux;

    public Enseignant(String login, String password, String nom, String prenom, String email, String id) {
        super(login, password, nom, prenom, email);
        this.id = id;
    }

}
