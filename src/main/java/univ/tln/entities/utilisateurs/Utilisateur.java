package univ.tln.entities.utilisateurs;

import lombok.*;

@NoArgsConstructor
@Builder
public class Utilisateur {

    @Getter
    @Setter
    private String login;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String nom;
    @Getter
    @Setter
    private String prenom;
    @Getter
    @Setter
    private String email;

    public Utilisateur(String login, String password, String nom, String prenom, String email) {
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Utilisateur(String login, String nom, String prenom) {
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
    }
}
