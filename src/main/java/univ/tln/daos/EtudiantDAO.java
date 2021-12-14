package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.entities.utilisateurs.Etudiant;
import univ.tln.entities.utilisateurs.Utilisateur;

import java.sql.*;
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
    public boolean checkEtudiant(String username, String password){

        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        /*String prof ="PROF";
        String etud="ETU";
        String res="RES";*/

        String verifylogin=  "SELECT  count(1) from UTILISATEUR where LOGIN= '"+username+"' AND PASSWORD = HASH('SHA256','"+password+"',1000)";

        //LoginController.user1= String.valueOf(usernametxt.getText());*
        //System.out.println(getUsernametxt()+"wtffffffffff");

        try {
            Statement statement = connection1.createStatement();
            Statement statement2 = connection1.createStatement();
            ResultSet queryResult = statement.executeQuery(verifylogin);
            //ResultSet roleresult = statement2.executeQuery(getrole);
            //roleresult.next();
            //String R = roleresult.getString("ROLE");

            while ((queryResult.next())){
                if( queryResult.getInt(1)==1){

                    /*if(R.trim().equals(prof)) loginmessage.setText("welcome profesor");
                    if(R.trim().equals(etud)) loginmessage.setText("welcome student");
                    if(R.trim().equals(res)) loginmessage.setText("welcome manager");*/
                    return true;

                }else return false;//loginmessage.setText("invalid try again");
            }
        }
        catch (
                SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
