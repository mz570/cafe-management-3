import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class CafeUtils {
    private static ArrayList<HashMap<String, String>> menuItems = new ArrayList<>();
    private static final String MENU_FILE = "menu.dat";

    static {
        loadMenu();
    }

    public static void addMenuItem(String name, String price, String category) {
        try {
            Double.parseDouble(price);
            HashMap<String, String> item = new HashMap<>();
            item.put("name", name);
            item.put("price", price);
            item.put("category", category);
            menuItems.add(item);
            saveMenu();
} catch (NumberFormatException e) {
    throw new IllegalArgumentException("Invalid price format");


}
    }

    public static String getMenuItems() {
        if (menuItems.isEmpty()) return "No items yet.";
        StringBuilder sb = new StringBuilder();
        String currentCategory = "";
        for (HashMap<String, String> item : menuItems) {
            if (!item.get("category").equals(currentCategory)) {
                currentCategory = item.get("category");
                sb.append("\n=== ").append(currentCategory).append(" ===\n");
            }
            sb.append(item.get("name")).append(" - $").append(item.get("price")).append("\n");
        }
        return sb.toString();
    }

    public static double getItemPrice(String name) {
        for (HashMap<String, String> item : menuItems) {
            if (item.get("name").equalsIgnoreCase(name)) {
                return Double.parseDouble(item.get("price"));
            }
        }
        return 0.0;
    }

    private static void saveMenu() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MENU_FILE))) {
            oos.writeObject(menuItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadMenu() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MENU_FILE))) {
            menuItems = (ArrayList<HashMap<String, String>>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, start with empty menu
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}