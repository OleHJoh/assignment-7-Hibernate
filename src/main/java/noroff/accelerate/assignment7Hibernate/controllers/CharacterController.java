package noroff.accelerate.assignment7Hibernate.controllers;

import noroff.accelerate.assignment7Hibernate.models.Character;
import noroff.accelerate.assignment7Hibernate.models.Movie;
import noroff.accelerate.assignment7Hibernate.repositories.CharacterRepository;
import noroff.accelerate.assignment7Hibernate.repositories.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/character")
public class CharacterController {
    private CharacterRepository characterRepository;
    private MovieRepository movieRepository;

    public CharacterController(CharacterRepository characterRepository, MovieRepository movieRepository) {
        this.characterRepository = characterRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        return new ResponseEntity<>(characterRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Character> addCharacter(@RequestBody Character character) {
        characterRepository.save(character);
        if (character.getMovies() != null) {
            Set<Movie> movies = new HashSet<>(movieRepository.findAllById(character.getMovies()));
            character.setMovies(movies);
            for (Movie movie : movies) {
                List<Long> ids = movieRepository.getById(movie.getId()).getCharacters();
                Set<Character> characters = new HashSet<>(characterRepository.findAllById(ids));
                characters.add(character);
                movie.setCharacters(characters);
                movieRepository.save(movie);
            }
        }
        return new ResponseEntity<>(characterRepository.getById(character.getId()), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) {
        return new ResponseEntity<>(characterRepository.getById(id), HttpStatus.OK);
    }

    @GetMapping("{id}/movies")
    public ResponseEntity<List<Movie>> getCharacterMovies(@PathVariable Long id) {
        List<Long> ids = characterRepository.getById(id).getMovies();
        return new ResponseEntity<>(movieRepository.findAllById(ids), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable Long id, @RequestBody Character character){
        Set<Movie> movies = new HashSet<>(movieRepository.findAllById(character.getMovies()));
        character.setMovies(movies);
        characterRepository.save(character);
        for (Movie movie: movies) {
            List<Long> ids = movieRepository.getById(movie.getId()).getCharacters();
            Set<Character> characters = new HashSet<>(characterRepository.findAllById(ids));
            characters.add(character);
            movie.setCharacters(characters);
            movieRepository.save(movie);
        }
        return new ResponseEntity<>(characterRepository.getById(character.getId()), HttpStatus.OK);
    }
}
