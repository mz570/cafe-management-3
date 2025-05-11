import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("BREWTOPIA - Login");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        JLabel title = new JLabel("BREWTOPIA - Admin Login", SwingConstants.CENTER);
        title.setBounds(100, 30, 300, 40);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        add(title);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(80, 100, 100, 30);
        userLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        userLbl.setForeground(Color.WHITE);
        add(userLbl);

        JTextField userField = new JTextField();
        userField.setBounds(180, 100, 200, 30);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(userField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(80, 150, 100, 30);
        passLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        passLbl.setForeground(Color.WHITE);
        add(passLbl);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(180, 150, 200, 30);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(passField);

        JButton loginBtn = createStyledButton("Login");
        loginBtn.setBounds(180, 220, 120, 40);
        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (user.equals("admin") && pass.equals("1234")) {
                dispose();
                new Dashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        });
        add(loginBtn);

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