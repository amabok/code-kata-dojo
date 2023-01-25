import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringCalculatorTests {
	private StringCalculator subject = new StringCalculator();
	
	@BeforeEach
	public void beforeEach() {
		this.subject = new StringCalculator();
	}
	
	@Test
	void test_add_with_empty_input() {
		// Given
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
		String input = "175.2,\n35";
		
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.add(input);});
		
		assertEquals("Number expected but '\n' found at position 6.", thrown.getMessage());
	}
	
	@Test
	void test_add_with_invalid_input_at_last_separator() {
		// Given		
		String input = "1,3,";
		
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.add(input);});
		
		assertEquals("Number expected but EOF found.", thrown.getMessage());
	}
	
	@Test
	void test_add_with_custom_separator_1() {
		// Given// Given
		String input = "//;\n1;2";
		
		float expectedResult = 3;
		
		// When
		float result = subject.add(input);
		
		// Then
		assertEquals(expectedResult, result);
	}

	@Test
	void test_add_with_custom_separator_2() {
		// Given// Given
		String input = "//|\n1|2|3";
		
		float expectedResult = 6;
		
		// When
		float result = subject.add(input);
		
		// Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_add_with_custom_separator_3() {
		// Given// Given
		String input = "//sep\n2sep3";
		
		float expectedResult = 5;
		
		// When
		float result = subject.add(input);
		
		// Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_add_with_custom_separator_with_mismatched_separators() {
		// Given		
		String input = "//|\n1|2,3";
				
		Throwable thrown = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> { subject.add(input);});
		
		assertEquals( "'|' expected but ',' found at position 3.", thrown.getMessage());
	}
	
}
