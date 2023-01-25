import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParcelCandidateParserTests {

	private ParcelCandidateParser subject;
	
	@BeforeEach
	public void beforeEach() {
		this.subject = new ParcelCandidateParser();
	}
	
	@Test
	void test_parse_happy_path() {
		// Given
		DelimiterDetails input = new DelimiterDetails(null,"1\n2,3");
		
		List<ParcelCandidate> expectedResult = 
				List.of(
						new ParcelCandidate("1", null, 0, "\n", 1),
						new ParcelCandidate("2", "\n", 1, ",", 3),
						new ParcelCandidate("3", ",", 3, null, 4)
						);
		
		// When
		List<ParcelCandidate> result = subject.parse(input);
		
		// Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_parse_newline_separator() {
		// Given
		DelimiterDetails input = new DelimiterDetails(null,"1,1.1,2.2");
		
		List<ParcelCandidate> expectedResult = 
				List.of(
						new ParcelCandidate("1", null, 0, ",", 1),
						new ParcelCandidate("1.1", ",", 1, ",", 5),
						new ParcelCandidate("2.2", ",", 5, null, 8)
						);
		
		// When
		List<ParcelCandidate> result = subject.parse(input);
		
		// Then
		assertIterableEquals(expectedResult, result);
	}
	
	
	@Test
	void test_parse_newline_separator_with_invalid_input_between_separators() {
		// Given
		DelimiterDetails input = new DelimiterDetails(null,"175.2,\n35");
		
		List<ParcelCandidate> expectedResult =
				List.of(
					new ParcelCandidate("175.2", null, 0, ",", 5),
					new ParcelCandidate("", ",", 5, "\n", 6),
					new ParcelCandidate("35", "\n", 6, null, 8));
		// When 		
		List<ParcelCandidate> result = subject.parse(input);
		
		// Then
		assertEquals(expectedResult,result);
	}
	
	@Test
	void test_parse_newline_separator_with_invalid_input_at_last_separator() {
		// Given
		DelimiterDetails input = new DelimiterDetails(null, "1,3,");
		
		List<ParcelCandidate> expectedResult =
				List.of(
					new ParcelCandidate("1", null, 0, ",", 1),
					new ParcelCandidate("3", ",", 1, ",", 3),
					new ParcelCandidate("", ",", 3, null, 3));
		// When 		
		List<ParcelCandidate> result = subject.parse(input);
	
		// Then
		assertEquals(expectedResult,result);
	}
	
	@Test
	void test_parse_delimiter_happy_path_1() {
		// Given
		String input = "//;\n1;2";
		
		String expectedDelimiter = ";";
		String expectedDelimiterHeaderlessInput = "1;2";
		
		// When
		DelimiterDetails result = subject.parseDelimiter(input);
		
		// Then
		assertEquals(expectedDelimiter, result.delimiter);
		assertEquals(expectedDelimiterHeaderlessInput, result.delimiterHeaderlessInput);
	}
	
	@Test
	void test_parse_delimiter_happy_path_2() {
		// Given
		String input = "//|\n1|2|3";
		
		String expectedDelimiter = "|";
		String expectedDelimiterHeaderlessInput = "1|2|3";
		
		// When
		DelimiterDetails result = subject.parseDelimiter(input);
		
		// Then
		assertEquals(expectedDelimiter, result.delimiter);
		assertEquals(expectedDelimiterHeaderlessInput, result.delimiterHeaderlessInput);
	}
	
	
	@Test
	void test_parse_delimiter_happy_path_3() {
		// Given
		String input = "//sep\n1sep2";
		
		String expectedDelimiter = "sep";
		String expectedDelimiterHeaderlessInput = "1sep2";
		
		// When
		DelimiterDetails result = subject.parseDelimiter(input);
		
		// Then
		assertEquals(expectedDelimiter, result.delimiter);
		assertEquals(expectedDelimiterHeaderlessInput, result.delimiterHeaderlessInput);
	}
}
