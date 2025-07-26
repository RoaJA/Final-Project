package QuickCall;

import java.util.*;    // Scanner, ArrayList, List, Locale, Comparator
import java.io.*;      // File, PrintWriter, IOException
public class Main {


    private static final Scanner         sc      = new Scanner(System.in);
    private static final ArrayList<Worker> workers = new ArrayList<>();
    private static final ArrayList<User>   users   = new ArrayList<>();
    private static final String SAVE_FILE = "saved_user.txt";
    private static User currentUser = null;

    // Flags for search and sort
    public static class SearchSortFlags {
        public static final int SORT_BY_PRICE      = 1; // 0001
        public static final int SORT_BY_EXPERIENCE = 2; // 0010
        public static final int FILTER_BY_NAME     = 4; // 0100
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        System.out.println("👋 Welcome to Quick Call!");
        System.out.println("📍 This service is for Jerusalem residents only.");

        initSampleData();

        boolean loaded = loadUser();

        if (!loaded) {
            // لو ما تم تحميل حساب محفوظ أو رفض المستخدم الحساب المحفوظ
            String userName;
            while (true) {
                System.out.print("Enter your name: ");
                userName = sc.nextLine().trim();
                if (userName.matches("[a-zA-Z ]+")) break;
                System.out.println("❌ Name must contain letters only.");
            }

            System.out.print("Enter your phone number: ");
            String phone = sc.nextLine().trim();
            while (!phone.matches("\\d{10}")) {
                System.out.println("❌ Invalid phone number. Please enter exactly 10 digits.");
                System.out.print("Enter your phone number: ");
                phone = sc.nextLine().trim();
            }
            currentUser = new User(userName, phone);
            users.add(currentUser);

            System.out.print("Save this account for future use? 1-Yes / 2-No: ");
            if (sc.nextLine().trim().equals("1")) {
                try (PrintWriter out = new PrintWriter(SAVE_FILE)) {
                    out.println(userName);
                    out.println(phone);
                    System.out.println("💾 Account saved successfully.");
                } catch (IOException e) {
                    System.out.println("⚠ Error saving account.");
                }
            }
        }

        while (true) {
            System.out.println("\n1-Worker 🛠  2-User 👤  3-Exit ❌");
            String choice = InputValidator.promptNonEmpty(sc, "Choose: ");
            switch (choice) {
                case "1" -> registerWorker();
                case "2" -> userFlow();
                case "3" -> {
                    System.out.println("👋 Goodbye!");
                    return;
                }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    private static boolean loadUser() {
        File f = new File(SAVE_FILE);
        if (!f.exists()) return false;

        try (Scanner file = new Scanner(f)) {
            String name  = file.nextLine();
            String phone = file.nextLine();
            System.out.printf("\nSaved account found for %s. Use it? 1-Yes / 2-No: ", name);
            if (sc.nextLine().trim().equals("1")) {
                currentUser = new User(name, phone);
                users.add(currentUser);
                return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    private static void registerWorker() {
        System.out.println("\n🛠 Register a new worker");
        String  n   = InputValidator.promptNonEmpty(sc, "Name: ");
        String  p   = InputValidator.promptPhone10(sc, "Phone (10 digits): ");

        List<String> serviceTypes = getDistinctServices();

        System.out.println("Choose a service type:");
        for (int i = 0; i < serviceTypes.size(); i++) {
            System.out.println((i + 1) + ". " + serviceTypes.get(i));
        }
        System.out.println((serviceTypes.size() + 1) + ". Another service");

        int choice;
        while (true) {
            choice = InputValidator.promptInt(sc, "Enter your choice (number): ", 1, serviceTypes.size() + 1);
            if (choice >= 1 && choice <= serviceTypes.size() + 1) break;
            System.out.println("❌ Invalid choice. Try again.");
        }

        String s;
        if (choice == serviceTypes.size() + 1) {
            s = InputValidator.promptNonEmpty(sc, "Enter the new service type: ");
        } else {
            s = serviceTypes.get(choice - 1);
        }

        double  pr  = InputValidator.promptDouble(sc, "Price (₪): ", 1, 10_000);
        int     exp = InputValidator.promptInt(sc, "Years of experience: ", 0, 100);

        // New workers do NOT have rating by default (false)
        workers.add(new Worker(n, p, s, pr, exp, true));  // isNewWorker = true for "new" emoji
        System.out.println("✅ Worker added.");
    }

    private static void userFlow() {
        if (currentUser == null) {
            System.out.println("\n👤 Register as a user");
            String n;
            while (true) {
                n = InputValidator.promptNonEmpty(sc, "Name: ");
                if (n.matches("[a-zA-Z ]+")) break;
                System.out.println("❌ Name must contain letters only.");
            }
            String p = InputValidator.promptPhone10(sc, "Phone (10 digits): ");
            currentUser = new User(n, p);
            users.add(currentUser);

            System.out.print("Save this account for future use? 1-Yes / 2-No: ");
            if (sc.nextLine().trim().equals("1")) {
                try (PrintWriter out = new PrintWriter(SAVE_FILE)) {
                    out.println(n);
                    out.println(p);
                    System.out.println("💾 Account saved successfully.");
                } catch (IOException e) {
                    System.out.println("⚠ Error saving account.");
                }
            }
        }

        while (true) {
            System.out.println("""
                \n1-View Services
                2-Search by Service
                3-Search by Name
                4-Sort Workers
                5-Add to Favorites
                6-Show Favorites
                7-Book Worker
                8-Rate Worker
                9-Show Worker Ratings
                10-Search History
                11-Back""");

            String op = InputValidator.promptNonEmpty(sc, "Choose: ");
            switch (op) {
                case "1"  -> showServices();
                case "2"  -> searchByService(currentUser);
                case "3"  -> searchByName(currentUser);
                case "4"  -> sortWorkers();
                case "5"  -> addToFav(currentUser);
                case "6"  -> currentUser.fav().show();
                case "7"  -> book();
                case "8"  -> rate();
                case "9"  -> showRatings();
                case "10" -> currentUser.showHistory();
                case "11" -> { return; }
                default   -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    private static void showServices() {
        List<String> services = getDistinctServices();
        System.out.println("\n🛠 Available Services:");
        for (int i = 0; i < services.size(); i++)
            System.out.printf(Locale.US, "%d- %s%n", i + 1, services.get(i));
    }

    private static List<String> getDistinctServices() {
        return workers.stream()
                .map(Worker::getServiceType)
                .distinct()
                .toList();
    }

    private static void showWorkersNumbered(List<Worker> list) {
        for (int i = 0; i < list.size(); i++)
            System.out.printf(Locale.US, "%d- %s%n", i + 1, list.get(i));
    }

    private static void chooseWorkerAction(User u, List<Worker> list) {

        showWorkersNumbered(list);

        int act = InputValidator.promptInt(sc, """
            Choose an action:
            1-Book
            2-Add to Favorites
            3-Rate
            4-Back
            > """, 1, 4);

        if (act == 4) return;

        int idx = InputValidator.promptInt(sc, "Choose worker number: ", 1, list.size());
        Worker w = list.get(idx - 1);

        switch (act) {
            case 1 -> {
                ChatSimulator.startChat(sc, w);
                u.addBooking(w);
            }
            case 2 -> u.fav().addWorker(w);
            case 3 -> {
                if (!u.hasBooked(w)) {
                    System.out.println("⚠ You must book the worker before rating.");
                    break;
                }
                int r = InputValidator.promptInt(sc, "Rating (1-5): ", 1, 5);
                w.addRating(r);
            }
        }
    }

    private static List<Worker> searchAndSortWorkersWithFlags(int flags, String nameFilter) {
        List<Worker> result = new ArrayList<>(workers);

        if ((flags & SearchSortFlags.FILTER_BY_NAME) != 0 && nameFilter != null && !nameFilter.isEmpty()) {
            result = result.stream()
                    .filter(w -> w.getName().equalsIgnoreCase(nameFilter))
                    .toList();
        }

        if ((flags & SearchSortFlags.SORT_BY_PRICE) != 0) {
            result.sort(Comparator.comparingDouble(Worker::getPrice));
        }

        if ((flags & SearchSortFlags.SORT_BY_EXPERIENCE) != 0) {
            result.sort((a, b) -> Integer.compare(b.getExperienceYears(), a.getExperienceYears()));
        }

        return result;
    }

    private static void searchByService(User u) {
        List<String> list = getDistinctServices();
        showServices();
        int idx = InputValidator.promptInt(sc, "Choose a service: ", 1, list.size());
        String service = list.get(idx - 1);

        u.addSearch("Service: " + service);

        List<Worker> filtered = workers.stream()
                .filter(w -> w.getServiceType().equalsIgnoreCase(service))
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("⚠ No matching workers found.");
            return;
        }
        chooseWorkerAction(u, filtered);
    }

    private static void searchByName(User u) {
        String n = InputValidator.promptNonEmpty(sc, "Worker name: ");
        u.addSearch("Name: " + n);

        int flags = SearchSortFlags.FILTER_BY_NAME;
        List<Worker> filtered = searchAndSortWorkersWithFlags(flags, n);

        if (filtered.isEmpty())
            System.out.println("⚠ Not found.");
        else
            chooseWorkerAction(u, filtered);
    }

    private static void sortWorkers() {
        System.out.println("\nSort workers by service separately:");
        List<String> services = getDistinctServices();

        System.out.println("Sort by: 1-Price | 2-Experience");
        String c = InputValidator.promptNonEmpty(sc, "Choose sorting criteria: ");

        int flags = 0;
        if (c.equals("1")) flags = SearchSortFlags.SORT_BY_PRICE;
        else if (c.equals("2")) flags = SearchSortFlags.SORT_BY_EXPERIENCE;
        else {
            System.out.println("⚠ Invalid option.");
            return;
        }

        for (String service : services) {
            System.out.println("\n🔧 Service: " + service);
            List<Worker> filtered = workers.stream()
                    .filter(w -> w.getServiceType().equalsIgnoreCase(service))
                    .toList();

            filtered = sortListWithFlags(filtered, flags);

            filtered.forEach(System.out::println);
        }
    }

    private static List<Worker> sortListWithFlags(List<Worker> list, int flags) {
        List<Worker> sorted = new ArrayList<>(list);

        if ((flags & SearchSortFlags.SORT_BY_PRICE) != 0) {
            sorted.sort(Comparator.comparingDouble(Worker::getPrice));
        }

        if ((flags & SearchSortFlags.SORT_BY_EXPERIENCE) != 0) {
            sorted.sort((a, b) -> Integer.compare(b.getExperienceYears(), a.getExperienceYears()));
        }

        return sorted;
    }

    private static void addToFav(User u) {
        String t = InputValidator.promptNonEmpty(sc, "Add: 1-Service | 2-Worker: ");
        if (t.equals("1")) {
            String s = InputValidator.promptNonEmpty(sc, "Service name: ");
            u.fav().addService(s);
        } else if (t.equals("2")) {
            String n = InputValidator.promptNonEmpty(sc, "Worker name: ");
            workers.stream()
                    .filter(w -> w.getName().equalsIgnoreCase(n))
                    .findFirst()
                    .ifPresentOrElse(u.fav()::addWorker,
                            () -> System.out.println("⚠ Not found."));
        } else System.out.println("⚠ Invalid option.");
    }

    private static void book() {
        String n = InputValidator.promptNonEmpty(sc, "Worker to book: ");
        workers.stream()
                .filter(w -> w.getName().equalsIgnoreCase(n))
                .findFirst()
                .ifPresentOrElse(w -> {
                    ChatSimulator.startChat(sc, w);
                    currentUser.addBooking(w);
                }, () -> System.out.println("⚠ Not found."));
    }

    private static void rate() {
        String n = InputValidator.promptNonEmpty(sc, "Worker to rate: ");
        workers.stream()
                .filter(w -> w.getName().equalsIgnoreCase(n))
                .findFirst()
                .ifPresentOrElse(w -> {
                    if (!currentUser.hasBooked(w)) {
                        System.out.println("⚠ You must book the worker before rating.");
                        return;
                    }
                    int r = InputValidator.promptInt(sc, "Rating (1-5): ", 1, 5);
                    w.addRating(r);
                }, () -> System.out.println("⚠ Not found."));
    }

    private static void showRatings() {
        String n = InputValidator.promptNonEmpty(sc, "Worker name: ");
        workers.stream()
                .filter(w -> w.getName().equalsIgnoreCase(n))
                .findFirst()
                .ifPresentOrElse(Worker::showRatings,
                        () -> System.out.println("⚠ Not found."));
    }

    private static void initSampleData() {
        // Adding initial workers with default rating = 5 (for example)
        workers.addAll(List.of(
                new Worker("Yoav",   "0501234567", "Electrician", 150.0, 5, true),
                new Worker("Daniel", "0509999999", "Electrician", 130.0, 4, true),
                new Worker("Gadi",   "0511111111", "Electrician", 140.0, 6, true),
                new Worker("Liron",  "0533333333", "Electrician", 200.0, 7, true),

                new Worker("Noam",   "0544444444", "Mechanic", 190.0, 6, true),
                new Worker("Alkanah",   "0555555555", "Mechanic", 180.0, 5, true),
                new Worker("Lior",   "0566666666", "Mechanic", 170.0, 4, true),
                new Worker("Gilad",  "0577777777", "Mechanic", 160.0, 3, true),

                new Worker("Shira",  "0588888888", "Cleaner", 100.0, 3, true),
                new Worker("Tamar",  "0599999999", "Cleaner", 110.0, 4, true),
                new Worker("Yael",   "0522222222", "Cleaner",  95.0, 2, true),
                new Worker("Hadas",  "0523333333", "Cleaner", 105.0, 5, true),

                new Worker("Itay",   "0524444444", "Carpenter", 170.0, 4, true),
                new Worker("Bar",    "0525555555", "Carpenter", 180.0, 6, true),
                new Worker("Alon",   "0526666666", "Carpenter", 175.0, 5, true),
                new Worker("Gal",    "0527777777", "Carpenter", 165.0, 3, true)
        ));
    }
}


