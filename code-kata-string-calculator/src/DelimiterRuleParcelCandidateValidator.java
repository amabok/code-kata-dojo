import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;

public class DelimiterRuleParcelCandidateValidator {

	public boolean validate(DelimiterDetails delimiterDetails, ParcelCandidate input) {
		if(delimiterDetails.delimiter != null) {
			String candidateValue = input.text;
			
			ParsePosition position = new ParsePosition(0);
			
			DecimalFormatSymbols sbls = new DecimalFormatSymbols();
			sbls.setDecimalSeparator('.');
			
			DecimalFormat df = new DecimalFormat();
			df.setDecimalFormatSymbols(sbls);
			
			df.parse(candidateValue,position).floatValue();
		
			if(position.getErrorIndex() != -1) {
				String message = String.format("'%s' expected but '%s' found at position %s.", delimiterDetails.delimiter, candidateValue.charAt(position.getErrorIndex()) , position.getErrorIndex() + input.startSeparatorIndex -1);
				throw new IllegalArgumentException(message);
			}else if(position.getIndex() < candidateValue.length() -1) {
				String message = String.format("'%s' expected but '%s' found at position %s.", delimiterDetails.delimiter, candidateValue.charAt(position.getIndex()), position.getIndex() + input.startSeparatorIndex + 1);
				throw new IllegalArgumentException(message);
			}
		}
		return true;
	}
}
