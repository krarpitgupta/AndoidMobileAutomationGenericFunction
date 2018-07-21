package GenericFunctionsLibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBFunctions {
       
       public String serverip;
       public String port;
       public String dbName;
       public String Username;
       public String Password;
       Connection connection1;
       Statement statement1;
      public  ResultSet rs;
       
       public void connectdatabase()
       {
              try {  
               Class.forName("com.mysql.jdbc.Driver");  
               connection1 = DriverManager.getConnection("jdbc:mysql://"+serverip+":"+port+"/"+dbName, Username, Password);
               statement1 = connection1.createStatement();  
                     
                     //statement1.executeUpdate();
               
          } catch (Exception e) {  
             e.printStackTrace();  
           }  
              
       }

       public ResultSet Selectstatement(String query)
       {
              
              try {
                     
                     rs=statement1.executeQuery(query);
              
              } catch (SQLException e) {
                     
                     e.printStackTrace();
              }
              
              return rs;
              
       }
              

       public void Updatestatement(String query)
       {
              
              try {
              statement1.executeUpdate(query);
                     } 
              catch (SQLException e) 
              {
                     e.printStackTrace();
              }
              
       }
       
       
       public void InsertStatement(String query)
       {
              
              try {
                     statement1.executeUpdate(query);
                           } 
                     catch (SQLException e) 
                     {
                           e.printStackTrace();
                     }
              
       }
       
       public void DisconnectFromDataBase(){
    	   try {
			connection1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
       
}
       
       


