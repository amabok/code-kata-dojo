import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DelimiterRuleParcelCandidateValidatorTests {

	private DelimiterRuleParcelCandidateValidator subject;
	
	@BeforeEach
	public void beforeEach() {
		this.subject = new DelimiterRuleParcelCandidateValidator();
	}
	
	@Test
	void test_validate_with_mismatching_delimiters_error_whole_string() {
		// Given
		DelimiterDetails delimiterDetails = new DelimiterDetails("|", "2,3");
		ParcelCandidate input = new ParcelCandidate("2,3", null, 0, null, 2);
		
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.validate(delimiterDetails, input);});
		
		assertEquals("'|' expected but ',' found at position 1.", thrown.getMessage());
	}
	
	@Test
	void test_validate_with_mismatching_delimiters_error_mid_string() {
		// Given
		DelimiterDetails delimiterDetails = new DelimiterDetails("|", "1|2,3");
		ParcelCandidate input = new ParcelCandidate("2,3", ",", 1, null, 4);
		
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.validate(delimiterDetails, input);});
		
		assertEquals("'|' expected but ',' found at position 3.", thrown.getMessage());
	}
}
