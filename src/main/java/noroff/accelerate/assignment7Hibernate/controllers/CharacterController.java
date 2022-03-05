package noroff.accelerate.assignment7Hibernate.controllers;

import noroff.accelerate.assignment7Hibernate.models.Character;
import noroff.accelerate.assignment7Hibernate.models.Movie;
import noroff.accelerate.assignment7Hibernate.repositories.CharacterRepository;
import noroff.accelerate.assignment7Hibernate.repositories.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new ResponseEntity<>(characterRepository.save(character), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) {
        return new ResponseEntity<>(characterRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("{id}/movies")
    public ResponseEntity<List<Movie>> getCharacterMovies(@PathVariable Long id) {
        List<Long> ids = characterRepository.findById(id).get().getMovies();
        return new ResponseEntity<>(movieRepository.findAllById(ids), HttpStatus.OK);
    }
}
