package view;

import controller.LokasiController;
import model.LokasiModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LokasiView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public LokasiView() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Manajemen Lokasi");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton refreshButton = new JButton("Refresh");

        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(refreshButton);

        // Table
        String[] columns = {"ID", "Alamat Lokasi"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(550);

        JScrollPane scrollPane = new JScrollPane(table);

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
                        "Pilih lokasi yang akan diedit",
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
                        "Pilih lokasi yang akan dihapus",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        refreshButton.addActionListener(e -> loadData());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<LokasiModel> list = LokasiController.getInstance().getAllLokasi();
            for (LokasiModel lokasi : list) {
                Object[] row = {
                        lokasi.getIdLokasi(),
                        lokasi.getAlamatLokasi()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(this, "Tambah Lokasi", true);
        dialog.setSize(500, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // TextArea for address
        JTextArea alamatArea = new JTextArea(4, 30);
        alamatArea.setLineWrap(true);
        alamatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(alamatArea);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("Alamat Lokasi:"), gbc);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Simpan");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        saveButton.addActionListener(e -> {
            if (validateInput(alamatArea.getText())) {
                LokasiModel lokasi = new LokasiModel();
                lokasi.setAlamatLokasi(alamatArea.getText().trim());

                try {
                    LokasiController.getInstance().save(lokasi);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Data lokasi berhasil disimpan",
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
        String currentAlamat = (String) table.getValueAt(row, 1);

        JDialog dialog = new JDialog(this, "Edit Lokasi", true);
        dialog.setSize(500, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // TextArea with pre-filled data
        JTextArea alamatArea = new JTextArea(currentAlamat, 4, 30);
        alamatArea.setLineWrap(true);
        alamatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(alamatArea);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("Alamat Lokasi:"), gbc);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        updateButton.addActionListener(e -> {
            if (validateInput(alamatArea.getText())) {
                LokasiModel lokasi = new LokasiModel();
                lokasi.setIdLokasi(id);
                lokasi.setAlamatLokasi(alamatArea.getText().trim());

                try {
                    LokasiController.getInstance().update(lokasi);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Data lokasi berhasil diupdate",
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
                "Apakah Anda yakin ingin menghapus lokasi ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Integer id = (Integer) table.getValueAt(row, 0);
                LokasiController.getInstance().delete(id);
                loadData();
                JOptionPane.showMessageDialog(this,
                        "Data lokasi berhasil dihapus",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                showError("Error deleting data: " + e.getMessage());
            }
        }
    }

    private boolean validateInput(String alamat) {
        if (alamat.trim().isEmpty()) {
            showError("Alamat lokasi tidak boleh kosong");
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