

/**
 * A dimension is a collection of three values: feet, inches, and eighths of an
 * inch. The purpose of this class is to store these values and convert them to
 * and from different formats.
 * 
 * @author lsg
 *
 */
public class Dimension implements Comparable<Dimension> {
	private int feet, inches, eighths;

	/**
	 * Creates a dimension object that stores values as separate variables: feet,
	 * inches, and eighths of inches.
	 * 
	 * @param dimension
	 */
	public Dimension(float dimension) {
		// Calculate the number of feet.
		this.feet = (int) Math.floor((double) dimension / 12);

		// Calculate the number of inches.
		this.inches = (int) Math.floor((double) dimension % 12);

		// Determine the fractional part and round it to the nearest eighth of an inch.
		double fractional = (double) dimension - Math.floor((double) dimension);
		this.eighths = (int) Math.round(fractional * 8);
	}

	/**
	 * Create a new dimension, specifying feet, inches, and eighths of an inch.
	 * 
	 * @param feet
	 *            Feet.
	 * @param inches
	 *            Inches.
	 * @param eighths
	 *            Eighths of an inch.
	 */
	public Dimension(int feet, int inches, int eighths) {
		this.feet = feet;
		this.inches = inches;
		this.eighths = eighths;
	}

	public int getFeet() {
		return feet;
	}

	public void setFeet(int feet) {
		this.feet = feet;
	}

	public int getInches() {
		return inches;
	}

	public void setInches(int inches) {
		this.inches = inches;
	}

	public int getEighths() {
		return eighths;
	}

	public void setEighths(int eighths) {
		this.eighths = eighths;
	}

	/**
	 * Converts this dimension to inches.
	 * 
	 * @return The dimension value in inches.
	 */
	public float toInches() {
		return (float) (this.feet * 12) + (this.inches) + ((float) this.eighths / 8f);
	}
	
	public int toEighths() {
		return (int) (this.toInches() * 8.0f);
	}

	@Override
	/**
	 * Converts this dimension into a readable string. Formatted examples:
	 * 
	 * 8' 4 3/8", 5 1/2", 0', 7'
	 */
	public String toString() {
		String result = "";

		if (this.toInches() == 0) {
			// If the entire dimension is 0, display 0 feet.
			result = "0'";
		} else {
			// Display the feet part of the measurement if it is nonzero.
			if (this.feet > 0) {
				result += String.format("%d'", this.feet);
			}

			// Display the inches part of the measurement if it is nonzero.
			if (this.inches > 0) {
				result += String.format("%d", this.inches);
				
				// Display the fractional part of the measurement if it is nonzero.
				if (this.eighths > 0) {
					if (this.eighths % 4 == 0) {
						// If it can be reduced to halves, display halves.
						result += String.format(" %d/2\"", this.eighths / 4);
					} else if (this.eighths % 2 == 0) {
						// If it can be reduced to fourths, display fourths.
						result += String.format(" %d/4\"", this.eighths / 2);
					} else {
						// If it cannot be reduced, display eighths.
						result += String.format(" %d/8\"", this.eighths);
					}
				} else {
					result += "\"";
				}
			}
		}

		return result;
	}

	@Override
	public int compareTo(Dimension dimension2) {
		// Find the difference between the two dimensions.
		float difference = this.toInches() - dimension2.toInches();

		// If the difference is negative, return -1, and vice-versa.
		return (int) (difference / Math.abs(difference));
	}
	
	public Dimension scale(float scalar) {
		return new Dimension(this.toInches() * scalar);
	}
	
	// Divides the dimension by another dimension.
	public float divideBy(Dimension d2) {
		return this.toInches() / d2.toInches();
	}

	/**
	 * Converts a fraction string in the format <code>numer/denom</code> to an
	 * integer representing eighths of an inch.
	 * 
	 * @param fraction
	 *            A string in the format <code>numerator/denominator</code>
	 * @return The number of eighths of an inch, or 0 if the fraction is improperly
	 *         formatted (e.g. no slash)
	 * @throws NumberFormatException
	 *             when the numerator or denominator are not proper integers.
	 */
	public static int convertFractionToEighths(String fraction) throws NumberFormatException {
		String[] strings = fraction.split("/");
		int eighths = 0;

		// Ensure that the fraction was formatted correctly.
		if (strings.length == 2) {
			// Parse the components. These may throw number format exceptions.
			int numerator = Integer.parseInt(strings[0]);
			int denominator = Integer.parseInt(strings[1]);

			// Convert the fraction into a double value.
			double fractional = (double) numerator / (double) denominator;

			// Round the fractional part to the nearest eighth.
			eighths = (int) Math.round(fractional * 8);
		}

		return eighths;
	}
	
	// subtracts d2 from d1.
	public static Dimension difference(Dimension d1, Dimension d2) {
		return new Dimension(d1.toInches() - d2.toInches());
	}
}
