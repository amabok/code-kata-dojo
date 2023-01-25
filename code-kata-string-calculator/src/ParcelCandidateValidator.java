public class ParcelCandidateValidator {

	private DelimiterRuleParcelCandidateValidator delimiterRuleValidator = new DelimiterRuleParcelCandidateValidator();
	
	public boolean validate(ParcelCandidate candidate, DelimiterDetails delimiterDetails) {
		if(candidate.text.isEmpty()) {
			String message = String.format("Number expected but '%s' found at position %s.", candidate.endSeparator, candidate.endSeparatorIndex); 
			if(candidate.endSeparator == null) {
				message = "Number expected but EOF found.";
			}
			
			throw new IllegalArgumentException(message);
		}
		
		delimiterRuleValidator.validate(delimiterDetails, candidate);
		
		return true;
	}
}