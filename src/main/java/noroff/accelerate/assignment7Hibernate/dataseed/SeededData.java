package noroff.accelerate.assignment7Hibernate.dataseed;

import noroff.accelerate.assignment7Hibernate.models.Character;
import noroff.accelerate.assignment7Hibernate.models.Franchise;
import noroff.accelerate.assignment7Hibernate.models.Movie;
import noroff.accelerate.assignment7Hibernate.repositories.CharacterRepository;
import noroff.accelerate.assignment7Hibernate.repositories.FranchiseRepository;
import noroff.accelerate.assignment7Hibernate.repositories.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class SeededData implements CommandLineRunner {
    private MovieRepository movieRepository;
    private CharacterRepository characterRepository;
    private FranchiseRepository franchiseRepository;

    public SeededData(MovieRepository movieRepository, CharacterRepository characterRepository, FranchiseRepository franchiseRepository) {
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        if (movieRepository.findAll().size() == 0 && characterRepository.findAll().size() == 0 && franchiseRepository.findAll().size() == 0){
            Movie movie1 = new Movie(1,"Batman","Action", new Date(2022,03,01),"Lucas Films",null,null);
            Movie movie2 = new Movie(2,"Batman the Dark Knight","Action", new Date(2022,03,01),"Lucas Films",null,null);
            Movie movie3 = new Movie(3,"Iron Man", "Action, si-fi", new Date(2022,03,01), "Jon Favreau", null, null);

            Franchise franchise1 = new Franchise(1,"Marvel", "Marvel Universe of Heroes", null);
            Franchise franchise2 = new Franchise(2,"DC", "DC Universe of Heroes", null);
            Franchise franchise3 = new Franchise(3,"Disney", "Disney Studios of cartoons", null);

            Character character1 = new Character(1,"Bruce Wayne", "Batman", "Male", null, null);
            Character character2 = new Character(2,"Jack Napier", "Joker", "Male", null, null);
            Character character3 = new Character(3,"Alfred", "Alfred the Butler", "Male", null, null);
            Character character4 = new Character(4,"Tony Stark", "Iron Man", "Male", null, null);

            movie1.setFranchise(franchise2);
            movie2.setFranchise(franchise2);
            movie3.setFranchise(franchise1);

            Set<Character> characters = new HashSet<>();
            characters.add(character1);
            characters.add(character2);
            characters.add(character3);
            movie1.setCharacters(characters);

            Set<Character> characters1 = new HashSet<>();
            characters1.add(character1);
            characters1.add(character3);
            movie2.setCharacters(characters1);

            Set<Character> characters2 = new HashSet<>();
            characters2.add(character4);
            movie3.setCharacters(characters2);

            characterRepository.saveAll(characters);
            characterRepository.saveAll(characters1);
            characterRepository.saveAll(characters2);

            franchiseRepository.save(franchise1);
            franchiseRepository.save(franchise2);
            franchiseRepository.save(franchise3);

            movieRepository.save(movie1);
            movieRepository.save(movie2);
            movieRepository.save(movie3);
        }
    }
}
