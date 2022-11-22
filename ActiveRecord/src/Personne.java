import java.sql.*;
import java.util.ArrayList;

public class Personne {
    private int id;

    private String nom;

    private String prenom;

    public Personne(String name, String firstName){
        this.id = -1;
        this.nom = name;
        this.prenom = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList findAll() throws SQLException {
        ArrayList<Personne> personnes = new ArrayList<>();
        DBConnection db = DBConnection.getInstance();
        Connection connect = DBConnection.getConnect();

        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        Personne p = null;
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            p = new Personne(nom, prenom);
            p.setId(id);
            personnes.add(p);
            System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
        }
        return personnes;
    }

    public static Personne findById() throws SQLException {
        Personne pers = null;
        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        Personne p = null;
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            pers = new Personne(nom, prenom);
            pers.setId(id);
            System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
        }
        return pers;
    }



}
