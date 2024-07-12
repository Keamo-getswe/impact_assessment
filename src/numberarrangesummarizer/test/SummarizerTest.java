package numberarrangesummarizer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import numberarrangesummarizer.main.Summarizer;

import java.util.Collection;
import java.util.TreeSet;

public class SummarizerTest {
	
	@Test
	public void testCollectOrder() {
		/* The assumption for this case is that the input could be
		 * presented in any order and should be presented sorted.
		 * */
		Summarizer sum = new Summarizer();
		
		//Test ascending ordered input
		String input1 = "1,3,4,5,6,7,8";
		
		Collection<Integer> testCollection = sum.collect(input1);
		
		TreeSet<Integer> numbers = new TreeSet<Integer>();
		numbers.add(1);
		numbers.add(3);
		numbers.add(4);
		numbers.add(5);
		numbers.add(6);
		numbers.add(7);
		numbers.add(8);
		Collection<Integer> expectedCollection = numbers;
		assertEquals(testCollection, expectedCollection);
		
		//Test descending ordered input
				String input2 = "8,7,6,5,4,3,1";
		testCollection = sum.collect(input2);
		assertEquals(testCollection, expectedCollection);
		
		//Test randomly ordered input
		String input3 = "1,3,6,5,4,7,8";
		testCollection = sum.collect(input3);
		assertEquals(testCollection, expectedCollection);
		
		//Test negative numbered input
		String input4 = "-1,-3,-6,-5,4,7,8";
		testCollection = sum.collect(input4);
		TreeSet<Integer> numbers2 = new TreeSet<Integer>();
		numbers2.add(-1);
		numbers2.add(-3);
		numbers2.add(4);
		numbers2.add(-5);
		numbers2.add(-6);
		numbers2.add(7);
		numbers2.add(8);
		expectedCollection = numbers2;
		assertEquals(testCollection, expectedCollection);
	}
	
	@Test
	public void testCollectSpace() {
		/* The assumption for this case is that the input could be
		 * presented with any number of spaces and it should not 
		 * affect the list.
		 * */
		Summarizer sum = new Summarizer();
		
		//Test whitespaced input string
		String input = " 1, 3 ,  6,7  ,8";
		
		Collection<Integer> testCollection = sum.collect(input);
		TreeSet<Integer> numbers = new TreeSet<Integer>();
		numbers.add(1);
		numbers.add(3);
		numbers.add(6);
		numbers.add(7);
		numbers.add(8);
		Collection<Integer> expectedCollection = numbers;

		assertEquals(testCollection, expectedCollection);
	}
	
	@Test
	public void testCollectEmptyString() {
		/* The assumption for this case is that when no list
		 * is provided per one of the following, then no
		 * list should be returned.
		 * */
		Summarizer sum = new Summarizer();
		
		//Test null input string
		String input1 = "";
		
		//Test whitespace string
		String input2 = " ";
		
		//Test newline string
		String input3 = "\n";
		
		Collection<Integer> testCollection = sum.collect(input1);
		Collection<Integer> expectedCollection = new TreeSet<Integer>();
		
		assertEquals(testCollection, expectedCollection);
		
		testCollection = sum.collect(input2);
		assertEquals(testCollection, expectedCollection);
		
		
		testCollection = sum.collect(input3);
		assertEquals(testCollection, expectedCollection);
	}
	
	@Test
	public void testCollectNonNumeric() {
		/* The assumption for this case is that the input could be
		 * non-numeric and an error should be printed. The error was
		 * chosen per my judgement since specifics were omitted.
		 * */
		Summarizer sum = new Summarizer();
		String input = "a";

		//Test non-numeric data will trigger exception.
		assertThrows(RuntimeException.class, () -> { 
			sum.collect(input);
		});
	}
	
	@Test
	public void testCollectMissingListedValue() {
		/* The assumption for this case is that the input could
		 * accidentally miss a value hence an error should occur.
		 * */
		Summarizer sum = new Summarizer();
		String in1 = "1,2,,3";
		
		//Test that empty value in list will trigger exception.
		assertThrows(RuntimeException.class, () -> { 
			sum.collect(in1);
		});
	}
	
	@Test
	public void testCollectNoCommas() {
		Summarizer sum = new Summarizer();
		String input = "1.2";

		//Test non-numeric data will trigger exception.
		assertThrows(RuntimeException.class, () -> { 
			sum.collect(input);
		});
	}
	
	@Test
	public void testSummarizeCollectionEmptyString() {
		Summarizer sum = new Summarizer();
		
		//Test null input string
		String in1 = "";
		Collection<Integer> input1 = sum.collect(in1);
		String result = sum.summarizeCollection(input1);
		assertEquals(result, "");
		
		//Test whitespace string
		String in2 = " ";
		Collection<Integer> input2 = sum.collect(in2);
		String result2 = sum.summarizeCollection(input2);
		assertEquals(result2, "");

		//Test newline string
		String in3 = "\n";
		Collection<Integer> input3 = sum.collect(in3);
		String result3 = sum.summarizeCollection(input3);
		assertEquals(result3, "");
	}
	
	@Test
	public void testSummarizeCollectionSingleNumber() {
		Summarizer sum = new Summarizer();
		
		String in1 = "1";
		Collection<Integer> input1 = sum.collect(in1);
		String result = sum.summarizeCollection(input1);
		assertEquals(result, "1");
	}
	
	@Test
	public void testSummarizeCollectionSimpleRange() {
		Summarizer sum = new Summarizer();
		
		String in1 = "1,2,3";
		Collection<Integer> input1 = sum.collect(in1);
		String result = sum.summarizeCollection(input1);
		assertEquals(result, "1-3");
	}
	
	@Test
	public void testSummarizeCollectionNoRange() {
		Summarizer sum = new Summarizer();
		
		//Test range assembling feature. It was assumed that it only
		//works when values in a range are 2 or more numbers apart.
		String in1 = "1,2,4,5,7,8,10,11";
		Collection<Integer> input1 = sum.collect(in1);
		String result = sum.summarizeCollection(input1);
		assertEquals(result, "1, 2, 4, 5, 7, 8, 10, 11");
	}
	
	@Test
	public void testSummarizeCollectionOrder() {
		Summarizer sum = new Summarizer();
		
		//Test ascending order
		String in1 = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
		Collection<Integer> input1 = sum.collect(in1);
		String result = sum.summarizeCollection(input1);
		assertEquals(result, "1, 3, 6-8, 12-15, 21-24, 31");
		
		//Test descending order
		String in2 = "31,24,23,22,21,15,14,13,12,8,7,6,3,1";
		Collection<Integer> input2 = sum.collect(in2);
		String result2 = sum.summarizeCollection(input2);
		assertEquals(result2, "1, 3, 6-8, 12-15, 21-24, 31");
		
		//Test mixed order
		String in3 = "1,3,6,7,8,12,13,31,24,23,22,21,15,14";
		Collection<Integer> input3 = sum.collect(in3);
		String result3 = sum.summarizeCollection(input3);
		assertEquals(result3, "1, 3, 6-8, 12-15, 21-24, 31");
		
		//Test with negatives and positives
		String in4 = "1,-3,-4,5,6,7,8";
		Collection<Integer> input4 = sum.collect(in4);
		String result4 = sum.summarizeCollection(input4);
		assertEquals(result4, "-4, -3, 1, 5-8");
	}

}
