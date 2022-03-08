package noroff.accelerate.assignment7Hibernate.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Size(min = 0, max = 40)
    private String fullName;
    private String alias;
    private String gender;
    private URL picture;
    //Relationships
    @ManyToMany(mappedBy = "characters")
    private Set<Movie> movies;

    public Character() {
    }

    public Character(long id, String fullName, String alias, String gender, URL picture, Set<Movie> movies) {
        this.id = id;
        this.fullName = fullName;
        this.alias = alias;
        this.gender = gender;
        this.picture = picture;
        this.movies = movies;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }

    @JsonGetter("movies")
    public List<Long> getMovies() {
        if (movies == null)
            return null;
        return movies.stream().map(m -> m.getId()).collect(Collectors.toList());
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
