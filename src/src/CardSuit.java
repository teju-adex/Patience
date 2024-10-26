public enum CardSuit {
    S("S"),
    H("H"),
    D("D"),
    C("C");

    private final String symbol;

    // Enum constructor
    CardSuit(String symbol) {
        this.symbol = symbol;
    }

    // Override toString to return the symbol
    @Override
    public String toString() {
        return this.symbol;
    }

    public String getKey() {
        return this.name(); // This returns the enum constant name (H, S, D, C)
    }
}
