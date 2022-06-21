package louie.dong.airbnb.repository;

import static louie.dong.airbnb.domain.QAccommodation.accommodation;
import static louie.dong.airbnb.domain.QAccommodationImage.accommodationImage;
import static louie.dong.airbnb.domain.QBook.book;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import louie.dong.airbnb.domain.Accommodation;
import louie.dong.airbnb.web.accommodation.dto.AccommodationSearchRequest;

@RequiredArgsConstructor
public class CustomAccommodationRepositoryImpl implements CustomAccommodationRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Accommodation> searchAccommodations(
        AccommodationSearchRequest accommodationSearchRequest) {
        return queryFactory
            .selectFrom(accommodation).distinct()
            .leftJoin(accommodation.accommodationImages, accommodationImage)
                .on(accommodationImage.isThumbnailImage.eq(true))
            .leftJoin(accommodation.wishlist).fetchJoin()
            .leftJoin(accommodation.books, book).on(book.isCanceled.eq(false))
            .where(
                accommodation.country.contains(accommodationSearchRequest.getCountry()),
                checkPrice(accommodationSearchRequest.getMinPrice(),
                    accommodationSearchRequest.getMaxPrice()),
                checkInAndOut(accommodationSearchRequest),
                checkGuestCount(accommodationSearchRequest.getGuestCount())
            ).fetch();
    }

    private BooleanExpression checkGuestCount(Integer guestCount) {
        return guestCount != null ? accommodation.roomInformation.maxGuestCount.goe(guestCount) : null;
    }

    private BooleanExpression checkInAndOut(AccommodationSearchRequest request) {
        return ((request.getCheckIn() != null) && (request.getCheckOut() != null)) ?
            accommodation.books.isEmpty()
                .or(book.checkOut.loe(request.getCheckInWithTime())
                .or(book.checkIn.goe(request.getCheckOutWithTime()))) : null;
    }

    private BooleanExpression checkPrice(Integer minPrice, Integer maxPrice) {
        return ((minPrice != null) && (maxPrice != null)) ?
            accommodation.price.goe(minPrice).and(accommodation.price.loe(maxPrice)) : null;
    }

}
