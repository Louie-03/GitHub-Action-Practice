package louie.dong.airbnb.web.accommodation.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import louie.dong.airbnb.domain.Accommodation;

@Getter
@AllArgsConstructor
public class AccommodationSearchResponse {

	private int count;
	private List<AccommodationResponse> accommodations;

	public AccommodationSearchResponse(List<Accommodation> accommodations, int nights) {
		this.count = accommodations.size();
		this.accommodations = accommodations.stream()
			.map(accommodation -> new AccommodationResponse(accommodation, nights))
			.collect(Collectors.toList());
	}
}
