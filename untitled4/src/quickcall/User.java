package quickcall;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {

    private final String name;
    private final String phone;

    private final SearchHistoryManager history = new SearchHistoryManager();
    private final FavoritesManager fav = new FavoritesManager();

    private final List<Worker> bookings = new ArrayList<>();

    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void addSearch(String term) {
        history.add(term);
    }

    public void showHistory() {
        history.show();
    }

    public FavoritesManager fav() {
        return fav;
    }

    public void addBooking(Worker w) {
        bookings.add(w);
    }

    public boolean hasBooked(Worker w) {
        return bookings.contains(w);
    }
}
