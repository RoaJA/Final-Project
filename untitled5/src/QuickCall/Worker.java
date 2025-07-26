package QuickCall;

import java.util.ArrayList;
import java.util.List;

public class Worker {

    private final String name;
    private final String phone;
    private final String serviceType;
    private final double price;
    private final int experienceYears;

    private final List<Integer> ratings = new ArrayList<>();
    private double averageRating = 0.0;

    private boolean isNewWorker;

    public Worker(String name, String phone, String serviceType, double price, int experienceYears, boolean isNewWorker) {
        this.name = name;
        this.phone = phone;
        this.serviceType = serviceType;
        this.price = price;
        this.experienceYears = experienceYears;
        this.isNewWorker = isNewWorker;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getServiceType() {
        return serviceType;
    }

    public double getPrice() {
        return price;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public boolean isNewWorker() {
        return isNewWorker;
    }

    public void setNewWorker(boolean newWorker) {
        isNewWorker = newWorker;
    }

    public void addRating(int rating) {
        ratings.add(rating);
        updateAverageRating();
    }

    private void updateAverageRating() {
        int sum = 0;
        for (int r : ratings) {
            sum += r;
        }
        averageRating = ratings.isEmpty() ? 0 : (double) sum / ratings.size();
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void showRatings() {
        System.out.println("Ratings for " + name + ":");
        if (ratings.isEmpty()) {
            System.out.println("⚠ No ratings yet.");
        } else {
            System.out.printf("⭐ Average rating: %.2f based on %d ratings.%n", averageRating, ratings.size());
        }
    }

    @Override
    public String toString() {
        String newBadge = isNewWorker ? " 🆕" : "";
        String ratingDisplay = ratings.isEmpty() ? "(No ratings yet)" : String.format("(⭐ %.2f)", averageRating);
        return String.format("%s%s - %s - Price: ₪%.2f - Experience: %d years %s", name, newBadge, serviceType, price, experienceYears, ratingDisplay);
    }
}
