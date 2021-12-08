package univ.tln.entities.utilisateurs;

import lombok.Getter;

public class Responsable extends Utilisateur{

    @Getter
    private String id;

    public Responsable(String login, String password, String nom, String prenom, String email, String id) {
        super(login, password, nom, prenom, email);
        this.id = id;
    }

}
