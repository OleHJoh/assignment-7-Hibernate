package noroff.accelerate.assignment7Hibernate.controllers;

import noroff.accelerate.assignment7Hibernate.models.Franchise;
import noroff.accelerate.assignment7Hibernate.models.Movie;
import noroff.accelerate.assignment7Hibernate.repositories.FranchiseRepository;
import noroff.accelerate.assignment7Hibernate.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/franchise")
public class FranchiseController {
    private FranchiseRepository franchiseRepository;
    private MovieRepository movieRepository;

    public FranchiseController(FranchiseRepository franchiseRepository, MovieRepository movieRepository) {
        this.franchiseRepository = franchiseRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchiseRepository.findAll(),status);
    }

    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise) {
        return new ResponseEntity<>(franchiseRepository.save(franchise), HttpStatus.CREATED);
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
}
