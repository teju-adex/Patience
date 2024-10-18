public enum CardSuit {
    S("\u2660"),   // Unicode for Spades
    H("\u2665"),   // Unicode for Hearts
    D("\u2666"), // Unicode for Diamonds
    C("\u2663");

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
