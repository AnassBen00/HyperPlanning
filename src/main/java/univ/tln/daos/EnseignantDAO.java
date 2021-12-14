package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.LoginController;

import java.sql.*;

public class EnseignantDAO {


    public boolean checkEnseignant(String username, String password){

        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();


        String verifylogin=  "SELECT  count(1) from UTILISATEUR join ENSEIGNANT on (UTILISATEUR.LOGIN = ENSEIGNANT.LOGIN) where ENSEIGNANT.LOGIN= '"+username+"' AND PASSWORD = HASH('SHA256','"+password+"',1000)";


        try {
            Statement statement = connection1.createStatement();
            Statement statement2 = connection1.createStatement();
            ResultSet queryResult = statement.executeQuery(verifylogin);


            while ((queryResult.next())){
                if( queryResult.getInt(1)==1){
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


    public void findall() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {
            String queryString = "SELECT * from ENSEIGNANT joint UTILISATEUR ON ENSEIGNANT.login = UTILISATEUR.login  ";
            PreparedStatement statement = connection1.prepareStatement(queryString);
            ResultSet resultset = statement.executeQuery();

            while(resultset.next()) {
                System.out.println("id" + resultset.getString("id") + ",Nom" + resultset.getString("nom") +
                        ",Prenom" + resultset.getString("prenom") + ",Email" + resultset.getString("prenom"));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeEnseignant(String id) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {
            String queryString = "delete from ENSEIGNANT where id = ? ";
            PreparedStatement statement = connection1.prepareStatement(queryString);
            statement.setString(1, id);
            statement.executeUpdate();
            System.out.println("Data deleted Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        }

    public String getEnseignantNameBylogin(String l) {
        String m = null;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {

            String queryString = "select nom from UTILISATEUR where login = ?";
            PreparedStatement statement = connection1.prepareStatement(queryString);
            statement.setString(1, l);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                m = resultSet.getString("NOM");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return m;
    }


    }



