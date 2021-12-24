package univ.tln.entities.utilisateurs;

import lombok.Getter;
import lombok.Setter;

public class Etudiant extends Utilisateur{

    @Getter
    @Setter
    private String nvxEtude;

    @Getter
    @Setter
    private String promo;

    @Getter
    @Setter
    private int idFiliere;

    public Etudiant(String login, String password, String nom, String prenom, String email, String nvxEtude, String promo, int idFiliere) {
        super(login, password, nom, prenom, email);
        this.nvxEtude = nvxEtude;
        this.promo = promo;
        this.idFiliere = idFiliere;
    }
}
