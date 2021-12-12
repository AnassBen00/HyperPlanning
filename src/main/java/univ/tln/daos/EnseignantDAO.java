package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.LoginController;

import java.sql.*;

public class EnseignantDAO {


    public boolean checkEnseignant(String username, String password){

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

    }



