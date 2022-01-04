package univ.tln.entities.utilisateurs;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Enseignant extends Utilisateur{

    public Enseignant(String login, String password, String nom, String prenom, String email) {
        super(login, password, nom, prenom, email);
    }
}
