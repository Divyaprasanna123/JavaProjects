import java.io.*;
import java.util.*;

public class SocialNetworkFriendSuggestion {

    static Map<String, Set<String>> graph = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        loadData();

        while (true) {
            System.out.println("\n===== SOCIAL NETWORK =====");
            System.out.println("1. Add User");
            System.out.println("2. Add Friendship");
            System.out.println("3. View Friends");
            System.out.println("4. Suggest Friends (Ranked)");
            System.out.println("5. Show Network (DFS)");
            System.out.println("6. Search User");
            System.out.println("7. Save & Exit");

            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1: addUser(); break;
                case 2: addFriendship(); break;
                case 3: viewFriends(); break;
                case 4: suggestFriends(); break;
                case 5: showNetworkDFS(); break;
                case 6: searchUser(); break;
                case 7: saveData(); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ===== ADD USER =====
    static void addUser() {
        System.out.print("Enter username: ");
        String user = sc.nextLine();

        graph.putIfAbsent(user, new HashSet<>());
        System.out.println("✅ User added!");
    }

    // ===== ADD FRIENDSHIP =====
    static void addFriendship() {
        System.out.print("Enter user1: ");
        String u1 = sc.nextLine();

        System.out.print("Enter user2: ");
        String u2 = sc.nextLine();

        graph.putIfAbsent(u1, new HashSet<>());
        graph.putIfAbsent(u2, new HashSet<>());

        if (graph.get(u1).contains(u2)) {
            System.out.println("Already friends!");
            return;
        }

        graph.get(u1).add(u2);
        graph.get(u2).add(u1);

        System.out.println("✅ Friendship added!");
    }

    // ===== VIEW FRIENDS =====
    static void viewFriends() {
        System.out.print("Enter user: ");
        String user = sc.nextLine();

        if (!graph.containsKey(user)) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("Friends of " + user + ": " + graph.get(user));
    }

    // ===== FRIEND SUGGESTION =====
    static void suggestFriends() {

        System.out.print("Enter user: ");
        String user = sc.nextLine();

        if (!graph.containsKey(user)) {
            System.out.println("User not found!");
            return;
        }

        Map<String, Integer> mutualCount = new HashMap<>();

        for (String friend : graph.get(user)) {
            for (String fof : graph.get(friend)) {

                if (!fof.equals(user) && !graph.get(user).contains(fof)) {
                    mutualCount.put(fof, mutualCount.getOrDefault(fof, 0) + 1);
                }
            }
        }

        if (mutualCount.isEmpty()) {
            System.out.println("No suggestions available.");
            return;
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(mutualCount.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("💡 Suggested Friends:");
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " (Mutual Friends: " + entry.getValue() + ")");
        }
    }

    // ===== DFS =====
    static void showNetworkDFS() {
        System.out.print("Enter starting user: ");
        String start = sc.nextLine();

        Set<String> visited = new HashSet<>();
        System.out.println("🌐 Network (DFS):");

        dfs(start, visited);
    }

    static void dfs(String user, Set<String> visited) {

        if (!graph.containsKey(user) || visited.contains(user)) return;

        visited.add(user);
        System.out.println(user);

        for (String friend : graph.get(user)) {
            dfs(friend, visited);
        }
    }

    // ===== SEARCH =====
    static void searchUser() {
        System.out.print("Enter username: ");
        String user = sc.nextLine();

        if (graph.containsKey(user)) {
            System.out.println("✅ User exists. Friends count: " + graph.get(user).size());
        } else {
            System.out.println("❌ User not found!");
        }
    }

    // ===== SAVE =====
    static void saveData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("network.dat"));
            oos.writeObject(graph);
            oos.close();
            System.out.println("Data saved!");
        } catch (Exception e) {
            System.out.println("Error saving!");
        }
    }

    // ===== LOAD =====
    static void loadData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("network.dat"));
            graph = (Map<String, Set<String>>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("No previous data.");
        }
    }
}