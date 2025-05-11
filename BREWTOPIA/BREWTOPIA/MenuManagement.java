import javax.swing.*;
import java.awt.*;

public class MenuManagement extends JFrame {
    public MenuManagement() {
        setTitle("BREWTOPIA - Menu Management");
        setSize(500, 500);
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
        panel.setPreferredSize(new Dimension(480, 600)); // Make panel taller for scrolling

        JLabel lbl = new JLabel("Add Menu Item", SwingConstants.CENTER);
        lbl.setBounds(100, 30, 300, 40);
        lbl.setFont(new Font("Serif", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);

        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setBounds(80, 100, 100, 30);
        nameLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLbl.setForeground(Color.WHITE);
        panel.add(nameLbl);

        JTextField nameField = new JTextField();
        nameField.setBounds(180, 100, 200, 30);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(nameField);

        JLabel priceLbl = new JLabel("Price:");
        priceLbl.setBounds(80, 150, 100, 30);
        priceLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLbl.setForeground(Color.WHITE);
        panel.add(priceLbl);

        JTextField priceField = new JTextField();
        priceField.setBounds(180, 150, 200, 30);
        priceField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(priceField);

        JLabel catLbl = new JLabel("Category:");
        catLbl.setBounds(80, 200, 100, 30);
        catLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        catLbl.setForeground(Color.WHITE);
        panel.add(catLbl);

        String[] categories = {"Drinks", "Food", "Desserts"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        categoryBox.setBounds(180, 200, 200, 30);
        categoryBox.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(categoryBox);

        JButton addBtn = createStyledButton("Add Item");
        addBtn.setBounds(180, 270, 120, 40);
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String price = priceField.getText();
                String category = (String) categoryBox.getSelectedItem();
                if (name.isEmpty() || price.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields");
                    return;
                }
                CafeUtils.addMenuItem(name, price, category);
                JOptionPane.showMessageDialog(this, "Item added!");
                nameField.setText("");
                priceField.setText("");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        panel.add(addBtn);

        JScrollPane scrollPane = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setContentPane(scrollPane);

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
}