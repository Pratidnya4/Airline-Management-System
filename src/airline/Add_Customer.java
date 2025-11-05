package airline;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Add_Customer extends JFrame {

    private JTextField passportField, pnrField, addressField, nationalityField, nameField, phoneField, flightCodeField;
    private JRadioButton maleRadio, femaleRadio;

    public Add_Customer() {
        getContentPane().setForeground(Color.BLUE);
        getContentPane().setBackground(Color.WHITE);
        setTitle("ADD CUSTOMER DETAILS");
        setLayout(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Labels and fields
        JLabel flightCodeLabel = new JLabel("FLIGHT CODE");
        flightCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        flightCodeLabel.setBounds(60, 30, 150, 27);
        add(flightCodeLabel);

        flightCodeField = new JTextField();
        flightCodeField.setBounds(200, 30, 150, 27);
        add(flightCodeField);

        JLabel passportLabel = new JLabel("PASSPORT NO");
        passportLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        passportLabel.setBounds(60, 80, 150, 27);
        add(passportLabel);

        passportField = new JTextField();
        passportField.setBounds(200, 80, 150, 27);
        add(passportField);

        JLabel pnrLabel = new JLabel("PNR NO");
        pnrLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        pnrLabel.setBounds(60, 120, 150, 27);
        add(pnrLabel);

        pnrField = new JTextField();
        pnrField.setBounds(200, 120, 150, 27);
        add(pnrField);

        JLabel addressLabel = new JLabel("ADDRESS");
        addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        addressLabel.setBounds(60, 170, 150, 27);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(200, 170, 150, 27);
        add(addressField);

        JLabel nationalityLabel = new JLabel("NATIONALITY");
        nationalityLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        nationalityLabel.setBounds(60, 220, 150, 27);
        add(nationalityLabel);

        nationalityField = new JTextField();
        nationalityField.setBounds(200, 220, 150, 27);
        add(nationalityField);

        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        nameLabel.setBounds(60, 270, 150, 27);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 270, 150, 27);
        add(nameField);

        JLabel genderLabel = new JLabel("GENDER");
        genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        genderLabel.setBounds(60, 320, 150, 27);
        add(genderLabel);

        maleRadio = new JRadioButton("MALE");
        maleRadio.setBackground(Color.WHITE);
        maleRadio.setBounds(200, 320, 70, 27);
        add(maleRadio);

        femaleRadio = new JRadioButton("FEMALE");
        femaleRadio.setBackground(Color.WHITE);
        femaleRadio.setBounds(280, 320, 90, 27);
        add(femaleRadio);

        // Group gender radio buttons
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        JLabel phoneLabel = new JLabel("PH NO");
        phoneLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        phoneLabel.setBounds(60, 370, 150, 27);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(200, 370, 150, 27);
        add(phoneField);

        JButton saveButton = new JButton("SAVE");
        saveButton.setBounds(200, 420, 150, 30);
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        add(saveButton);

        JLabel header = new JLabel("ADD CUSTOMER DETAILS");
        header.setForeground(Color.BLUE);
        header.setFont(new Font("Tahoma", Font.BOLD, 28));
        header.setBounds(400, 20, 450, 35);
        add(header);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/emp.png"));
        JLabel image = new JLabel(i1);
        image.setBounds(450, 80, 300, 410);
        add(image);

        // Save action
        saveButton.addActionListener(e -> saveCustomer());

        setSize(900, 600);
        setLocation(400, 200);
        setVisible(true);
    }

    private void saveCustomer() {
        String flightCode = flightCodeField.getText().trim();
        String passportNo = passportField.getText().trim();
        String pnrNo = pnrField.getText().trim();
        String address = addressField.getText().trim();
        String nationality = nationalityField.getText().trim();
        String name = nameField.getText().trim();
        String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "";
        String phone = phoneField.getText().trim();

        // Validation
        if (flightCode.isEmpty() || passportNo.isEmpty() || pnrNo.isEmpty() || name.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!", "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Insert data safely using PreparedStatement
        String sql = "INSERT INTO passenger (pnr_no, address, nationality, name, gender, ph_no, passport_no, fl_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/airline", "root", "prat04h@");
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, pnrNo);
            pst.setString(2, address);
            pst.setString(3, nationality);
            pst.setString(4, name);
            pst.setString(5, gender);
            pst.setString(6, phone);
            pst.setString(7, passportNo);
            pst.setString(8, flightCode);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Customer Added Successfully!");
            setVisible(false);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Add_Customer::new);
    }
}
