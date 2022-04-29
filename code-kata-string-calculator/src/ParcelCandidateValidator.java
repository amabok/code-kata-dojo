
public class ParcelCandidateValidator {

	public boolean validate(ParcelCandidate candidate) {
		if(candidate.text.isEmpty()) {
			String message = String.format("Number expected but '%s' found at position %s.", candidate.endSeparator, candidate.endSeparatorIndex); 
			if(candidate.endSeparator == null) {
				message = "Number expected but EOF found.";
			}
			
			throw new IllegalArgumentException(message);
		}
		
		return true;
	}
}