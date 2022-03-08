package noroff.accelerate.assignment7Hibernate.controllers;

import noroff.accelerate.assignment7Hibernate.models.Character;
import noroff.accelerate.assignment7Hibernate.models.Franchise;
import noroff.accelerate.assignment7Hibernate.models.Movie;
import noroff.accelerate.assignment7Hibernate.repositories.CharacterRepository;
import noroff.accelerate.assignment7Hibernate.repositories.FranchiseRepository;
import noroff.accelerate.assignment7Hibernate.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/franchise")
public class FranchiseController {
    private FranchiseRepository franchiseRepository;
    private MovieRepository movieRepository;
    private CharacterRepository characterRepository;

    public FranchiseController(FranchiseRepository franchiseRepository, MovieRepository movieRepository, CharacterRepository characterRepository) {
        this.franchiseRepository = franchiseRepository;
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
    }

    @GetMapping
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchiseRepository.findAll(),status);
    }

    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise) {
        franchiseRepository.save(franchise);
        if (franchise.getMovies() != null) {
            List<Movie> movies = movieRepository.findAllById(franchise.getMovies());
            for (Movie movie : movies) {
                movie.setFranchise(franchise);
                movieRepository.save(movie);
            }
        }
        return new ResponseEntity<>(franchiseRepository.getById(franchise.getId()), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Franchise> getFranchiseById(@PathVariable Long id) {
        return new ResponseEntity<>(franchiseRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("{id}/movies")
    public ResponseEntity<List<Movie>> getFranchiseMovies(@PathVariable Long id) {
        List<Long> ids = franchiseRepository.findById(id).get().getMovies();
        return new ResponseEntity<>(movieRepository.findAllById(ids), HttpStatus.OK);
    }

    @GetMapping("{id}/characters")
    public ResponseEntity<List<Character>> getFranchiseCharacters(@PathVariable Long id) {
        List<Long> ids = franchiseRepository.findById(id).get().getMovies();
        List<Long> ids2 = new ArrayList<>();
        for (Movie movie: movieRepository.findAllById(ids)) {
            List<Long> characterIds = movie.getCharacters();
            for (int i = 0; i < characterIds.size(); i++){
                if (ids2.contains(characterIds.get(i)) == false){
                    ids2.add(characterIds.get(i));
                }
            }
        }
        return new ResponseEntity<>(characterRepository.findAllById(ids2), HttpStatus.OK);
    }

    @PatchMapping("{id}/movies")
    public ResponseEntity<Franchise> updateFranchiseMovies(@PathVariable Long id, @RequestBody List<Long> moviesIds){
        List<Movie> movies = (movieRepository.findAllById(moviesIds));
        Franchise franchise = franchiseRepository.getById(id);
        franchise.setMovies(movies);
        franchiseRepository.save(franchise);
        for (Movie movie: movies) {
            movie.setFranchise(franchise);
            movieRepository.save(movie);
        }
        return new ResponseEntity<>(franchiseRepository.getById(id), HttpStatus.OK);
    }
}
