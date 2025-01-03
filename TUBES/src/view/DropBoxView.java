package view;

import controller.DropBoxController;
import model.DropBoxModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DropBoxView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public DropBoxView() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Manajemen Drop Box");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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
        String[] columns = {"ID", "Nama Drop Box", "Alamat"};
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
        table.getColumnModel().getColumn(2).setPreferredWidth(400);

        // Add components to main panel
        mainPanel.add(toolbar, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Add action listeners
        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                showEditDialog(row);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Pilih drop box yang akan diedit",
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
                        "Pilih drop box yang akan dihapus",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        refreshButton.addActionListener(e -> loadData());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<DropBoxModel> list = DropBoxController.getInstance().getAllDropBox();
            for (DropBoxModel dropBox : list) {
                Object[] row = {
                        dropBox.getIdDropbox(),
                        dropBox.getNamaDropbox(),
                        dropBox.getAlamat()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(this, "Tambah Drop Box", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form components
        JTextField namaField = new JTextField(20);
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

        panel.add(new JLabel("Nama Drop Box:"), gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Alamat:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(namaField, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(alamatScroll, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Simpan");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        saveButton.addActionListener(e -> {
            if (validateInput(namaField.getText(), alamatArea.getText())) {
                DropBoxModel dropBox = new DropBoxModel();
                dropBox.setNamaDropbox(namaField.getText().trim());
                dropBox.setAlamat(alamatArea.getText().trim());

                try {
                    DropBoxController.getInstance().save(dropBox);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Data berhasil disimpan",
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
        String currentAlamat = (String) table.getValueAt(row, 2);

        JDialog dialog = new JDialog(this, "Edit Drop Box", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form components with pre-filled data
        JTextField namaField = new JTextField(currentNama, 20);
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

        panel.add(new JLabel("Nama Drop Box:"), gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Alamat:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(namaField, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(alamatScroll, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action Listeners
        updateButton.addActionListener(e -> {
            if (validateInput(namaField.getText(), alamatArea.getText())) {
                DropBoxModel dropBox = new DropBoxModel();
                dropBox.setIdDropbox(id);
                dropBox.setNamaDropbox(namaField.getText().trim());
                dropBox.setAlamat(alamatArea.getText().trim());

                try {
                    DropBoxController.getInstance().update(dropBox);
                    loadData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Data berhasil diupdate",
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
                DropBoxController.getInstance().delete(id);
                loadData();
                JOptionPane.showMessageDialog(this,
                        "Data berhasil dihapus",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                showError("Error deleting data: " + e.getMessage());
            }
        }
    }

    private boolean validateInput(String nama, String alamat) {
        if (nama.trim().isEmpty()) {
            showError("Nama Drop Box tidak boleh kosong");
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