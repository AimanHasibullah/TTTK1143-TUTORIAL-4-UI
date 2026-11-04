public interface Measurable {
    /**
     * Computes the measure of the object.
     * @return the numerical measure (e.g. balance, value, height)
     */
    double getMeasure();

    /**
     * Returns the name or description of the object.
     * @return the name string
     */
    String getName();
}