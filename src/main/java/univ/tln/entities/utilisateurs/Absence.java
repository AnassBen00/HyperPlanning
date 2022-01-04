package univ.tln.entities.utilisateurs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class Absence {
    @Getter
    @Setter
    String login;
    @Getter
    @Setter
    String date_d;

    public Absence(String login, String date_d) {
        this.login = login;
        this.date_d = date_d;
    }
}
