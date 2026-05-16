import java.io.*;
import java.util.*;

/**
 * RecommendationSystem.java
 * Suggests products to users based on similar users' preferences
 * Uses User-Based Collaborative Filtering
 */
public class RecommendationSystem {

    // Stores all ratings: userId -> (productId -> rating)
    static Map<Integer, Map<String, Integer>> userRatings = new HashMap<>();

    public static void main(String[] args) throws Exception {

        System.out.println("====================================");
        System.out.println("   🛍️  Product Recommendation System");
        System.out.println("====================================\n");

        // Step 1: Load data from CSV file
        loadRatings("ratings.csv");
        System.out.println("✅ Data loaded successfully!\n");

        // Step 2: Show all users and their rated products
        System.out.println("📊 User Ratings Data:");
        System.out.println("------------------------------------");
        for (Map.Entry<Integer, Map<String, Integer>> entry : userRatings.entrySet()) {
            System.out.println("User " + entry.getKey() + " rated: " + entry.getValue());
        }
        System.out.println();

        // Step 3: Get recommendations for each user
        System.out.println("🎯 Recommendations:");
        System.out.println("------------------------------------");
        for (int userId : userRatings.keySet()) {
            List<String> recommendations = getRecommendations(userId);
            System.out.println("User " + userId + " → Recommended: " + recommendations);
        }

        System.out.println("\n====================================");
        System.out.println("✅ Recommendation Engine Complete!");
        System.out.println("====================================");
    }

    /**
     * Loads ratings from CSV file into userRatings map
     */
    static void loadRatings(String filename) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        boolean firstLine = true;

        while ((line = reader.readLine()) != null) {
            // Skip header line
            if (firstLine) {
                firstLine = false;
                continue;
            }

            // Split each line by comma
            String[] parts = line.split(",");
            int userId = Integer.parseInt(parts[0].trim());
            String productId = parts[1].trim();
            int rating = Integer.parseInt(parts[2].trim());

            // Add to userRatings map
            userRatings.putIfAbsent(userId, new HashMap<>());
            userRatings.get(userId).put(productId, rating);
        }
        reader.close();
    }

    /**
     * Finds recommendations for a specific user
     * by looking at what similar users liked
     */
    static List<String> getRecommendations(int targetUser) {

        // Products the target user already rated
        Map<String, Integer> targetProducts = userRatings.get(targetUser);

        // Map to count how many similar users liked each product
        Map<String, Integer> productScore = new HashMap<>();

        // Compare target user with every other user
        for (Map.Entry<Integer, Map<String, Integer>> entry : userRatings.entrySet()) {
            int otherUser = entry.getKey();
            Map<String, Integer> otherProducts = entry.getValue();

            // Skip comparing with themselves
            if (otherUser == targetUser) continue;

            // Count how many products they have in common
            int commonProducts = 0;
            for (String product : targetProducts.keySet()) {
                if (otherProducts.containsKey(product)) {
                    commonProducts++;
                }
            }

            // If they have at least 1 product in common = similar user
            if (commonProducts >= 1) {
                // Find products other user rated that target user hasn't seen
                for (Map.Entry<String, Integer> productEntry : otherProducts.entrySet()) {
                    String product = productEntry.getKey();
                    int rating = productEntry.getValue();

                    // Only recommend if target user hasn't rated it
                    // and rating is 3 or above (good product)
                    if (!targetProducts.containsKey(product) && rating >= 3) {
                        productScore.put(product,
                                productScore.getOrDefault(product, 0) + rating);
                    }
                }
            }
        }

        // Sort products by score (highest first)
        List<Map.Entry<String, Integer>> sortedProducts =
                new ArrayList<>(productScore.entrySet());
        sortedProducts.sort((a, b) -> b.getValue() - a.getValue());

        // Return top 3 recommendations
        List<String> recommendations = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedProducts) {
            if (count >= 3) break;
            recommendations.add(entry.getKey() + " (score: " + entry.getValue() + ")");
            count++;
        }

        // If no recommendations found
        if (recommendations.isEmpty()) {
            recommendations.add("No new recommendations available");
        }

        return recommendations;
    }
}