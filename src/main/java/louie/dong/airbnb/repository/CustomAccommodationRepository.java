package louie.dong.airbnb.repository;

import java.util.List;
import louie.dong.airbnb.web.accommodation.dto.AccommodationSearchRequest;
import louie.dong.airbnb.domain.Accommodation;

public interface CustomAccommodationRepository {

    List<Accommodation> searchAccommodations(AccommodationSearchRequest accommodationSearchRequest);
}
