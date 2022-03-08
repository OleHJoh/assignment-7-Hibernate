package noroff.accelerate.assignment7Hibernate.controllers;

import noroff.accelerate.assignment7Hibernate.models.Character;
import noroff.accelerate.assignment7Hibernate.models.Franchise;
import noroff.accelerate.assignment7Hibernate.models.Movie;
import noroff.accelerate.assignment7Hibernate.repositories.CharacterRepository;
import noroff.accelerate.assignment7Hibernate.repositories.FranchiseRepository;
import noroff.accelerate.assignment7Hibernate.repositories.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/movie")
public class MovieController {
    private MovieRepository movieRepository;
    private CharacterRepository characterRepository;
    private FranchiseRepository franchiseRepository;

    public MovieController(MovieRepository movieRepository, CharacterRepository characterRepository, FranchiseRepository franchiseRepository) {
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
        this.franchiseRepository = franchiseRepository;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        if (movie.getFranchise() != null) {
            Franchise franchise = franchiseRepository.findById(movie.getFranchise()).get();
            movie.setFranchise(franchise);
        }
        if (movie.getCharacters() != null) {
            Set<Character> characters = new HashSet<>(characterRepository.findAllById(movie.getCharacters()));
            movie.setCharacters(characters);
        }
        return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return new ResponseEntity<>(movieRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("{id}/characters")
    public ResponseEntity<List<Character>> getMovieCharacters(@PathVariable Long id) {
        List<Long> ids = movieRepository.findById(id).get().getCharacters();
        return new ResponseEntity<>(characterRepository.findAllById(ids), HttpStatus.OK);
    }

    @GetMapping("{id}/franchise")
    public ResponseEntity<Franchise> getMovieFranchise(@PathVariable Long id) {
        Long franchiseId = movieRepository.getById(id).getFranchise();
        return new ResponseEntity<>(franchiseRepository.getById(franchiseId), HttpStatus.OK);
    }

    @PatchMapping("{id}/characters")
    public ResponseEntity<Movie> updateMovieCharacters(@PathVariable Long id, @RequestBody List<Long> ids){
        Set<Character> characters = new HashSet<>(characterRepository.findAllById(ids));
        Movie movie = movieRepository.getById(id);
        movie.setCharacters(characters);
        return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.OK);
    }
}
