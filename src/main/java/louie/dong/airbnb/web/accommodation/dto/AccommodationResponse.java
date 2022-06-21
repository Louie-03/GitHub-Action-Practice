package louie.dong.airbnb.web.accommodation.dto;

import lombok.Getter;
import louie.dong.airbnb.domain.Accommodation;
import org.springframework.data.geo.Point;

@Getter
public class AccommodationResponse {

	private Long id;
	private String name;
	private String imageUrl;
	private double rating;
	private int reviewCount;
	private int price;
	private int totalPrice;
	private boolean existsWish;
	private double latitude;
	private double longitude;

	public AccommodationResponse(Accommodation accommodation, int nights) {
		this.id = accommodation.getId();
		this.name = accommodation.getName();
		this.rating = accommodation.getRating();
		this.reviewCount = accommodation.getReviewCount();
		this.price = accommodation.getPrice();
		this.latitude = accommodation.getPoint().getY();
		this.longitude = accommodation.getPoint().getX();
		this.totalPrice = accommodation.getPrice() * nights;
		this.existsWish = accommodation.existsWish();
		this.imageUrl = accommodation.getAccommodationImages().get(0).getImageUrl();
	}

}
