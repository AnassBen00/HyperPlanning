package univ.tln.entities.utilisateurs;

import lombok.Getter;

public class Responsable extends Utilisateur{

    public Responsable(String login, String password, String nom, String prenom, String email, String login1) {
        super(login, password, nom, prenom, email);
    }
}
