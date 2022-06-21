package louie.dong.airbnb.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import louie.dong.airbnb.domain.Accommodation;
import louie.dong.airbnb.domain.Wish;
import louie.dong.airbnb.repository.WishRepository;
import louie.dong.airbnb.web.wishlist.dto.WishResponse;
import louie.dong.airbnb.web.wishlist.dto.WishSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WishService {

    private final AccommodationService accommodationService;
    private final WishRepository wishRepository;

    @Transactional
    public void save(WishSaveRequest wishSaveRequest) {
        Accommodation accommodation = accommodationService.getAccommodationOrThrow(
            wishSaveRequest.getAccommodationId());

        boolean existsWish = wishRepository.existsByAccommodationId(
            wishSaveRequest.getAccommodationId());

        if (!existsWish) {
            Wish wish = new Wish(accommodation);
            wishRepository.save(wish);
        }
    }

    @Transactional
    public void delete(Long id) {
        wishRepository.deleteById(id);
    }

    public List<WishResponse> findAll() {
        return wishRepository.findAll().stream()
            .map(WishResponse::new)
            .collect(Collectors.toList());
    }
}
