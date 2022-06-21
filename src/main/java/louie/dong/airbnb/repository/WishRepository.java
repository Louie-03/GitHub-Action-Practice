package louie.dong.airbnb.repository;

import louie.dong.airbnb.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

	boolean existsByAccommodationId(Long accommodationId);

}
