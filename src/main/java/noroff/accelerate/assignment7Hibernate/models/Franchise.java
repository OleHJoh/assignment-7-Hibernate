package noroff.accelerate.assignment7Hibernate.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Franchise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @NotBlank
    @Size(min = 0, max = 20)
    private String name;
    @NotBlank
    private String description;
    //Relationships
    @OneToMany(mappedBy = "franchise")
    private List<Movie> movies;

    public Franchise() {
    }

    public Franchise(long id, String name, String description, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.movies = movies;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonGetter("movies")
    public List<Long> getMovies() {
        if (movies == null)
            return null;
        return movies.stream().map(m -> m.getId()).collect(Collectors.toList());
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
