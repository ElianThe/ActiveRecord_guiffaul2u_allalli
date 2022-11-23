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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList findAll() throws SQLException {
        ArrayList<Personne> personnes = new ArrayList<>();
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

    public static Personne findById(int id) throws SQLException {
        Personne pers = null;
        String SQLPrep = "SELECT * FROM Personne where id = ?;";
        DBConnection.getInstance();
        Connection connect = DBConnection.getConnect();
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        Personne p = null;
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            pers = new Personne(nom, prenom);
            pers.setId(id);
            System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
        }
        return pers;
    }

    public ArrayList<Personne> findByName(String name) throws SQLException {
        // On crée une liste de Personne vide
        ArrayList<Personne> lPersonnes = new ArrayList<>();
        // On récupère la connection à la base de données
        Connection connect = DBConnection.getConnect();
        // On crée une requête qui Select toutes les Personnes dont le nom vaut name
        String statement = "SELECT prenom, id FROM Personne WHERE name=?";
        PreparedStatement ps = connect.prepareStatement(statement);
        ps.setString(1, name);
        // On exécute la requête
        ps.execute();
        // On récupère les résultats
        ResultSet rs = ps.getResultSet();
        // On les parcourt
        while(rs.next()){
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            Personne pers = new Personne(name, prenom);
            pers.setId(id);
        }
        return lPersonnes;
    }

    public static void createTable () throws SQLException {
        //connection créée
        Connection connec = DBConnection.getConnect();
        //On crée une requete qui permet de créer la table personne
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement ps = connec.createStatement();
        ps.executeUpdate(createString);
        System.out.println("1) creation table Personne\n");
    }

    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnect();
        String deleteString = "DROP TABLE Personne;";
        Statement statement = connect.createStatement();
        statement.executeUpdate(deleteString);
        System.out.println("9) Supprime table Personne");
    }

    public void delete () throws SQLException {
        Connection connect = DBConnection.getConnect();
        String statement = "DELETE from Personne where id = ?";
        PreparedStatement ps = connect.prepareStatement(statement);
        ps.setInt(1, this.id);
        ps.execute();
        this.id = -1;
        System.out.println("5) Suppression personne id 1 (Spielberg)");
        System.out.println();
    }



}
