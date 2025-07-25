package quickcall;

import java.util.LinkedList;

public class SearchHistoryManager {

    private static final int MAX = 5;
    private final LinkedList<String> history = new LinkedList<>();

    public void add(String term) {
        // لا تضيف مصطلح جديد إذا كان موجود أصلاً، لكن اجعله يظهر كأحدث بحث
        if (history.contains(term)) {
            history.remove(term);
        }
        if (history.size() == MAX) {
            history.removeFirst();
        }
        history.add(term);
    }

    public void show() {
        System.out.println("🕒 Recent searches:");
        if (history.isEmpty()) {
            System.out.println("No search history.");
        } else {
            for (String s : history) {
                System.out.println(" - " + s);
            }
        }
    }
}
