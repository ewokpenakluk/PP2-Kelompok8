package view;

import controller.MasyarakatController;
import model.MasyarakatModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MasyarakatView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public MasyarakatView() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Manajemen Data Masyarakat");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Panel (Search & Buttons)
        JPanel topPanel = new JPanel(new BorderLayout(5, 0));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Cari");
        searchPanel.add(new JLabel("Cari:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Table
        String[] columns = {"ID", "Nama Lengkap", "Email", "No. Telepon", "Alamat"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(300);

        // Add components
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Action Listeners
        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                showEditDialog(row);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Pilih data yang akan diedit",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                deleteData(row);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Pilih data yang akan dihapus",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        refreshButton.addActionListener(e -> loadData());
        searchButton.addActionListener(e -> searchData());

        // Add search on enter
        searchField.addActionListener(e -> searchData());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<MasyarakatModel> list = MasyarakatController.getInstance().getAllMasyarakat();
            for (MasyarakatModel masyarakat : list) {
                addToTable(masyarakat);
            }
        } catch (Exception e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void searchData() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        tableModel.setRowCount(0);
        try {
            List<MasyarakatModel> list = MasyarakatController.getInstance()
                    .searchMasyarakat(keyword);
            for (MasyarakatModel masyarakat : list) {
                addToTable(masyarakat);
            }
        } catch (Exception e) {
            showError("Error searching data: " + e.getMessage());
        }
    }

    private void addToTable(MasyarakatModel masyarakat) {
        Object[] row = {
                masyarakat.getIdMasyarakat(),
                masyarakat.getNamaLengkap(),
                masyarakat.getEmail(),
                masyarakat.getNoTelepon(),
                masyarakat.getAlamat()
        };
        tableModel.addRow(row);
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(this, "Tambah Data Masyarakat", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form components
        JTextField namaField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField teleponField = new JTextField(20);
        JTextArea alamatArea = new JTextArea(4, 20);
        alamatArea.setLineWrap(true);
        alamatArea.setWrapStyleWord(true);
        JScrollPane alamatScroll = new JScrollPane(alamatArea);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add components using helper method
        addFormField(panel, "Nama Lengkap:", namaField, gbc);
        addFormField(panel, "Email:", emailField, gbc);
        addFormField(panel, "No. Telepon:", teleponField, gbc);
        addFormField(panel, "Alamat:", alamatScroll, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Simpan");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        saveButton.addActionListener(e -> {
            if (validateInput(namaField.getText(), emailField.getText(),
                    teleponField.getText(), alamatArea.getText())) {

                MasyarakatModel masyarakat = new MasyarakatModel();
                masyarakat.setNamaLengkap(namaField.getText().trim());
                masyarakat.setEmail(emailField.getText().trim());
                masyarakat.setNoTelepon(teleponField.getText().trim());
                masyarakat.setAlamat(alamatArea.getText().trim());

                try {
                    MasyarakatController.getInstance().save(masyarakat);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Data masyarakat berhasil disimpan",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    showError("Error saving data: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void showEditDialog(int row) {
        Integer id = (Integer) table.getValueAt(row, 0);
        String currentNama = (String) table.getValueAt(row, 1);
        String currentEmail = (String) table.getValueAt(row, 2);
        String currentTelepon = (String) table.getValueAt(row, 3);
        String currentAlamat = (String) table.getValueAt(row, 4);

        JDialog dialog = new JDialog(this, "Edit Data Masyarakat", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form components with pre-filled data
        JTextField namaField = new JTextField(currentNama, 20);
        JTextField emailField = new JTextField(currentEmail, 20);
        JTextField teleponField = new JTextField(currentTelepon, 20);
        JTextArea alamatArea = new JTextArea(currentAlamat, 4, 20);
        alamatArea.setLineWrap(true);
        alamatArea.setWrapStyleWord(true);
        JScrollPane alamatScroll = new JScrollPane(alamatArea);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add components
        addFormField(panel, "Nama Lengkap:", namaField, gbc);
        addFormField(panel, "Email:", emailField, gbc);
        addFormField(panel, "No. Telepon:", teleponField, gbc);
        addFormField(panel, "Alamat:", alamatScroll, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        updateButton.addActionListener(e -> {
            if (validateInput(namaField.getText(), emailField.getText(),
                    teleponField.getText(), alamatArea.getText())) {

                MasyarakatModel masyarakat = new MasyarakatModel();
                masyarakat.setIdMasyarakat(id);
                masyarakat.setNamaLengkap(namaField.getText().trim());
                masyarakat.setEmail(emailField.getText().trim());
                masyarakat.setNoTelepon(teleponField.getText().trim());
                masyarakat.setAlamat(alamatArea.getText().trim());

                try {
                    MasyarakatController.getInstance().update(masyarakat);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Data masyarakat berhasil diupdate",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    showError("Error updating data: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void deleteData(int row) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Integer id = (Integer) table.getValueAt(row, 0);
                MasyarakatController.getInstance().delete(id);
                loadData();
                JOptionPane.showMessageDialog(this,
                        "Data masyarakat berhasil dihapus",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                showError("Error deleting data: " + e.getMessage());
            }
        }
    }

    private void addFormField(JPanel panel, String label, JComponent field,
                              GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
    }

    private boolean validateInput(String nama, String email, String telepon, String alamat) {
        if (nama.trim().isEmpty()) {
            showError("Nama lengkap tidak boleh kosong");
            return false;
        }

        if (email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("Format email tidak valid");
            return false;
        }

        if (telepon.trim().isEmpty()) {
            showError("No. Telepon tidak boleh kosong");
            return false;
        }

        if (alamat.trim().isEmpty()) {
            showError("Alamat tidak boleh kosong");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}