package QuickCall;

import java.util.ArrayList;

public class FavoritesManager {
    private final ArrayList<String> favServices = new ArrayList<>();
    private final ArrayList<Worker> favWorkers  = new ArrayList<>();

    public void addService(String s) {
        if (!favServices.contains(s)) {
            favServices.add(s);
            System.out.println("⭐ Service \"" + s + "\" added to favorites.");
        } else System.out.println("⚠️ Service is already in favorites.");
    }

    public void addWorker(Worker w) {
        if (!favWorkers.contains(w)) {
            favWorkers.add(w);
            System.out.println("⭐ Worker \"" + w.getName() + "\" added to favorites.");
        } else System.out.println("⚠️ Worker is already in favorites.");
    }

    public void show() {
        System.out.println("\n📋 Favorites:");
        if (favServices.isEmpty() && favWorkers.isEmpty()) {
            System.out.println("(Empty)");
            return;
        }
        if (!favServices.isEmpty()) {
            System.out.println("Services:");
            for (String s : favServices) System.out.println("- " + s);
        }
        if (!favWorkers.isEmpty()) {
            System.out.println("Workers:");
            for (Worker w : favWorkers) System.out.println("- " + w.getName());
        }
    }
}