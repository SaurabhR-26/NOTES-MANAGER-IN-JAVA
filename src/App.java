import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.jar.Attributes.Name;

public class App {
    public static void main(String[] args) throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "saur");

        Statement st = con.createStatement();

        // st.executeQuery("CREATE TABLE STUD_NOTES(UNAME VARCHAR(20),PASS VARCHAR(20),
        // NAME VARCHAR(30) , PRN INT , NOTES VARCHAR(100))");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String uname = "", pass = "";

        // st.executeQuery("truncate table stud_notes");

        while (true) {
            System.out.print("Username :");
            uname = br.readLine();

            if (uname.equals("stop")) {
                System.out.println("Thank you for using the system !");
                break;
            }

            System.out.print("Password :");
            pass = br.readLine();

            ResultSet rs = st.executeQuery("SELECT * FROM STUD_NOTES");

            boolean flag = false;

            if (rs != null) {
                while (rs.next()) {
                    String username = rs.getString("uname");
                    String password = rs.getString("pass");

                    if (username.equals(uname) && password.equals(pass)) {
                        flag = true;
                        break;
                    }
                }
            }

            if (flag == true) {
                int prn = rs.getInt("PRN");
                String nm = rs.getString("name");
                String notes = rs.getString("notes");
                System.out.println("DATA" + "\nPRN :" + prn + "\nName :" + nm + "\nNOTES :" + notes);
                System.out.println("Do you want to change your notes ?");
                String tmp = br.readLine();
                if (tmp.equals("yes")) {
                    System.out.print("NEW NOTES :");
                    String newnotes = br.readLine();
                    st.executeQuery("UPDATE STUD_NOTES SET NOTES =\'" + newnotes + "\' WHERE UNAME =\'" + uname + "\'");
                    System.out.println("Record updated successfully");
                }
                flag = false;
            } else {
                String un = uname;
                String ps = pass;
                System.out.print("Enter name :");
                String n = br.readLine();

                int pn = 0;
                try {
                    System.out.print("Enter PRN :");
                    pn = Integer.parseInt(br.readLine());
                } catch (Exception e) {
                }
                System.out.print("\nNotes :");
                String nnotes = br.readLine();
                st.executeQuery(
                        "INSERT INTO STUD_NOTES VALUES(\'" + un + "\',\'" + ps + "\', \'" + n + "\'," + pn + ",\'"
                                + nnotes + "\')");
                System.out.println("Record added successfully !!!  new user created..");
            }

        }

        System.out.println("Query executed successfully !");
        st.close();
        con.close();
    }
}
