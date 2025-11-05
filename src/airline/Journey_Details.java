package airline;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Journey_Details extends JFrame {  // Fourth

    private JTable table;
    private JButton showButton;
    private JComboBox<String> comboBoxSource, comboBoxDestination;

    public static void main(String[] args) {
        new Journey_Details();
    }

    public Journey_Details() {

        setTitle("JOURNEY DETAILS");
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel heading = new JLabel("JOURNEY DETAILS");
        heading.setForeground(Color.BLUE);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        heading.setBounds(280, 20, 400, 40);
        add(heading);

        // ---------- Labels ----------
        JLabel lblSource = new JLabel("SOURCE");
        lblSource.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblSource.setBounds(60, 100, 150, 27);
        add(lblSource);

        JLabel lblDestination = new JLabel("DESTINATION");
        lblDestination.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblDestination.setBounds(350, 100, 150, 27);
        add(lblDestination);

        // ---------- Combo Boxes ----------
        String[] cities = {"BANGALORE", "MUMBAI", "CHENNAI", "PATNA", "DELHI", "HYDERABAD"};
        comboBoxSource = new JComboBox<>(cities);
        comboBoxSource.setBounds(150, 100, 150, 27);
        add(comboBoxSource);

        comboBoxDestination = new JComboBox<>(cities);
        comboBoxDestination.setBounds(500, 100, 150, 27);
        add(comboBoxDestination);

        // ---------- Show Button ----------
        showButton = new JButton("SHOW");
        showButton.setBounds(680, 100, 100, 30);
        showButton.setBackground(Color.BLACK);
        showButton.setForeground(Color.WHITE);
        add(showButton);

        // ---------- Table Setup ----------
        table = new JTable();
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(40, 250, 780, 250);
        pane.getViewport().setBackground(Color.WHITE);
        add(pane);

        // ---------- Column Labels (below heading row) ----------
        String[] colLabels = {"PNR_NO", "TICKET_ID", "F_CODE", "JNY_DATE", "JNY_TIME", "SOURCE", "DESTINATION"};
        int x = 70;
        for (String label : colLabels) {
            JLabel lbl = new JLabel(label);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
            lbl.setBounds(x, 220, 100, 20);
            add(lbl);
            x += 100;
        }

        // ---------- Button Action ----------
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String src = (String) comboBoxSource.getSelectedItem();
                String dst = (String) comboBoxDestination.getSelectedItem();

                try {
                    Conn c = new Conn();
                    String query = "SELECT * FROM reservation WHERE src = '" + src + "' AND dst = '" + dst + "'";
                    ResultSet rs = c.s.executeQuery(query);

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
                        JOptionPane.showMessageDialog(null,
                                "No flights found between " + src + " and " + dst,
                                "No Data", JOptionPane.INFORMATION_MESSAGE);
                    }

                    rs.close();
                    c.s.close();
                    c.c.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Database Error: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setSize(880, 600);
        setLocation(400, 200);
        setVisible(true);
    }
}
