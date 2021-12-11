package univ.tln.entities.utilisateurs;

import lombok.Getter;

public class Etudiant extends Utilisateur{

    @Getter
    private String id;

    public Etudiant(String login, String password, String nom, String prenom, String email, String id) {
        super(login, password, nom, prenom, email);
        this.id = id;
    }

}
