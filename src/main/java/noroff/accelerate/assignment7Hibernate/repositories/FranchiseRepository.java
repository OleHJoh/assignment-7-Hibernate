package noroff.accelerate.assignment7Hibernate.repositories;

import noroff.accelerate.assignment7Hibernate.models.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
}
