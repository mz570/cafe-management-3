import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Dashboard extends JFrame {
    private JLabel backgroundLabel;

    public Dashboard() {
        setTitle("BREWTOPIA - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 144, 255), 0, getHeight(), new Color(70, 130, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("BREWTOPIA");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Sidebar Panel
        JPanel sidebarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(70, 130, 180), 0, getHeight(), new Color(100, 149, 237));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebarPanel.setPreferredSize(new Dimension(200, 540));
        sidebarPanel.setLayout(null);

        // Sidebar Buttons
        JButton menuBtn = createStyledButton("Manage Menu", 50);
        menuBtn.addActionListener(e -> new MenuManagement().setVisible(true));
        sidebarPanel.add(menuBtn);

        JButton orderBtn = createStyledButton("Take Order", 150);
        orderBtn.addActionListener(e -> new OrderPanel().setVisible(true));
        sidebarPanel.add(orderBtn);

        JButton historyBtn = createStyledButton("Order History", 250);
        historyBtn.addActionListener(e -> new OrderHistory().setVisible(true));
        sidebarPanel.add(historyBtn);

        JButton bgBtn = createStyledButton("Change Background", 350);
        bgBtn.addActionListener(e -> changeBackground());
        sidebarPanel.add(bgBtn);

        add(sidebarPanel, BorderLayout.WEST);

        // Main Content Panel with Gradient Background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 250), 0, getHeight(), new Color(70, 130, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 600, 540);
        mainPanel.add(backgroundLabel);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createStyledButton(String text, int y) {
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
        button.setBounds(20, y, 160, 50);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    private void changeBackground() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIcon icon = new ImageIcon(file.getPath());
                Image img = icon.getImage().getScaledInstance(600, 540, Image.SCALE_SMOOTH);
                backgroundLabel.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading image");
            }
        }
    }
}