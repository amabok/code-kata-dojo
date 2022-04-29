import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParcelCandidateValidatorTests {
	private ParcelCandidateValidator subject;
	
	@BeforeEach
	public void beforeEach() {
		this.subject = new ParcelCandidateValidator();
	}
	
	@Test
	public void validate_happy_path() {
		// Given
		ParcelCandidate input = new ParcelCandidate("1", null, 0, null, 1);
		
		boolean expectedResult = true;
		
		// When
		var result = subject.validate(input);
		
		// Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_add_with_invalid_input_between_separators() {
		// Given
		ParcelCandidate input = new ParcelCandidate("", ",", 5, "\n", 6);
	
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.validate(input);});
		
		assertEquals("Number expected but '\n' found at position 6.", thrown.getMessage());
	}
	
	@Test
	void test_add_with_invalid_input_at_last_separator() {
		// Given
		ParcelCandidate input = new ParcelCandidate("", ",", 3, null, 3);
		
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.validate(input);});
		
		assertEquals("Number expected but EOF found.", thrown.getMessage());
	}
}
