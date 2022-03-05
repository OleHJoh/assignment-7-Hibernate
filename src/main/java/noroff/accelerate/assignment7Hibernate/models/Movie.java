package noroff.accelerate.assignment7Hibernate.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String title;
    private String genre;
    private Date releaseYear;
    private String director;
    private URL picture;
    private URL trailer;
    //Relationships
    @ManyToOne
    private Franchise franchise;
    @ManyToMany
    @JoinTable(
            name = "movie_character",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "character_id")}
    )
    private Set<Character> characters;

    public Movie() {
    }

    public Movie(long id, String title, String genre, Date releaseYear, String director, URL picture, URL trailer) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
        this.picture = picture;
        this.trailer = trailer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }

    public URL getTrailer() {
        return trailer;
    }

    public void setTrailer(URL trailer) {
        this.trailer = trailer;
    }

    @JsonGetter("franchise")
    public Long getFranchise() {
        if (franchise == null)
            return null;
        return franchise.getId();
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    @JsonGetter("characters")
    public List<Long> getCharacters() {
        if (characters == null)
            return null;
        return characters.stream().map(c -> c.getId()).collect(Collectors.toList());
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }
}
