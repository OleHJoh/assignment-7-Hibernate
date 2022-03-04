package noroff.accelerate.assignment7Hibernate.models;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.net.URL;
import java.util.Set;

@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    @Nullable
    private String alias;
    private String gender;
    private URL picture;
    //Relationships
    @ManyToMany(mappedBy = "characters")
    private Set<Movie> movies;

    public Character() {
    }

    public Character(long id, String fullName, String alias, String gender, URL picture) {
        this.id = id;
        this.fullName = fullName;
        this.alias = alias;
        this.gender = gender;
        this.picture = picture;
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
}
