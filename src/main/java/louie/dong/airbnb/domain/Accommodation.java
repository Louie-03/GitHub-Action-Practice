package louie.dong.airbnb.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Accommodation {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)	// mysql에 autoincrement 가능하게 하는 옵션
	private Long id;

	@OneToMany(mappedBy = "accommodation")
	private List<Book> books = new ArrayList<>();

	@OneToMany(mappedBy = "accommodation")
	private List<AccommodationImage> accommodationImages = new ArrayList<>();

	@OneToMany(mappedBy = "accommodation")
	private List<Wish> wishlist = new ArrayList<>();

	@Embedded
	private RoomInformation roomInformation;

	@Column(name = "accommodation_name")
	private String name;

	@Column(name = "accommodation_description")
	private String description;

	private Point point;

	private String country;
	private int price;
	private double rating;
	private int reviewCount;
	private String hostName;
	private String hostImageUrl;
	private LocalTime checkInTime;
	private LocalTime checkOutTime;
	private int cleaningFee;
	private int serviceFee;
	private int accommodationFee;

	public void validAccommodation() {
		if (notExistsImage()) {
			throw new IllegalStateException("숙소의 메인 이미지가 존재하지 않습니다");
		}
	}

	public boolean existsWish() {
		return !wishlist.isEmpty();
	}

	public int getDate(LocalDate checkIn, LocalDate checkOut) {
		return (int) checkIn.until(checkOut, ChronoUnit.DAYS);
	}

	public int getTotalPrice(int date) {
		return price * date;
	}

	public int getDiscountRate(int date) {
		return DiscountPolicy.getDiscountRate(date);
	}

	public int getDiscountPrice(int totalPrice, int discountRate) {
		return (int) (totalPrice * (discountRate * 0.01));
	}

	public int getFinalPrice(int totalPrice, int discountPrice) {
		return totalPrice - discountPrice + cleaningFee
			+ serviceFee + accommodationFee;
	}

	private boolean notExistsImage() {
		return accommodationImages.isEmpty();
	}
}
