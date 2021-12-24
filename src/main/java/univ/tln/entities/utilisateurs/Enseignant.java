package univ.tln.entities.utilisateurs;

import lombok.Getter;
import univ.tln.entities.creneaux.Creneau;

import java.util.List;

public class Enseignant extends Utilisateur{

    public Enseignant(String login, String password, String nom, String prenom, String email, String login1) {
        super(login, password, nom, prenom, email);
    }
}
