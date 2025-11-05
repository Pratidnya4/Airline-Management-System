package airline;


import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Flight_Info extends JFrame {

    private JTable table;
    private JTextField textField;

    public static void main(String[] args) {
        new Flight_Info().setVisible(true);
    }

    public Flight_Info() {

        super("Flight Information");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null); // centers window on screen
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));

        // ---------------- Header ----------------
        JLabel header = new JLabel("FLIGHT INFORMATION", SwingConstants.CENTER);
        header.setFont(new Font("Tahoma", Font.BOLD, 28));
        header.setForeground(new Color(70, 130, 180));
        add(header, BorderLayout.NORTH);

        // ---------------- Search Panel ----------------
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCode = new JLabel("FLIGHT CODE:");
        lblCode.setFont(new Font("Tahoma", Font.PLAIN, 17));
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(lblCode, gbc);

        textField = new JTextField(15);
        gbc.gridx = 1;
        searchPanel.add(textField, gbc);

        JButton btnShow = new JButton("SHOW");
        btnShow.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gbc.gridx = 2;
        searchPanel.add(btnShow, gbc);

        add(searchPanel, BorderLayout.CENTER);

        // ---------------- Table ----------------
        table = new JTable();
        table.setBackground(Color.WHITE);
        JScrollPane pane = new JScrollPane(table);
        pane.setBorder(BorderFactory.createTitledBorder("Flight Details"));
        add(pane, BorderLayout.SOUTH);

        // ---------------- Button Action ----------------
        btnShow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String code = textField.getText().trim();
                if (code.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a flight code.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Conn c = new Conn();
                    String query = "SELECT f_code AS 'Flight Code', f_name AS 'Flight Name', src AS 'Source', dst AS 'Destination', capacity AS 'Capacity', class_code AS 'Class Code', class_name AS 'Class Name' FROM flight, sector WHERE f_code = '" + code + "'";
                    ResultSet rs = c.s.executeQuery(query);

                    if (!rs.isBeforeFirst()) {
                        JOptionPane.showMessageDialog(null, "No flight found for code: " + code, "No Data", JOptionPane.INFORMATION_MESSAGE);
                    }

                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    DefaultTableModel model = new DefaultTableModel();
                    for (int i = 1; i <= columnCount; i++) {
                         model.addColumn(rsmd.getColumnName(i));
                    }

                    while (rs.next()) {
                     Object[] row = new Object[columnCount];
                      for (int i = 1; i <= columnCount; i++) {
                          row[i - 1] = rs.getObject(i);
                          }
                           model.addRow(row);
                      }

                       table.setModel(model);


                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
                }
            }
        });

        setVisible(true);
    }
}
