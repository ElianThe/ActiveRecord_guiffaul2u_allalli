import java.sql.*;

public class Film {
    private String titre;

    private int id, id_real;

    public Film(String titre, Personne real){
        this.titre = titre;
        this.id_real = real.getId();
        this.id = -1;
    }

    public Film(String titre, int id, int id_real){
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

    public static Film findById(int id) throws SQLException {
        Connection connect = DBConnection.getConnect();
    String statement = "SELECT TITRE, ID_REA from film where ID = ?";
        PreparedStatement ps = connect.prepareStatement(statement);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        Film film = null;
        while (rs.next()){
            String titre = rs.getString(1);
            int id_rea = rs.getInt(2);
            film = new Film(titre, id, id_rea);
        }
        return film;
    }

    public Personne getRealiateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public int getId_real() {
        return id_real;
    }

    public static void createTable () throws SQLException {
        //connection créée
        Connection connec = DBConnection.getConnect();
        //On crée une requete qui permet de créer la table film
        String createString = "CREATE TABLE `Film` (" +
                "  `id` int(11) NOT NULL," +
                "  `titre` varchar(40) NOT NULL," +
                "  `id_rea` int(11) DEFAULT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
        Statement ps = connec.createStatement();
        ps.executeUpdate(createString);
        ps.executeUpdate("ALTER TABLE `Film`" +
                "  ADD PRIMARY KEY (`id`)," +
                "  ADD KEY `id_rea` (`id_rea`);");
        ps.executeUpdate("ALTER TABLE `Film`" +
                "  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;");
        ps.executeUpdate("ALTER TABLE `Film`" +
                "  ADD CONSTRAINT `film_ibfk_1` FOREIGN KEY (`id_rea`) REFERENCES `Personne` (`id`);");
    }

    public static void deleteTable() throws SQLException {
        // On récupère la connexion à la base
        Connection connect = DBConnection.getConnect();
        // On lance une requête de suppression de la table Film
        Statement statement = connect.createStatement();
        statement.executeUpdate("DROP TABLE Film");
    }
}
