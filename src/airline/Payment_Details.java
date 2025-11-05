package airline;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Payment_Details extends JFrame { // Fifth

    private JTextField textField;
    private JTable table;

    public static void main(String[] args) {
        new Payment_Details();
    }

    public Payment_Details() {
        initialize();
    }

    private void initialize() {
        setTitle("PAYMENT_DETAILS");
        getContentPane().setBackground(Color.WHITE);
        setSize(960, 590);
        setLayout(null);

        JLabel Fcode = new JLabel("PNR NO");
        Fcode.setFont(new Font("Tahoma", Font.PLAIN, 17));
        Fcode.setBounds(60, 160, 150, 26);
        add(Fcode);

        textField = new JTextField();
        textField.setBounds(200, 160, 150, 26);
        add(textField);

        table = new JTable();
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(45, 329, 850, 150);
        add(pane);

        JButton Show = new JButton("SHOW");
        Show.setFont(new Font("Tahoma", Font.PLAIN, 17));
        Show.setBackground(Color.BLACK);
        Show.setForeground(Color.WHITE);
        Show.setBounds(200, 210, 150, 26);
        add(Show);

        JLabel Sector = new JLabel("PAYMENT DETAILS");
        Sector.setForeground(Color.BLUE);
        Sector.setFont(new Font("Tahoma", Font.BOLD, 28));
        Sector.setBounds(51, 17, 350, 39);
        add(Sector);

        JLabel FlightCode = new JLabel("PNR_NO");
        FlightCode.setFont(new Font("Tahoma", Font.PLAIN, 13));
        FlightCode.setBounds(84, 292, 108, 26);
        add(FlightCode);

        JLabel Capacity = new JLabel("PAID_AMOUNT");
        Capacity.setFont(new Font("Tahoma", Font.PLAIN, 13));
        Capacity.setBounds(183, 298, 92, 14);
        add(Capacity);

        JLabel Classcode = new JLabel("PAY_DATE");
        Classcode.setFont(new Font("Tahoma", Font.PLAIN, 13));
        Classcode.setBounds(322, 294, 101, 24);
        add(Classcode);

        JLabel Classname = new JLabel("CHEQUE_NO");
        Classname.setFont(new Font("Tahoma", Font.PLAIN, 13));
        Classname.setBounds(455, 298, 114, 14);
        add(Classname);

        JLabel Cardno = new JLabel("CARD_NO");
        Cardno.setFont(new Font("Tahoma", Font.PLAIN, 13));
        Cardno.setBounds(602, 299, 101, 19);
        add(Cardno);

        JLabel Phoneno = new JLabel("PHONE_NO");
        Phoneno.setFont(new Font("Tahoma", Font.PLAIN, 13));
        Phoneno.setBounds(712, 294, 86, 24);
        add(Phoneno);

        JLabel label = new JLabel("");
        label.setIcon(new ImageIcon(ClassLoader.getSystemResource("Icons/payment.png")));
        label.setBounds(425, 15, 239, 272);
        add(label);

        // ----------- Button Action -----------
        Show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String code = textField.getText().trim();
                if (code.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a PNR number.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Conn c = new Conn();
                    String str = "SELECT pnr_no AS 'PNR No', paid_amt AS 'Paid Amount', pay_date AS 'Pay Date', cheque_no AS 'Cheque No', card_no AS 'Card No', ph_no AS 'Phone No' FROM payment WHERE pnr_no = '" + code + "'";
                    ResultSet rs = c.s.executeQuery(str);

                    // --- Manual conversion to TableModel (no external jar) ---
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

                    if (model.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No payment found for PNR: " + code, "No Data", JOptionPane.INFORMATION_MESSAGE);
                    }

                    rs.close();
                    c.s.close();
                    c.c.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setLocation(400, 200);
        setVisible(true);
    }
}
