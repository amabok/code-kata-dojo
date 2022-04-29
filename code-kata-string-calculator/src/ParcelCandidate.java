
public class ParcelCandidate {
	public String text;
	public String startSeparator;
	public int startSeparatorIndex;
	public String endSeparator;
	public int endSeparatorIndex;

	
	public ParcelCandidate(String text, String startSeparator, int startSeparatorIndex, String endSeparator, int endSeparatorIndex) {
		this.text = text;
		this.startSeparator = startSeparator;
		this.startSeparatorIndex = startSeparatorIndex;
		this.endSeparator = endSeparator;
		this.endSeparatorIndex = endSeparatorIndex;
	}
	
	public boolean equals(Object obj) {
		ParcelCandidate other = (ParcelCandidate) obj;
		return this.text.equals(other.text) &&
				(this.startSeparator == other.startSeparator || this.startSeparator.equals(other.startSeparator)) &&
				this.startSeparatorIndex == other.startSeparatorIndex &&
				(this.endSeparator == other.endSeparator || this.endSeparator.equals(other.endSeparator)) &&
				this.endSeparatorIndex == other.endSeparatorIndex;
	}
	
	// Helper methods
	public String toString() {
		return String.format("Parcel text: '%s' StartSeparator: '%s' StartSeparatorIndex: %s EndSeparator: '%s' EndSeparatorIndex: %s",
							text,
							sanitizeSeparatorForPrint(startSeparator),
							startSeparatorIndex,
							sanitizeSeparatorForPrint(endSeparator), 
							endSeparatorIndex);
	}
	
	private String sanitizeSeparatorForPrint(String separator) {
		if(separator!=null && separator.contains("\n")) {
			return "<new line>";
		}
		
		return separator;
	}
}
