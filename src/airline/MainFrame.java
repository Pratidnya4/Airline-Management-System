package airline;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }

    public MainFrame() {
        super("AIRLINE RESERVATION MANAGEMENT SYSTEM");
        initialize();
    }

    private void initialize() {
        // Basic frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Load background image
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("Icons/front.jpg"));
        Image img = icon.getImage();

        // Create a panel with a custom paint method for dynamic scaling
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Scale image to fit screen dynamically
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);

        // Add title
        JLabel titleLabel = new JLabel("AIR INDIA WELCOMES YOU", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
        titleLabel.setBounds(0, 80, getWidth(), 60);
        backgroundPanel.add(titleLabel);

        // Add background panel to frame
        add(backgroundPanel, BorderLayout.CENTER);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu airlineSystem = new JMenu("AIRLINE SYSTEM");
        airlineSystem.setForeground(Color.BLUE);
        menuBar.add(airlineSystem);

        JMenuItem flightDetails = new JMenuItem("FLIGHT_INFO");
        JMenuItem reservationDetails = new JMenuItem("ADD_CUSTOMER_DETAILS");
        JMenuItem passengerDetails = new JMenuItem("JOURNEY_DETAILS");
        JMenuItem paymentDetails = new JMenuItem("PAYMENT_DETAILS");
        JMenuItem cancellation = new JMenuItem("CANCELLATION");

        airlineSystem.add(flightDetails);
        airlineSystem.add(reservationDetails);
        airlineSystem.add(passengerDetails);
        airlineSystem.add(paymentDetails);
        airlineSystem.add(cancellation);

        JMenu logoutMenu = new JMenu("Logout");
        logoutMenu.setForeground(Color.RED);
        menuBar.add(logoutMenu);

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutMenu.add(logoutItem);

        // Event actions
        flightDetails.addActionListener(e -> new Flight_Info());
        reservationDetails.addActionListener(e -> { try { new Add_Customer(); } catch (Exception ex) { ex.printStackTrace(); } });
        passengerDetails.addActionListener(e -> { try { new Journey_Details(); } catch (Exception ex) { ex.printStackTrace(); } });
        paymentDetails.addActionListener(e -> { try { new Payment_Details(); } catch (Exception ex) { ex.printStackTrace(); } });
        cancellation.addActionListener(e -> new Cancel());
        logoutItem.addActionListener(e -> {
            setVisible(false);
            new Login().setVisible(true);
        });

        // Final touches
        setVisible(true);
    }
}
