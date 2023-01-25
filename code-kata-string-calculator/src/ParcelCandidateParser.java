import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParcelCandidateParser {
	
	private static final String DEFAULT_DELIMITER = ",";
	private static final String DEFAULT_DELIMITER_EXPRESSION = ",|\n";
	
	public List<ParcelCandidate> parse(DelimiterDetails delimiterDetailsInput){
		// 'Clone' the object so we can change it later
		DelimiterDetails delimiterDetails = new DelimiterDetails(
				delimiterDetailsInput.delimiter, 
				delimiterDetailsInput.delimiterHeaderlessInput);
		
		List<ParcelCandidate> result = new ArrayList<>();
		
		//TODO: consider extracting a method for patternExpression creation
		String patternExpression = delimiterDetails.delimiter;
		if(delimiterDetails.delimiter == null) {
			delimiterDetails.delimiter = DEFAULT_DELIMITER;
			patternExpression = DEFAULT_DELIMITER_EXPRESSION;
		}
		
		if(patternExpression.equals("|")) {
			patternExpression = "\\|";
		}
		
		
		String input = delimiterDetails.delimiterHeaderlessInput;
		
		Pattern pattern = Pattern.compile(patternExpression);
		Matcher matcher = pattern.matcher(input);
		
		List<MatchResult> res = 
				matcher.results()
					.collect(Collectors.toList());
		
		String previouslyMatchedSeparator = null;
		//TODO: shouldn't this -1 or null if we don't use primitive types?
		int previouslyMatchedSeparatorIndex = 0;
		for (MatchResult matchResult : res) {
			
			int matchedSeparatorStartIndex = matchResult.start();
			
			String splitResult = generateSplitString(
						input, 
						delimiterDetails.delimiter, 
						previouslyMatchedSeparatorIndex,
						matchedSeparatorStartIndex);
					
			ParcelCandidate candidate = new ParcelCandidate(splitResult,previouslyMatchedSeparator,previouslyMatchedSeparatorIndex, matchResult.group(),  matchResult.start());
			result.add(candidate);
		
			previouslyMatchedSeparator = matchResult.group();
			previouslyMatchedSeparatorIndex = matchResult.start();
		}
		
		String remainingString = generateRemainingString(input, delimiterDetails.delimiter, previouslyMatchedSeparatorIndex);
		
		ParcelCandidate candidate = new ParcelCandidate(remainingString, previouslyMatchedSeparator, previouslyMatchedSeparatorIndex, null, input.length()-1);
		result.add(candidate);
			
		return result;
	}
	
	public DelimiterDetails parseDelimiter(String input) {
		// Find for delimiter details
		String delimiterStart = "//";
		String delimiterEnd = "\n";
		String patternExpression = String.format("%s.*%s*",delimiterStart,delimiterEnd);
		
		Pattern pattern = Pattern.compile(patternExpression);
		Matcher matcher = pattern.matcher(input);
	
		List<MatchResult> res = 
				matcher.results()
					.collect(Collectors.toList());
		
		String delimiter = null;
		for (MatchResult matchResult : res) {
			String matchedText = matchResult.group();
			delimiter = matchedText.replace(delimiterStart, "");
			delimiter = delimiter.replace(delimiterEnd,"");
		}
		
		if(delimiter==null) {
			DelimiterDetails details = new DelimiterDetails(null, input);
			return details;
		}
		
		// Extract input without delimiter header
		int delimiterHeaderEnd = input.indexOf("\n")+1;
		return new DelimiterDetails(delimiter,input.substring(delimiterHeaderEnd)); 
	}
	
	public String generateSplitString(String input, String separator, int startSeparatorIndex, int endSeparatorIndex) {
		return input.substring(calculateStartIndex(input,startSeparatorIndex), endSeparatorIndex);
	}
	
	public String generateRemainingString(String input, String separator, int separatorStartIndex) {
		// If the separator is at the last string index then return an empty string
		if(separatorStartIndex == input.length()-1) {
			return "";
		}
		
		return input.substring(calculateStartIndex(input, separatorStartIndex) + separator.length()-1);
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
