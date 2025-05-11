import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class OrderPanel extends JFrame {
    private ArrayList<HashMap<String, String>> orders = new ArrayList<>();
    private static ArrayList<String> orderHistory = new ArrayList<>();
    private static final String HISTORY_FILE = "order_history.dat";

    public OrderPanel() {
        setTitle("BREWTOPIA - New Order");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 250), 0, getHeight(), new Color(70, 130, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);
        setContentPane(panel);

        JLabel lbl = new JLabel("Take Order", SwingConstants.CENTER);
        lbl.setBounds(200, 20, 300, 40);
        lbl.setFont(new Font("Serif", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        add(lbl);

        // Create JTextArea for menu and wrap it in a JScrollPane
        JTextArea menu = new JTextArea(CafeUtils.getMenuItems());
        menu.setFont(new Font("Arial", Font.PLAIN, 14));
        menu.setEditable(false);
        
        JScrollPane menuScrollPane = new JScrollPane(menu);
        menuScrollPane.setBounds(30, 80, 250, 350);
        menuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        menuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(menuScrollPane);

        JLabel itemLbl = new JLabel("Item:");
        itemLbl.setBounds(300, 100, 100, 30);
        itemLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        itemLbl.setForeground(Color.WHITE);
        add(itemLbl);

        JTextField itemField = new JTextField();
        itemField.setBounds(380, 100, 250, 30);
        itemField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(itemField);

        JLabel qtyLbl = new JLabel("Quantity:");
        qtyLbl.setBounds(300, 150, 100, 30);
        qtyLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        qtyLbl.setForeground(Color.WHITE);
        add(qtyLbl);

        JTextField qtyField = new JTextField("1");
        qtyField.setBounds(380, 150, 100, 30);
        qtyField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(qtyField);

        JButton addBtn = createStyledButton("Add to Order");
        addBtn.setBounds(380, 200, 140, 40);
        addBtn.addActionListener(e -> {
            try {
                String item = itemField.getText();
                int quantity = Integer.parseInt(qtyField.getText());
                if (item.isEmpty() || quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter valid item and quantity");
                    return;
                }
                double price = CafeUtils.getItemPrice(item);
                if (price == 0.0) {
                    JOptionPane.showMessageDialog(this, "Item not found");
                    return;
                }
                HashMap<String, String> order = new HashMap<>();
                order.put("item", item);
                order.put("quantity", String.valueOf(quantity));
                order.put("price", String.valueOf(price));
                orders.add(order);
                JOptionPane.showMessageDialog(this, "Item added to order.");
                itemField.setText("");
                qtyField.setText("1");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity");
            }
        });
        add(addBtn);

        JButton billBtn = createStyledButton("Generate Bill");
        billBtn.setBounds(380, 260, 140, 40);
        billBtn.addActionListener(e -> {
            double total = 0.0;
            StringBuilder summary = new StringBuilder("Order Summary:\n\n");
            for (HashMap<String, String> order : orders) {
                double price = Double.parseDouble(order.get("price"));
                int quantity = Integer.parseInt(order.get("quantity"));
                double subtotal = price * quantity;
                total += subtotal;
                summary.append(order.get("item"))
                       .append(" x").append(quantity)
                       .append(" - $").append(String.format("%.2f", subtotal))
                       .append("\n");
            }
            summary.append("\nTotal: $").append(String.format("%.2f", total));
            JOptionPane.showMessageDialog(this, summary.toString());
            orderHistory.add(summary.toString());
            saveHistory();
            orders.clear();
        });
        add(billBtn);

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 144, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    private static void saveHistory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HISTORY_FILE))) {
            oos.writeObject(orderHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<String> loadHistory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HISTORY_FILE))) {
            return (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}