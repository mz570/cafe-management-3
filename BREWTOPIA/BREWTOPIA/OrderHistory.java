import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderHistory extends JFrame {
    public OrderHistory() {
        setTitle("BREWTOPIA - Order History");
        setSize(600, 500);
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

        JLabel lbl = new JLabel("Order History", SwingConstants.CENTER);
        lbl.setBounds(100, 20, 400, 40);
        lbl.setFont(new Font("Serif", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        add(lbl);

        JTextArea historyArea = new JTextArea();
        historyArea.setBounds(30, 80, 540, 350);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 14));
        historyArea.setEditable(false);

        ArrayList<String> history = OrderPanel.loadHistory();
        if (history.isEmpty()) {
            historyArea.setText("No orders yet.");
        } else {
            historyArea.setText(String.join("\n\n=== Order ===\n", history));
        }

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBounds(30, 80, 540, 350);
        add(scrollPane);

        setVisible(true);
    }
}