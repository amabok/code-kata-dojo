import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringCalculatorTests {

	@Test
	void test_add_with_empty_input() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input = "";
		
		float expectedResult = 0;
		// When
		float result = subject.add(input);
		
		// Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_add_happy_path() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input = "1,1.1,2.2";
		
		float expectedResult = 4.3f;
		// When
		float result = subject.add(input);
		
		// Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_add_with_variable_arguments() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input1 = "1,1.1,2.2";
		String input2 = "3.4,6.5,1.2"; 
		
		float expectedResult = 15.4f;
		// Whens
		float result = subject.add(input1, input2);
		
		// Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_add_with_newline_as_separator() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input = "1\n2,3";
		
		float expectedResult = 6f;
		// Whens
		float result = subject.add(input);
		
		// Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_add_with_invalid_input_between_separators() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input = "175.2,\n35";
		
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.add(input);});
		
		assertEquals("Number expected but '\n' found at position 6.", thrown.getMessage());
	}
	
	@Test
	void test_add_with_invalid_input_at_last_separator() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input = "1,3,";
		
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.add(input);});
		
		assertEquals("Number expected but EOF found.", thrown.getMessage());
	}
	
	// Helper methods tests
	@Test
	void test_extractParcels_happy_path() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input = "1\n2,3";
		
		List<Float> expectedResult=  List.of(1f,2f,3f);
		
		// When
		List<Float> result = subject.extractParcels(input);
		
		// Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_extractParcels_newline_separator() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input = "1,1.1,2.2";
		
		List<Float> expectedResult=  List.of(1f,1.1f,2.2f);
		
		// When
		List<Float> result = subject.extractParcels(input);
		
		// Then
		assertEquals(expectedResult, result);
	}

	@Test
	void test_extractParcels_newline_separator_with_invalid_input_between_separators() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input = "175.2,\n35";
		
		// When / Then		
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.extractParcels(input);});
		
		assertEquals("Number expected but '\n' found at position 6.", thrown.getMessage());
	}
	
	@Test
	void test_extractParcels_newline_separator_with_invalid_input_at_last_separator() {
		// Given
		StringCalculator subject = new StringCalculator();
		
		String input = "1,3,";
		
		// When / Then		
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.extractParcels(input);});
		
		assertEquals("Number expected but EOF found.", thrown.getMessage());
	}
}
