import activeRecord.DBConnection;
import activeRecord.Film;
import activeRecord.Personne;
import activeRecord.RealisateurAbsentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilm {

    @BeforeEach
    /**
     * Methode qui permet de créer la table activeRecord.Film et de la peupler avant l'exécution de chaque méthode de test
     */
    public void initTable() throws SQLException {
        // Récupération de la connexion
        DBConnection connection = DBConnection.getInstance();
        // Utilisation des méthodes createTable pour crée les tables activeRecord.Personne et activeRecord.Film
        Personne.createTable();
        Statement st = connection.getConnection().createStatement();
        st.executeUpdate("INSERT INTO `Personne` (`id`, `nom`, `prenom`) VALUES\n" +
                "(1, 'Spielberg', 'Steven'),\n" +
                "(2, 'Scott', 'Ridley'),\n" +
                "(3, 'Kubrick', 'Stanley'),\n" +
                "(4, 'Fincher', 'David');");

        Film.createTable();
        st.executeUpdate("INSERT INTO `Film` (`id`, `titre`, `id_rea`) VALUES\n" +
                "(1, 'Arche perdue', 1),\n" +
                "(2, 'Alien', 2),\n" +
                "(3, 'Temple Maudit', 1),\n" +
                "(4, 'Blade Runner', 2),\n" +
                "(5, 'Alien3', 4),\n" +
                "(6, 'Fight Club', 4),\n" +
                "(7, 'Orange Mecanique', 3);");

    }

    @AfterEach
    public void afterTest() throws SQLException {
        // Utilisation de la méthode statique deleteTable
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    /**
     * Test de la méthode findyById de la classe activeRecord.Film avec un id existant dans la base
     */
    public void testFindById_Ok() throws SQLException {
        // Methode testee
        Film film = Film.findById(6);

        // Verifications
        assertEquals(6, film.getId(), "L'id du film devrait etre 6.");
        assertEquals("Fight Club", film.getTitre(), "Le titre du film devrait etre Fight Club.");
        assertEquals(4, film.getId_rea(), "L'id du realisateur devrait etre 4");
    }

    @Test
    /**
     * Test de la méthode findyById de la classe activeRecord.Film avec un id inexistant dans la base
     */
    public void testFindById_KO() throws SQLException {
        // Methode testee
        Film film = Film.findById(0);

        // Verifications
        assertEquals(null, film);
    }

    @Test
    /**
     * Test de la méthode getRealisateur
     */
    public void testGetRealisateur() throws SQLException{
        // Preparation des données
        Film film = Film.findById(1);

        // Méthode testée
        Personne real = film.getRealisateur();

        // Vérifications
        assertEquals("Steven", real.getPrenom());
        assertEquals("Spielberg", real.getNom());
        assertEquals(1,real.getId());
    }

    @Test
    public void testdelete() throws SQLException, RealisateurAbsentException {
        //preparation des donnees
        Film film = new Film("Test", Personne.findById(1));
        film.save();
        //methode testee
        film.delete();

        //verification des resultats
        assertEquals(null,Film.findById(8));
    }

    @Test
    public void testSaveNew() throws SQLException, RealisateurAbsentException {
        //preparation des donnees
        Personne pers = new Personne("Nom","Prenom");
        pers.save();
        Film film = new Film("Test", pers);

        //methode testee
        film.save();

        //verification des resultats
        assertEquals(film, Film.findById(8));
    }

    @Test
    public void testSaveUpdate () throws SQLException, RealisateurAbsentException {
        //preparation des donnees
        Film film = Film.findById(1);
        film.setTitre("Bushi");

        //methode testee
        film.save();

        //verification
        assertEquals("Bushi", Film.findById(1).getTitre(), "Le titre devrait etre Bushi");

    }


}
