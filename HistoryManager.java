import java.util.ArrayList;
import java.util.List;

/**
 * Manages the calculation history for the calculator.
 */
public class HistoryManager {
    private final List<String> history;
    private static final int MAX_HISTORY = 50;

    public HistoryManager() {
        this.history = new ArrayList<>();
    }

    /**
     * Adds a calculation to the history.
     * @param expression The expression evaluated.
     * @param result The result of the evaluation.
     */
    public void addEntry(String expression, String result) {
        if (history.size() >= MAX_HISTORY) {
            history.remove(0);
        }
        history.add(expression + " = " + result);
    }

    /**
     * Gets all history entries.
     * @return List of history strings.
     */
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    /**
     * Clears all history.
     */
    public void clearHistory() {
        history.clear();
    }
}
