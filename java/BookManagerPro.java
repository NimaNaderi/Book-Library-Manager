import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookManagerPro extends JFrame {

    private JTextField txtSearch, txtTitle, txtAuthor, txtCategory, txtIsbn;
    private JCheckBox chkAvailable;
    private JButton btnAdd, btnUpdate, btnDelete, btnBorrow, btnReturn, btnClear, btnTheme, btnLang;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;

    private List<Book> bookList;
    private final DataService dataService;
    private String currentLang = "DE";
    private boolean isDarkMode = false;
    private Book selectedBook = null;

    public BookManagerPro() {
        dataService = new DataService();
        bookList = dataService.loadBooks();

        initWindow();
        initComponents();
        refreshTable("");
        updateTexts();
        applyCurrentTheme();
    }

    private void initWindow() {
        setTitle("Book Manager Pro");
        setSize(1000, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(new EmptyBorder(15, 15, 5, 15));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(300, 35));
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }
            private void search() { refreshTable(txtSearch.getText()); }
        });

        searchPanel.add(new JLabel("🔍"), BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);

        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTheme = createButton("Theme", ThemeManager.ACCENT_BLUE);
        btnLang = createButton("Lang", ThemeManager.ACCENT_BLUE);

        settingsPanel.add(btnTheme);
        settingsPanel.add(btnLang);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(settingsPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] cols = {"ID", "Title", "Author", "Category", "ISBN", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleSelection();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 15, 10, 15));
        add(scrollPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(320, 0));
        rightPanel.setBorder(new EmptyBorder(10, 0, 15, 15));

        JPanel formPanel = new JPanel(new GridLayout(10, 1, 5, 5));

        txtTitle = new JTextField();
        txtAuthor = new JTextField();
        txtCategory = new JTextField();
        txtIsbn = new JTextField();
        chkAvailable = new JCheckBox();

        formPanel.add(new JLabel("Title:"));
        formPanel.add(txtTitle);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(txtAuthor);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(txtCategory);
        formPanel.add(new JLabel("ISBN:"));
        formPanel.add(txtIsbn);
        formPanel.add(chkAvailable);

        JPanel actionPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        actionPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        btnAdd = createButton("Add", ThemeManager.ACCENT_GREEN);
        btnUpdate = createButton("Update", ThemeManager.ACCENT_BLUE);
        btnDelete = createButton("Delete", ThemeManager.ACCENT_RED);
        btnBorrow = createButton("Borrow", ThemeManager.ACCENT_ORANGE);
        btnReturn = createButton("Return", ThemeManager.ACCENT_GREEN);
        btnClear = createButton("Clear", Color.GRAY);

        actionPanel.add(btnAdd);
        actionPanel.add(btnUpdate);
        actionPanel.add(btnBorrow);
        actionPanel.add(btnReturn);
        actionPanel.add(btnDelete);
        actionPanel.add(btnClear);

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(formPanel, BorderLayout.NORTH);
        containerPanel.add(actionPanel, BorderLayout.CENTER);

        rightPanel.add(containerPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
        lblStatus = new JLabel("Ready");
        lblStatus.setFont(new Font("Consolas", Font.PLAIN, 12));
        statusPanel.add(lblStatus);
        add(statusPanel, BorderLayout.SOUTH);

        btnTheme.addActionListener(e -> toggleTheme());
        btnLang.addActionListener(e -> toggleLanguage());

        btnAdd.addActionListener(e -> addBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnDelete.addActionListener(e -> deleteBooks());
        btnBorrow.addActionListener(e -> borrowBooks());
        btnReturn.addActionListener(e -> changeStatus(true));
        btnClear.addActionListener(e -> clearForm());
    }

    private void refreshTable(String query) {
        tableModel.setRowCount(0);
        for (Book b : bookList) {
            if (query.isEmpty() ||
                    b.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    b.getAuthor().toLowerCase().contains(query.toLowerCase())) {

                String status = b.isAvailable() ? "✔ " + LangManager.get(currentLang, "avail") : "❌";
                tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getCategory(), b.getIsbn(), status});
            }
        }
    }

    private void handleSelection() {
        int[] rows = table.getSelectedRows();
        if (rows.length == 1) {
            String id = (String) tableModel.getValueAt(rows[0], 0);
            selectedBook = bookList.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
            if (selectedBook != null) {
                txtTitle.setText(selectedBook.getTitle());
                txtAuthor.setText(selectedBook.getAuthor());
                txtCategory.setText(selectedBook.getCategory());
                txtIsbn.setText(selectedBook.getIsbn());
                chkAvailable.setSelected(selectedBook.isAvailable());

                btnAdd.setEnabled(false);
                btnUpdate.setEnabled(true);
            }
        } else {
            clearFormOnly();
            btnAdd.setEnabled(true);
            btnUpdate.setEnabled(false);
        }
    }

    private void addBook() {
        if (!validateForm()) return;
        Book newBook = new Book(txtTitle.getText(), txtAuthor.getText(), txtCategory.getText(), txtIsbn.getText(), chkAvailable.isSelected());
        bookList.add(newBook);
        saveAndReset("Book added.");
    }

    private void updateBook() {
        if (selectedBook == null || !validateForm()) return;
        selectedBook.setTitle(txtTitle.getText());
        selectedBook.setAuthor(txtAuthor.getText());
        selectedBook.setCategory(txtCategory.getText());
        selectedBook.setIsbn(txtIsbn.getText());
        selectedBook.setAvailable(chkAvailable.isSelected());
        saveAndReset("Book updated.");
    }

    private void deleteBooks() {
        int[] rows = table.getSelectedRows();
        if (rows.length == 0) {
            showMessage(LangManager.get(currentLang, "err_select"));
            return;
        }

        List<Book> toRemove = new ArrayList<>();
        for (int row : rows) {
            String id = (String) tableModel.getValueAt(row, 0);
            bookList.stream().filter(b -> b.getId().equals(id)).findFirst().ifPresent(toRemove::add);
        }

        bookList.removeAll(toRemove);
        saveAndReset(rows.length + " Books deleted.");
    }

    private void borrowBooks() {
        int[] rows = table.getSelectedRows();
        if (rows.length == 0) {
            showMessage(LangManager.get(currentLang, "err_select"));
            return;
        }

        boolean alreadyBorrowed = false;
        for (int row : rows) {
            String id = (String) tableModel.getValueAt(row, 0);
            Book b = bookList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
            if (b != null && !b.isAvailable()) {
                alreadyBorrowed = true;
                break;
            }
        }

        if (alreadyBorrowed) {
            showMessage(LangManager.get(currentLang, "err_borrowed"));
            return;
        }

        changeStatus(false);
    }

    private void changeStatus(boolean available) {
        int[] rows = table.getSelectedRows();
        if (rows.length == 0) return;

        for (int row : rows) {
            String id = (String) tableModel.getValueAt(row, 0);
            bookList.stream().filter(b -> b.getId().equals(id)).findFirst().ifPresent(b -> b.setAvailable(available));
        }
        saveAndReset("Status updated.");
    }

    private void saveAndReset(String statusMsg) {
        dataService.saveBooks(bookList);
        refreshTable(txtSearch.getText());
        clearForm();
        lblStatus.setText(statusMsg + " | Total: " + bookList.size());
    }

    private void clearForm() {
        clearFormOnly();
        table.clearSelection();
    }

    private void clearFormOnly() {
        txtTitle.setText("");
        txtAuthor.setText("");
        txtCategory.setText("");
        txtIsbn.setText("");
        chkAvailable.setSelected(true);
        selectedBook = null;
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
    }

    private boolean validateForm() {
        if (txtTitle.getText().trim().isEmpty() ||
                txtAuthor.getText().trim().isEmpty() ||
                txtCategory.getText().trim().isEmpty() ||
                txtIsbn.getText().trim().isEmpty()) {

            showMessage(LangManager.get(currentLang, "err_empty"));
            return false;
        }
        return true;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        return btn;
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        applyCurrentTheme();
    }

    private void applyCurrentTheme() {
        ThemeManager.applyTheme(this.getContentPane(), isDarkMode);
    }

    private void toggleLanguage() {
        if (currentLang.equals("DE")) currentLang = "EN";
        else if (currentLang.equals("EN")) currentLang = "FA";
        else currentLang = "DE";
        updateTexts();
    }

    private void updateTexts() {
        setTitle(LangManager.get(currentLang, "app_title"));

        TableColumnModel cm = table.getColumnModel();
        cm.getColumn(1).setHeaderValue(LangManager.get(currentLang, "title"));
        cm.getColumn(2).setHeaderValue(LangManager.get(currentLang, "author"));
        cm.getColumn(3).setHeaderValue(LangManager.get(currentLang, "category"));
        cm.getColumn(4).setHeaderValue(LangManager.get(currentLang, "isbn"));
        cm.getColumn(5).setHeaderValue(LangManager.get(currentLang, "status"));
        table.getTableHeader().repaint();

        btnAdd.setText(LangManager.get(currentLang, "add"));
        btnUpdate.setText(LangManager.get(currentLang, "update"));
        btnDelete.setText(LangManager.get(currentLang, "delete"));
        btnBorrow.setText(LangManager.get(currentLang, "borrow"));
        btnReturn.setText(LangManager.get(currentLang, "return"));
        btnClear.setText(LangManager.get(currentLang, "clear"));
        btnTheme.setText(LangManager.get(currentLang, "theme"));
        btnLang.setText(LangManager.get(currentLang, "lang"));
        chkAvailable.setText(LangManager.get(currentLang, "avail"));
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookManagerPro().setVisible(true));
    }
}