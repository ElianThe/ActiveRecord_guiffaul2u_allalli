package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Film {
    private String titre;

    private int id, id_rea;

    public Film(String titre, Personne real){
        this.titre = titre;
        this.id_rea = real.getId();
        this.id = -1;
    }

    public Film(String titre, int id, int id_rea){
        this.titre = titre;
        this.id = id;
        this.id_rea = id_rea;
    }

    public static Film findById(int id) throws SQLException {
        Connection connect = DBConnection.getConnection();
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

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public int getId_rea() {
        return id_rea;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_rea(int id_rea) {
        this.id_rea = id_rea;
    }

    public static void createTable () throws SQLException {
        //connection créée
        Connection connec = DBConnection.getConnection();
        //On crée une requete qui permet de créer la table film
        String createString = "CREATE TABLE IF NOT EXISTS `Film` (" +
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
                "  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;");
        ps.executeUpdate("ALTER TABLE `Film`" +
                "  ADD CONSTRAINT `film_ibfk_1` FOREIGN KEY (`id_rea`) REFERENCES `Personne` (`id`);");
    }

    public static void deleteTable() throws SQLException {
        // On récupère la connexion à la base
        Connection connect = DBConnection.getConnection();
        // On lance une requête de suppression de la table activeRecord.Film
        Statement statement = connect.createStatement();
        statement.executeUpdate("DROP TABLE Film");
    }

    public static ArrayList<Film> findByRealisateur(Personne p) throws SQLException {
        // On crée une liste de activeRecord.Film vide
        ArrayList<Film> listFilms = new ArrayList<>();
        // On récupère la connexion à la base
        Connection connect = DBConnection.getConnection();
        PreparedStatement ps = connect.prepareStatement("SELECT * FROM Film WHERE id_real = ?");
        ps.setInt(1, p.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Film film = new Film(rs.getString("titre"),rs.getInt("id"),rs.getInt("id_real"));
            listFilms.add(film);
        }
        return listFilms;
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if (id_rea == -1) throw new RealisateurAbsentException();
        else {
            if (id == -1) {
                saveNew();
            } else {
                update();
            }
        }
    }

    private void saveNew() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String statement = "INSERT INTO Film(titre, id_rea) VALUES (?, ?);";
        PreparedStatement ps = connect.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.titre);
        ps.setInt(2, this.id_rea);
        ps.executeUpdate();
        int autoInc = -1;
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            autoInc = rs.getInt(1);
        }
        this.id = autoInc;
    }

    private void update() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String statement = "update Film set titre = ?, id_rea = ? where id = ?;";
        PreparedStatement ps = connect.prepareStatement(statement);
        ps.setString(1, this.titre);
        ps.setInt(2, this.id_rea);
        ps.setInt(3, this.id);
        ps.executeUpdate();
    }

    public void delete () throws SQLException {
        Connection connect = DBConnection.getConnection();
        String statement = "DELETE from Film where id = ?";
        PreparedStatement ps = connect.prepareStatement(statement);
        ps.setInt(1, this.id);
        ps.execute();
        this.id = -1;
        System.out.println("5) Suppression personne id 1 (Spielberg)");
        System.out.println();
    }

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(this.id_rea);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && id_rea == film.id_rea && titre.equals(film.titre);
    }
}
