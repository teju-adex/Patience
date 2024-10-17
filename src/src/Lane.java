import java.util.Stack;

public class Lane {

    private Stack<Card> uncovered = new Stack<>();
    private Stack<Card> covered = new Stack<>();

    public Stack<Card> GetCovered() {
        return covered;
    }

    public Stack<Card> GetUncovered() {
        return uncovered;
    }

    public void AddCoveredCard(Card card) {
        covered.push(card);
    }

    public void AddUncoveredCard(Card card) {
        uncovered.push(card);
    }

    public Card GetTopUncovered() {
        return uncovered.isEmpty() ? null : uncovered.peek();
    }
    public Card RemoveTopUncovered() {
        return uncovered.isEmpty() ? null : uncovered.pop();
    }

    public void CoveredToUncoveredTopCard() {
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

    public boolean hasCoveredCards() {
        return !covered.isEmpty();
    }

}
