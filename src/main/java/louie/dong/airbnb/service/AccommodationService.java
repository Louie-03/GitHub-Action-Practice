package louie.dong.airbnb.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import louie.dong.airbnb.repository.AccommodationRepository;
import louie.dong.airbnb.web.accommodation.dto.AccommodationDetailPriceRequest;
import louie.dong.airbnb.web.accommodation.dto.AccommodationDetailPriceResponse;
import louie.dong.airbnb.web.accommodation.dto.AccommodationDetailResponse;
import louie.dong.airbnb.web.accommodation.dto.AccommodationPriceResponse;
import louie.dong.airbnb.web.accommodation.dto.AccommodationSearchRequest;
import louie.dong.airbnb.web.accommodation.dto.AccommodationSearchResponse;
import louie.dong.airbnb.domain.Accommodation;
import louie.dong.airbnb.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final WishRepository wishRepository;

    public AccommodationPriceResponse findPrices(String country) {
        List<Integer> prices = accommodationRepository.findPricesByAccommodation(country);
        return new AccommodationPriceResponse(calculateAverage(prices), prices);
    }

    public AccommodationDetailResponse findById(Long id) {
        Accommodation accommodation = getAccommodationOrThrow(id);
        boolean wish = wishRepository.existsByAccommodationId(id);
        return new AccommodationDetailResponse(accommodation, wish);
    }

    public AccommodationDetailPriceResponse findDetailPrice(Long id,
        AccommodationDetailPriceRequest accommodationDetailPriceRequest) {
        Accommodation accommodation = getAccommodationOrThrow(id);
        LocalDate checkIn = accommodationDetailPriceRequest.getCheckIn();
        LocalDate checkOut = accommodationDetailPriceRequest.getCheckOut();
        return new AccommodationDetailPriceResponse(checkIn, checkOut, accommodation);
    }

    public AccommodationSearchResponse findAccommodations(
        AccommodationSearchRequest accommodationSearchRequest) {
		LocalDate checkIn = accommodationSearchRequest.getCheckIn();
		LocalDate checkOut = accommodationSearchRequest.getCheckOut();

		List<Accommodation> accommodations = accommodationRepository.
			searchAccommodations(accommodationSearchRequest);

        validAccommodations(accommodations);

        int nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return new AccommodationSearchResponse(accommodations, nights);
    }

    public Accommodation getAccommodationOrThrow(Long id) {
        return accommodationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? id?????????."));

    }

    private void validAccommodations(List<Accommodation> accommodations) {
        for (Accommodation accommodation : accommodations) {
            accommodation.validAccommodation();
        }
    }

    private int calculateAverage(List<Integer> prices) {
        return (int) prices.stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElseThrow();
    }
}
