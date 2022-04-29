import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParcelCandidateParser {
	
	public List<ParcelCandidate> parse(String input){
		List<ParcelCandidate> result = new ArrayList<>();
		
		String patternExpression = ",|\n";
		Pattern pattern = Pattern.compile(patternExpression);
		Matcher matcher = pattern.matcher(input);
		
		List<MatchResult> res = 
				matcher.results()
					.collect(Collectors.toList());
		
		String previouslyMatchedSeparator = null;
		//TODO: shouldnt this -1 or null if we don't use primitive types?
		int previouslyMatchedSeparatorIndex = 0;
		for (MatchResult matchResult : res) {
			
			int matchedSeparatorStartIndex = matchResult.start();
			
			String splitResult = generateSplitString(input, previouslyMatchedSeparatorIndex, matchedSeparatorStartIndex);
					
			ParcelCandidate candidate = new ParcelCandidate(splitResult,previouslyMatchedSeparator,previouslyMatchedSeparatorIndex, matchResult.group(),  matchResult.start());
			result.add(candidate);
		
			previouslyMatchedSeparator = matchResult.group();
			previouslyMatchedSeparatorIndex = matchResult.start();
		}
		
		String remainingString = generateRemainingString(input, previouslyMatchedSeparatorIndex);
		
		ParcelCandidate candidate = new ParcelCandidate(remainingString, previouslyMatchedSeparator, previouslyMatchedSeparatorIndex, null, input.length()-1);
		result.add(candidate);
			
		return result;
	}
	
	public String generateSplitString(String input, int startSeparatorIndex, int endSeparatorIndex) {
		return input.substring(calculateStartIndex(input,startSeparatorIndex), endSeparatorIndex);
	}
	
	public String generateRemainingString(String input, int separatorStartIndex) {
		// If the separator is at the last string index then return an empty string
		if(separatorStartIndex == input.length()-1) {
			return "";
		}
		
		return input.substring(calculateStartIndex(input,separatorStartIndex));
	}
	
	public int calculateStartIndex(String input, int index) {
		//If we are at the start of the string we don't want to eat any values
		int result = index == 0 ? 0 : index + 1;
	
		//If we are at the end of the string we don't want to go out of bounds
		if(result > input.length()-1) {
			result = input.length()-1;
		}
		
		return result;
	}
}
