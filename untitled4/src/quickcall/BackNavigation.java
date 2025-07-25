package quickcall;

import java.util.Stack;

public class BackNavigation {
    private Stack<String> history;
// constructor
    public BackNavigation() {
        history = new Stack<>();
    }

    public void addAction(String action) {
        history.push(action);
    }

    public String goBack() {
        if (history.isEmpty()) return null;
        history.pop(); // pop  current
        if (history.isEmpty()) return null;
        return history.peek();
    }
}
