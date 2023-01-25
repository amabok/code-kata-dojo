import java.util.ArrayList;
import java.util.List;

public class StringCalculator {
	private ParcelCandidateParser parser = new ParcelCandidateParser();
	private ParcelCandidateValidator validator = new ParcelCandidateValidator();
	
	public float add(String... inputs) {
		float result = 0;
		
		for (String input : inputs) {
			result += processSingleInput(input);
		}
		
		return result;
	}
	
	public float processSingleInput(String input) {
		if(input.isEmpty()) {
			return 0;
		}
		
		List<Float> parcels = extractParcels(input);
		
		Float result = Float.valueOf(0);
		for (Float parcel : parcels) {
			result += parcel;
		}
		
		return result;
	}

	public List<Float> extractParcels(String input) {
		List<Float> parcels = new ArrayList<Float>();
		
		DelimiterDetails delimiterDetails = parser.parseDelimiter(input);
		
		List<ParcelCandidate> candidates = parser.parse(delimiterDetails);
		for (ParcelCandidate parcelCandidate : candidates){	
			if(validator.validate(parcelCandidate,delimiterDetails)) {
				
				try {
					float candidateValue = Float.valueOf(parcelCandidate.text);
					parcels.add(candidateValue);
				}
				catch(Exception e) {
					System.err.println(e);
				}
			}
		}
			
		return parcels;
	}	
}