package univ.tln.entities.utilisateurs;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Responsable extends Utilisateur{

    public Responsable(String login, String password, String nom, String prenom, String email) {
        super(login, password, nom, prenom, email);
    }
}
