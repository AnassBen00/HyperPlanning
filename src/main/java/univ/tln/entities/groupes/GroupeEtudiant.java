package univ.tln.entities.groupes;

import lombok.Getter;
import lombok.Setter;

public class GroupeEtudiant extends Groupe{
    @Getter
    @Setter
    private String login;


    public GroupeEtudiant(int id,String login) {
        super(id);
        this.login = login;
    }
}
