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
}
