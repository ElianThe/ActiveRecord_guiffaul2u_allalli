package activeRecord;

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

    public static ArrayList findAll() throws SQLException {
        ArrayList<Personne> personnes = new ArrayList<>();
        Connection connect = DBConnection.getConnection();

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
        Connection connect = DBConnection.getConnection();
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

    public static ArrayList<Personne> findByName(String name) throws SQLException {
        // On crée une liste de activeRecord.Personne vide
        ArrayList<Personne> lPersonnes = new ArrayList<>();
        // On récupère la connection à la base de données
        Connection connect = DBConnection.getConnection();
        // On crée une requête qui Select toutes les Personnes dont le nom vaut name
        String statement = "SELECT prenom, id FROM Personne WHERE nom=?";
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
            lPersonnes.add(pers);
        }
        return lPersonnes;
    }

    public static void createTable () throws SQLException {
        //connection créée
        DBConnection db = DBConnection.getInstance();
        Connection connec = DBConnection.getConnection();
        //On crée une requete qui permet de créer la table personne
        String createString = "CREATE TABLE IF NOT EXISTS Personne ( " + "ID INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL);";
        Statement ps = connec.createStatement();
        ps.executeUpdate(createString);
        System.out.println("1) creation table activeRecord.Personne\n");
    }

    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String deleteString = "DROP TABLE Personne;";
        Statement statement = connect.createStatement();
        statement.executeUpdate(deleteString);
        System.out.println("9) Supprime table activeRecord.Personne");
    }

    public void delete () throws SQLException {
        Connection connect = DBConnection.getConnection();
        String statement = "DELETE from Personne where id = ?";
        PreparedStatement ps = connect.prepareStatement(statement);
        ps.setInt(1, this.id);
        ps.execute();
        this.id = -1;
        System.out.println("5) Suppression personne id 1 (Spielberg)");
        System.out.println();
    }

    public void save() throws SQLException {
        if (id == -1){
            saveNew();
        } else {
            update();
        }
    }

    private void update () throws SQLException {
        Connection connect = DBConnection.getConnection();
        String statement = "update Personne set nom = ?, prenom = ? where id = ?;";
        PreparedStatement ps = connect.prepareStatement(statement);
        ps.setString(1, this.nom);
        ps.setString(2, this.prenom);
        ps.setInt(3, this.id);
        ps.executeUpdate();
        System.out.println("7) Effectue modification activeRecord.Personne id 2");
        System.out.println();
    }

    private void saveNew() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String statement = "INSERT INTO Personne(nom, prenom) VALUES (?, ?);";
        PreparedStatement ps = connect.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.nom);
        ps.setString(2, this.prenom);
        ps.executeUpdate();
        int autoInc = -1;
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            autoInc = rs.getInt(1);
        }
        this.id = autoInc;
        System.out.print("  ->  id utilise lors de l'ajout : ");
        System.out.println(autoInc);
        System.out.println();
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getId() {
        return id;
    }
}
