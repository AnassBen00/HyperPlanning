package univ.tln.daos;

import univ.tln.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResponsableDao {
    public boolean checkResponsable(String username, String password){

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
