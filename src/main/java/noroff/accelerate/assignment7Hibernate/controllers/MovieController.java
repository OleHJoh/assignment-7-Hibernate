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
        Franchise franchise = franchiseRepository.findById(movie.getFranchise()).get();
        movie.setFranchise(franchise);
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
        return new ResponseEntity<>(franchiseRepository.findById(movieRepository.findById(id).get().getFranchise()).get(), HttpStatus.OK);
    }

    @PutMapping("{id}/characters")
    public ResponseEntity<List<Character>> updateMovieCharacters(@PathVariable Long id, @RequestBody List<Long> ids){;
        Set<Character> characters = (Set<Character>) characterRepository.findAllById(ids);
        movieRepository.findById(id).get().setCharacters(characters);
        List<Long> ids2 = movieRepository.findById(id).get().getCharacters();
        return new ResponseEntity<>(characterRepository.findAllById(ids), HttpStatus.OK);
    }
}
