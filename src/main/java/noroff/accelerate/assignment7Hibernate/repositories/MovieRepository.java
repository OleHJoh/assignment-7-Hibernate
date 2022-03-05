package noroff.accelerate.assignment7Hibernate.repositories;

import noroff.accelerate.assignment7Hibernate.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
