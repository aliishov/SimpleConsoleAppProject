package model;

public enum Genre
{
    Novel("Novel", 1),
    Mystery("Mystery",2),
    Thriller("Thriller",3),
    Fantasy("Fantasy",4),
    ScienceFiction("ScienceFiction",5),
    Horror("Horror",6),
    HistoricalNovel("HistoricalNovel",7),
    PopularScience("PopularScience",8),
    Classics("Classics",9),
    Poetry("Poetry",10),
    BiographiesAndMemoirs("BiographiesAndMemoirs",11),
    PsychologicalNovel("PsychologicalNovel",12);

    //-------------------------------------------------
    private final int id;
    private final String name;

    //-------------------------------------------------
    Genre(String name, int id)
    {
        this.name = name;
        this.id = id;
    }
    //-------------------------------------------------
    public static Genre getGenreById(int id)
    {
        for (Genre genre : Genre.values())
            if (genre.id == id)
                return genre;
        return null;
    }
    //-------------------------------------------------
    @Override
    public String toString()
    {
        return name;
    }
}
