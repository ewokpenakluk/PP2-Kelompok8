package view;

import controller.KategoriController;
import model.KategoriModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KategoriView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public KategoriView() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Manajemen Kategori");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Toolbar Panel
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Buttons
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton refreshButton = new JButton("Refresh");

        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(refreshButton);

        // Table
        String[] columns = {"ID", "Nama Kategori"};
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
        table.getColumnModel().getColumn(1).setPreferredWidth(350);

        // Add components
        mainPanel.add(toolbar, BorderLayout.NORTH);
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
                        "Pilih kategori yang akan diedit",
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
                        "Pilih kategori yang akan dihapus",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        refreshButton.addActionListener(e -> loadData());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<KategoriModel> list = KategoriController.getInstance().getAllKategori();
            for (KategoriModel kategori : list) {
                Object[] row = {
                        kategori.getIdKategori(),
                        kategori.getNamaKategori()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(this, "Tambah Kategori", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form components
        JTextField namaField = new JTextField(20);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        panel.add(new JLabel("Nama Kategori:"), gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(namaField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Simpan");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        saveButton.addActionListener(e -> {
            if (validateInput(namaField.getText())) {
                KategoriModel kategori = new KategoriModel();
                kategori.setNamaKategori(namaField.getText().trim());

                try {
                    KategoriController.getInstance().save(kategori);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Kategori berhasil ditambahkan",
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

        JDialog dialog = new JDialog(this, "Edit Kategori", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form components with pre-filled data
        JTextField namaField = new JTextField(currentNama, 20);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        panel.add(new JLabel("Nama Kategori:"), gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(namaField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        updateButton.addActionListener(e -> {
            if (validateInput(namaField.getText())) {
                KategoriModel kategori = new KategoriModel();
                kategori.setIdKategori(id);
                kategori.setNamaKategori(namaField.getText().trim());

                try {
                    KategoriController.getInstance().update(kategori);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Kategori berhasil diupdate",
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
                "Apakah Anda yakin ingin menghapus kategori ini?\n" +
                        "Penghapusan kategori akan mempengaruhi data sampah yang terkait.",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Integer id = (Integer) table.getValueAt(row, 0);
                KategoriController.getInstance().delete(id);
                loadData();
                JOptionPane.showMessageDialog(this,
                        "Kategori berhasil dihapus",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                showError("Error deleting data: " + e.getMessage());
            }
        }
    }

    private boolean validateInput(String nama) {
        if (nama.trim().isEmpty()) {
            showError("Nama kategori tidak boleh kosong");
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