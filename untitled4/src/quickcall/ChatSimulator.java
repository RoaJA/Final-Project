package quickcall;

import java.util.Scanner;

public class ChatSimulator {

    public static void startChat(Scanner sc, Worker w) {
        System.out.println("💬 Starting chat with " + w.getName() + " (" + w.getServiceType() + ")...");
        System.out.println("(Type 'exit' to end chat)");

        while (true) {
            System.out.print("You: ");
            String msg = sc.nextLine().trim();
            if (msg.equalsIgnoreCase("exit")) {
                System.out.println("Chat ended.");
                break;
            }
            System.out.println(w.getName() + ": I received your message \"" + msg + "\".");
        }
    }
}
