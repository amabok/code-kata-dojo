
public class ParcelCandidate {
	public String text;
	public String startSeparator;
	public int startSeparatorIndex;
	public String endSeparator;
	public int endSeparatorIndex;

	public String toString() {
	
		return String.format("Parcel text: '%s' StartSeparator: '%s' EndSeparator: '%s' StartSeparatorIndex: %s EndSeparatorIndex: %s",
							text,
							sanitizeSeparatorForPrint(startSeparator),
							sanitizeSeparatorForPrint(endSeparator), 
							startSeparatorIndex,
							endSeparatorIndex);
	}
	
	private String sanitizeSeparatorForPrint(String separator) {
		if(separator!=null && separator.contains("\n")) {
			return "<new line>";
		}
		
		return separator;
	}
}
