import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

	public float add(String... inputs) {
		float result = 0;
		
		for (String input : inputs) {
			result += processSingleInput(input);
		}
		
		return result;
	}
	
	private float processSingleInput(String input) {
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
			
		List<ParcelCandidate> candidates = extractParcelCandidates(input);
		
		for (ParcelCandidate parcelCandidate : candidates) {
			System.out.println(parcelCandidate);
		}
		
		for (ParcelCandidate parcelCandidate : candidates) {
			if(parcelCandidate.text.isEmpty()) {
				
				String message = String.format("Number expected but '%s' found at position %s.", parcelCandidate.endSeparator, parcelCandidate.endSeparatorIndex); 
				if(parcelCandidate.endSeparator == null) {
					message = "Number expected but EOF found.";
				}
				
				throw new IllegalArgumentException(message);
			}
			
			float candidateValue = Float.valueOf(parcelCandidate.text);
			parcels.add(candidateValue);	
		}
			
		return parcels;
	}
	
	private List<ParcelCandidate> extractParcelCandidates(String input){
		List<ParcelCandidate> result = new ArrayList<>();
		
		String patternExpression = ",|\n";
		Pattern pattern = Pattern.compile(patternExpression);
		Matcher matcher = pattern.matcher(input);
		
		List<MatchResult> res = 
				matcher.results()
					.collect(Collectors.toList());
		
		int previouslyMatchedSeparatorEndIndex = 0;
		String previouslyMatchedSeparator = null;
		for (MatchResult matchResult : res) {
			
			int matchedSeparatorStartIndex = matchResult.start();
			
			String splitResult = generateSplitString(input, previouslyMatchedSeparatorEndIndex, matchedSeparatorStartIndex);
					
			ParcelCandidate candidate = new ParcelCandidate();
			candidate.text = splitResult;
			candidate.startSeparator = previouslyMatchedSeparator;
			candidate.startSeparatorIndex = previouslyMatchedSeparatorEndIndex;
			candidate.endSeparator = matchResult.group();
			candidate.endSeparatorIndex = matchResult.start();
			result.add(candidate);
		
			previouslyMatchedSeparatorEndIndex = matchResult.start();
			previouslyMatchedSeparator = matchResult.group();
		}
		
		String remainingString = generateRemainingString(input, previouslyMatchedSeparatorEndIndex);
		
		ParcelCandidate candidate = new ParcelCandidate();
		candidate.text = remainingString;
		candidate.startSeparatorIndex = previouslyMatchedSeparatorEndIndex;
		candidate.endSeparatorIndex = input.length()-1;
		candidate.startSeparator = previouslyMatchedSeparator;
		candidate.endSeparator = null;
		result.add(candidate);
			
		return result;
	}
	
	private String generateSplitString(String input, int startSeparatorIndex, int endSeparatorIndex) {
		return input.substring(calculateStartIndex(input,startSeparatorIndex), endSeparatorIndex);
	}
	
	private String generateRemainingString(String input, int separatorStartIndex) {
		// If the separator is at the last string index then return an empty string
		if(separatorStartIndex == input.length()-1) {
			return "";
		}
		
		return input.substring(calculateStartIndex(input,separatorStartIndex));
	}
	private int calculateStartIndex(String input, int index) {
		//If we are at the start of the string we don't want to eat any values
		int result = index == 0 ? 0 : index + 1;
	
		//If we are at the end of the string we don't want to go out of bounds
		if(result > input.length()-1) {
			result = input.length()-1;
		}
		
		return result;
	}
}
