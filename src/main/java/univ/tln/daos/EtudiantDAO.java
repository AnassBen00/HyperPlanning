package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.entities.utilisateurs.Etudiant;
import univ.tln.entities.utilisateurs.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    public String getEtudiantNameByFiliereName(String nom) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {
            PreparedStatement statement = connection1.prepareStatement("select utilisateur.nom from etudiant join utilisateur on etudiant.login = utilisateur.login join filiere on etudiant.filiere = filiere.id where filiere.nom = ? ");
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();
            Utilisateur utilisateur = new Utilisateur();
            while (resultSet.next()) {
                utilisateur.setNom(resultSet.getString("nom"));
            }
            return utilisateur.getNom();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

    }

    public List<Etudiant> findall() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        List<Etudiant> etudiants = new ArrayList<Etudiant>();
        try {
            String queryString = "SELECT * from ETUDIANT join UTILISATEUR  ON ETUDIANT.login = UTILISATEUR.login ";
            PreparedStatement statement = connection1.prepareStatement(queryString);
            ResultSet resultset = statement.executeQuery();


            List<ResultSet> resultsetList = new ArrayList<ResultSet>();
            while(resultset.next()) {
                //System.out.println("id" + resultset.getString("id") + ",Nom" + resultset.getString("nom") +
                // ",Prenom" + resultset.getString("prenom") + ",Email" + resultset.getString("prenom"));

                System.out.println(resultset.getString("NOM"));
                System.out.println(resultset.getString("PRENOM"));
                Etudiant etudiant = new Etudiant("", "", resultset.getString("NOM"), resultset.getString("PRENOM"),"", "");
                etudiants.add(etudiant);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return etudiants;
    }

    public void removeEtudiant(String id) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        try {
            String queryString = "delete from etudiant where id = ? ";
            PreparedStatement statement = connection1.prepareStatement(queryString);
            statement.setString(1, id);
            statement.executeUpdate();
            System.out.println("Data deleted Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
