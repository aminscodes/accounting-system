package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JTextField nameField;
    private JTextField amountField;
    private JComboBox<String> fromCombo;
    private JComboBox<String> toCombo;
    private JTextField transferAmountField;
    private JTable table;

    private int accountCounter = 1001;

    public MainFrame() {

        setTitle("سیستم مدیریت حساب بانکی");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        add(createTopBar(), BorderLayout.NORTH);
        add(createLeftPanel(), BorderLayout.WEST);
        add(createRightPanel(), BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel top = new JPanel();
        top.setBackground(new Color(44, 62, 80));
        top.setPreferredSize(new Dimension(1000, 70));

        JLabel title = new JLabel("سیستم مدیریت حساب های بانکی");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        top.add(title);
        return top;
    }

    private JPanel createLeftPanel() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(330, 650));
        panel.setBackground(new Color(235, 239, 242));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 15, 5, 15);
        c.weightx = 1;

        int row = 0;

        JLabel t1 = new JLabel("ایجاد حساب جدید");
        t1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        c.gridy = row++;
        panel.add(t1, c);

        JLabel l1 = new JLabel("نام صاحب حساب:");
        l1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.gridy = row++;
        panel.add(l1, c);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(250, 32));
        c.gridy = row++;
        panel.add(nameField, c);

        JLabel l2 = new JLabel("موجودی اولیه:");
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.gridy = row++;
        panel.add(l2, c);

        amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(250, 32));
        c.gridy = row++;
        panel.add(amountField, c);

        JButton btnCreate = createButton(
                "ایجاد حساب",
                new Color(46, 204, 113),
                e -> createAccount()
        );

        c.gridy = row++;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(btnCreate, c);
        c.anchor = GridBagConstraints.WEST;

        c.gridy = row++;
        panel.add(Box.createVerticalStrut(25), c);

        JLabel t2 = new JLabel("انتقال وجه");
        t2.setFont(new Font("Segoe UI", Font.BOLD, 18));
        c.gridy = row++;
        panel.add(t2, c);

        JLabel l3 = new JLabel("انتقال از حساب:");
        l3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.gridy = row++;
        panel.add(l3, c);

        fromCombo = new JComboBox<>();
        fromCombo.setPreferredSize(new Dimension(250, 32));
        c.gridy = row++;
        panel.add(fromCombo, c);

        JLabel l4 = new JLabel("به حساب:");
        l4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.gridy = row++;
        panel.add(l4, c);

        toCombo = new JComboBox<>();
        toCombo.setPreferredSize(new Dimension(250, 32));
        c.gridy = row++;
        panel.add(toCombo, c);

        JLabel l5 = new JLabel("مبلغ:");
        l5.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.gridy = row++;
        panel.add(l5, c);

        transferAmountField = new JTextField();
        transferAmountField.setPreferredSize(new Dimension(250, 32));
        c.gridy = row++;
        panel.add(transferAmountField, c);

        JButton btnTransfer = createButton(
                "انتقال وجه",
                new Color(52, 152, 219),
                e -> transferMoney()
        );

        c.gridy = row++;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(btnTransfer, c);

        return panel;
    }

    private JPanel createRightPanel() {

        JPanel main = new JPanel(new BorderLayout());

        String[] columns = {"شماره حساب", "نام", "موجودی"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {

            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) return Double.class;
                return String.class;
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(26);
        table.getTableHeader().setReorderingAllowed(false);

        TableCellRenderer balanceRenderer = new DefaultTableCellRenderer() {
            private final java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.##");

            protected void setValue(Object value) {
                if (value instanceof Number) {
                    setText(df.format(((Number) value).doubleValue()));
                } else {
                    super.setValue(value);
                }
            }
        };

        table.getColumnModel().getColumn(2).setCellRenderer(balanceRenderer);

        JScrollPane scroll = new JScrollPane(table);
        main.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        bottom.setBackground(new Color(245, 245, 245));

        JButton btnUpdate = createButton(
                "به‌روزرسانی",
                new Color(52, 73, 94),
                e -> JOptionPane.showMessageDialog(this, "به‌روزرسانی شد")
        );

        JButton btnDelete = createButton(
                "حذف حساب",
                new Color(231, 76, 60),
                e -> deleteAccount()
        );

        bottom.add(btnUpdate);
        bottom.add(btnDelete);

        main.add(bottom, BorderLayout.SOUTH);

        return main;
    }

    private JButton createButton(String text, Color color, ActionListener action) {

        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setPreferredSize(new Dimension(200, 40));
        btn.addActionListener(action);

        return btn;
    }

    private void createAccount() {

        String name = nameField.getText().trim();
        String amountText = amountField.getText().trim();

        if (name.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفا همه فیلدها را پر کنید");
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(amountText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "مبلغ نامعتبر است");
            return;
        }

        String accountNumber = String.valueOf(accountCounter++);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{accountNumber, name, amount});

        String display = accountNumber + " - " + name;

        fromCombo.addItem(display);
        toCombo.addItem(display);

        nameField.setText("");
        amountField.setText("");

        JOptionPane.showMessageDialog(this, "حساب با موفقیت ایجاد شد");
    }

    private void transferMoney() {

        if (fromCombo.getSelectedItem() == null || toCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "لطفاً حساب مبدا و مقصد را انتخاب کنید");
            return;
        }

        String from = fromCombo.getSelectedItem().toString();
        String to = toCombo.getSelectedItem().toString();
        String amountText = transferAmountField.getText().trim();

        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً مبلغ را وارد کنید");
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(amountText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "مبلغ نامعتبر است");
            return;
        }

        if (from.equals(to)) {
            JOptionPane.showMessageDialog(this, "حساب مبدا و مقصد نمی‌توانند یکسان باشند");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        int fromRow = -1;
        int toRow = -1;

        String fromAcc = from.split(" - ")[0];
        String toAcc = to.split(" - ")[0];

        for (int i = 0; i < model.getRowCount(); i++) {
            String acc = model.getValueAt(i, 0).toString();

            if (acc.equals(fromAcc)) fromRow = i;
            if (acc.equals(toAcc)) toRow = i;
        }

        if (fromRow == -1 || toRow == -1) {
            JOptionPane.showMessageDialog(this, "خطا در پیدا کردن حساب‌ها");
            return;
        }

        double fromBalance = (double) model.getValueAt(fromRow, 2);
        double toBalance = (double) model.getValueAt(toRow, 2);

        if (amount > fromBalance) {
            JOptionPane.showMessageDialog(this, "موجودی حساب مبدا کافی نیست");
            return;
        }

        model.setValueAt(fromBalance - amount, fromRow, 2);
        model.setValueAt(toBalance + amount, toRow, 2);

        transferAmountField.setText("");

        JOptionPane.showMessageDialog(this, "انتقال با موفقیت انجام شد");
    }

    private void deleteAccount() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "لطفاً یک حساب را از جدول انتخاب کنید");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        String account = model.getValueAt(row, 0).toString();
        String name = model.getValueAt(row, 1).toString();
        String display = account + " - " + name;

        model.removeRow(row);

        fromCombo.removeItem(display);
        toCombo.removeItem(display);

        JOptionPane.showMessageDialog(this, "حساب با موفقیت حذف شد");
    }
}
