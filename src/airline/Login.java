package airline;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {

    private JLabel lblUsername, lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCancel;

    public Login() {
        super("Login");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // ---------- Labels ----------
        lblUsername = new JLabel("Username");
        lblUsername.setBounds(40, 30, 100, 30);
        add(lblUsername);

        lblPassword = new JLabel("Password");
        lblPassword.setBounds(40, 80, 100, 30);
        add(lblPassword);

        // ---------- Text Fields ----------
        txtUsername = new JTextField();
        txtUsername.setBounds(150, 30, 150, 30);
        add(txtUsername);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 80, 150, 30);
        add(txtPassword);

        // ---------- Image ----------
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(i2));
        imgLabel.setBounds(350, 20, 200, 200);
        add(imgLabel);

        // ---------- Buttons ----------
        btnLogin = new JButton("Login");
        btnLogin.setBounds(40, 140, 120, 35);
        btnLogin.setFont(new Font("serif", Font.BOLD, 15));
        btnLogin.setBackground(Color.BLACK);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(this);
        add(btnLogin);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(180, 140, 120, 35);
        btnCancel.setFont(new Font("serif", Font.BOLD, 15));
        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(this);
        add(btnCancel);

        // ---------- Frame Settings ----------
        setSize(600, 300);
        setLocation(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnLogin) {
            handleLogin();
        } else if (ae.getSource() == btnCancel) {
            dispose(); // closes only the current window
        }
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Conn c = new Conn()) {
            String query = "SELECT * FROM login WHERE username = ? AND password = ?";
            PreparedStatement pst = c.c.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                new MainFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
