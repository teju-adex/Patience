import java.util.Stack;

// Lane class has 2 stacks for uncovered and covered cards in each lane.
public class Lane {

    private Stack<Card> uncovered = new Stack<>();
    private Stack<Card> covered = new Stack<>();

    public Stack<Card> getCovered() {
        return covered;
    }

    public Stack<Card> getUncovered() {
        return uncovered;
    }

    public void addCoveredCard(Card card) {
        covered.push(card);
    }

    public void addUncoveredCard(Card card) {
        uncovered.push(card);
    }

    public Card getTopUncovered() {
        return uncovered.isEmpty() ? null : uncovered.peek();
    }
    public Card removeTopUncovered() {
        return uncovered.isEmpty() ? null : uncovered.pop();
    }

    public void coveredToUncoveredTopCard() {
        if(!covered.isEmpty()) {
            uncovered.push(covered.pop());
        }
    }

    public int size() {
        return covered.size() + uncovered.size();
    }

    public boolean hasUncoveredCards() {
        return !uncovered.isEmpty();
    }

}
