package view;

import controller.KurirController;
import model.KurirModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KurirView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public KurirView() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Manajemen Kurir");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Toolbar
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton refreshButton = new JButton("Refresh");

        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(refreshButton);

        // Table
        String[] columns = {"ID", "Nama Kurir", "Email", "No. Telepon"};
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
                        "Pilih kurir yang akan diedit",
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
                        "Pilih kurir yang akan dihapus",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        refreshButton.addActionListener(e -> loadData());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<KurirModel> kurirs = KurirController.getInstance().getAllKurir();
            for (KurirModel kurir : kurirs) {
                Object[] row = {
                        kurir.getIdKurir(),
                        kurir.getNamaKurir(),
                        kurir.getEmail(),
                        kurir.getNoTelepon()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(this, "Tambah Kurir", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form fields
        JTextField namaField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField teleponField = new JTextField(20);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        panel.add(new JLabel("Nama Kurir:"), gbc);
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridy = 2;
        panel.add(new JLabel("No. Telepon:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(namaField, gbc);
        gbc.gridy = 1;
        panel.add(emailField, gbc);
        gbc.gridy = 2;
        panel.add(teleponField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Simpan");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        saveButton.addActionListener(e -> {
            if (validateInput(namaField.getText(), emailField.getText(), teleponField.getText())) {
                KurirModel kurir = new KurirModel();
                kurir.setNamaKurir(namaField.getText().trim());
                kurir.setEmail(emailField.getText().trim());
                kurir.setNoTelepon(teleponField.getText().trim());

                try {
                    KurirController.getInstance().save(kurir);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Data kurir berhasil disimpan",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Error saving data: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
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

        JDialog dialog = new JDialog(this, "Edit Kurir", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form fields with pre-filled data
        JTextField namaField = new JTextField(currentNama, 20);
        JTextField emailField = new JTextField(currentEmail, 20);
        JTextField teleponField = new JTextField(currentTelepon, 20);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        panel.add(new JLabel("Nama Kurir:"), gbc);
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridy = 2;
        panel.add(new JLabel("No. Telepon:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(namaField, gbc);
        gbc.gridy = 1;
        panel.add(emailField, gbc);
        gbc.gridy = 2;
        panel.add(teleponField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        updateButton.addActionListener(e -> {
            if (validateInput(namaField.getText(), emailField.getText(), teleponField.getText())) {
                KurirModel kurir = new KurirModel();
                kurir.setIdKurir(id);
                kurir.setNamaKurir(namaField.getText().trim());
                kurir.setEmail(emailField.getText().trim());
                kurir.setNoTelepon(teleponField.getText().trim());

                try {
                    KurirController.getInstance().update(kurir);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Data kurir berhasil diupdate",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Error updating data: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void deleteData(int row) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus kurir ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Integer id = (Integer) table.getValueAt(row, 0);
                KurirController.getInstance().delete(id);
                loadData();
                JOptionPane.showMessageDialog(this,
                        "Data kurir berhasil dihapus",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error deleting data: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateInput(String nama, String email, String telepon) {
        if (nama.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama kurir tidak boleh kosong",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this,
                    "Email tidak valid",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (telepon.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No. Telepon tidak boleh kosong",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}