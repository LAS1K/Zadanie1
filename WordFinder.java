import java.sql.*;
import java.sql.Connection;

public class WordFinder {

    private String paths = "";

    public String findWordPaths(String word){

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "user", "password");
            Statement st = con.createStatement();

            String query = "USE database";
            st.execute(query);

            query = "SELECT sciezka FROM tabela WHERE ? LIKE CONCAT('%' , slowo , '%') ORDER BY INSTR(?, slowo)";

            PreparedStatement prep = con.prepareStatement(query);
            prep.setString(1, word);
            prep.setString(2, word);
            ResultSet set = prep.executeQuery();
            if (!set.isBeforeFirst()) {
                System.out.println("ERROR: NO PATHS FOUND");
            }else {
                //Tworzenie stringa CSV z rezultatow
                while (set.next()) {
                    paths = paths + set.getString("sciezka") + ",";
                }
            }
            con.close();
            st.close();
            prep.close();
            set.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return paths;
    }
}
