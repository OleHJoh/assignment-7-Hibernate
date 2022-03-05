package noroff.accelerate.assignment7Hibernate.controllers;

import noroff.accelerate.assignment7Hibernate.models.Franchise;
import noroff.accelerate.assignment7Hibernate.repositories.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/franchise")
public class FranchiseController {
    private FranchiseRepository franchiseRepository;

    public FranchiseController(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
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
}
