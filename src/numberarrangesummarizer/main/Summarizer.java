package numberarrangesummarizer.main;

import java.util.Collection;
import java.util.TreeSet;

/*
 * @author Keamogetswe Mphakalasi
 * 
 * Implementation class of the NumberRangeSummarizer interface.
 * 
 */
public class Summarizer implements NumberRangeSummarizer {
	
	@Override
	public Collection<Integer> collect(String input) { //collect the input
		
		TreeSet<Integer> numbers = new TreeSet<Integer>();
		input = input.replaceAll("\\s", "");
		String[] range = new String[0];
		
		if (input.length() >= 1) {
			range = input.split("[,]");
		}
		
		int n = 0;
		
		for (String num : range) {
			try {
				n = Integer.parseInt(num);
				numbers.add(n);
			} catch (NumberFormatException e) {
				System.err.println("Summarizer: Failed to convert to integer.");
                throw new RuntimeException("Invalid number format: " + num, e);
			}	
		}
		return numbers;
	}
	
	@Override
	public String summarizeCollection(Collection<Integer> input) { //get the summarized string
		
		String collection = "";
		Integer[] in = input.toArray(new Integer[0]);
		
		int rangeEnd = 0;
		int rangeMin = 2;
		
		 if (in.length > 0) {
			collection = String.valueOf(in[0]);
			
			for (int i = 1; i < input.size(); i++) {
				//Try to extend potential interval
				if (in[i] - in[i-1] == 1) {
					rangeEnd = getRange(i, in);
					
					if (rangeEnd - (i-1) < rangeMin) {
						collection += ", " + in[rangeEnd];
					} else {
						collection += "-" + in[rangeEnd];
					}
					
					//proceed from end of range
					i = rangeEnd;
				} else {
					collection += ", " + in[i];
				}
			}
		}
		
		return collection;
	}
	
	private int getRange(int current, Integer[] in) {
		int base = current - 1;
		++current;
		int count = 1;
		
		while (current < in.length) {
			++count;
			if (in[current] - in[base] != count) {
				break;
			}
			++current;
		}
		
		return current - 1;
	}
}
