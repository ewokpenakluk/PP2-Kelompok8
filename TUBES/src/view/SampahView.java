package view;

import controller.KategoriController;
import controller.SampahController;
import model.KategoriModel;
import model.SampahModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SampahView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<KategoriModel> kategoriComboBox;

    public SampahView() {
        setTitle("Manajemen Sampah");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Toolbar Panel
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        // Buttons
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton refreshButton = new JButton("Refresh");

        // Filter by Kategori
        JLabel filterLabel = new JLabel("Filter Kategori:");
        kategoriComboBox = new JComboBox<>();
        kategoriComboBox.setPreferredSize(new Dimension(150, 25));

        // Add components to toolbar
        toolbarPanel.add(addButton);
        toolbarPanel.add(editButton);
        toolbarPanel.add(deleteButton);
        toolbarPanel.add(refreshButton);
        toolbarPanel.add(Box.createHorizontalStrut(20));
        toolbarPanel.add(filterLabel);
        toolbarPanel.add(kategoriComboBox);

        // Table
        String[] columns = {"ID", "Kategori", "Nama Sampah", "Berat (kg)", "Poin"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) return Float.class;
                if (columnIndex == 4) return Integer.class;
                return Object.class;
            }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Customize table appearance
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        // Right align numbers
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        // Add to main panel
        mainPanel.add(toolbarPanel, BorderLayout.NORTH);
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

        kategoriComboBox.addActionListener(e -> {
            if (kategoriComboBox.getSelectedItem() != null) {
                KategoriModel selected = (KategoriModel) kategoriComboBox.getSelectedItem();
                loadDataByKategori(selected.getIdKategori());
            }
        });

        // Initialize data
        loadKategori();
        loadData();
    }

    private void loadKategori() {
        kategoriComboBox.removeAllItems();
        kategoriComboBox.addItem(null); // Add empty option

        try {
            List<KategoriModel> kategoris = KategoriController.getInstance().getAllKategori();
            for (KategoriModel kategori : kategoris) {
                kategoriComboBox.addItem(kategori);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading kategori: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<SampahModel> list = SampahController.getInstance().getAllSampah();
            for (SampahModel sampah : list) {
                Object[] row = {
                        sampah.getIdSampah(),
                        sampah.getKategori().getNamaKategori(),
                        sampah.getNamaSampah(),
                        sampah.getBerat(),
                        sampah.getPoin()
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

    private void loadDataByKategori(Integer kategoriId) {
        if (kategoriId == null) {
            loadData();
            return;
        }

        tableModel.setRowCount(0);
        try {
            List<SampahModel> list = SampahController.getInstance().getSampahByKategori(kategoriId);
            for (SampahModel sampah : list) {
                Object[] row = {
                        sampah.getIdSampah(),
                        sampah.getKategori().getNamaKategori(),
                        sampah.getNamaSampah(),
                        sampah.getBerat(),
                        sampah.getPoin()
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
        JDialog dialog = new JDialog(this, "Tambah Sampah", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Form components
        JComboBox<KategoriModel> kategoriField = new JComboBox<>();
        for (int i = 0; i < kategoriComboBox.getItemCount(); i++) {
            KategoriModel item = kategoriComboBox.getItemAt(i);
            if (item != null) {
                kategoriField.addItem(item);
            }
        }

        JTextField namaField = new JTextField(20);
        JTextField beratField = new JTextField(20);
        JTextField poinField = new JTextField(20);

        // Layout
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Kategori:"), gbc);
        gbc.gridx = 1;
        panel.add(kategoriField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nama Sampah:"), gbc);
        gbc.gridx = 1;
        panel.add(namaField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Berat (kg):"), gbc);
        gbc.gridx = 1;
        panel.add(beratField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Poin:"), gbc);
        gbc.gridx = 1;
        panel.add(poinField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Simpan");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action listeners
        saveButton.addActionListener(e -> {
            if (validateInput(kategoriField, namaField, beratField, poinField)) {
                try {
                    SampahModel sampah = new SampahModel();
                    KategoriModel selectedKategori = (KategoriModel) kategoriField.getSelectedItem();
                    sampah.setIdKategori(selectedKategori.getIdKategori());
                    sampah.setKategori(selectedKategori);
                    sampah.setNamaSampah(namaField.getText().trim());
                    sampah.setBerat(Float.parseFloat(beratField.getText().trim()));
                    sampah.setPoin(Integer.parseInt(poinField.getText().trim()));

                    SampahController.getInstance().save(sampah);
                    loadData();
                    dialog.dispose();

                    JOptionPane.showMessageDialog(this,
                            "Data berhasil disimpan",
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
        String currentKategori = (String) table.getValueAt(row, 1);
        String currentNama = (String) table.getValueAt(row, 2);
        Float currentBerat = (Float) table.getValueAt(row, 3);
        Integer currentPoin = (Integer) table.getValueAt(row, 4);

        JDialog dialog = new JDialog(this, "Edit Sampah", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        // Create and setup panel similar to showAddDialog
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Form components with pre-filled data
        JComboBox<KategoriModel> kategoriField = new JComboBox<>();
        for (int i = 0; i < kategoriComboBox.getItemCount(); i++) {
            KategoriModel item = kategoriComboBox.getItemAt(i);
            if (item != null) {
                kategoriField.addItem(item);
                if (item.getNamaKategori().equals(currentKategori)) {
                    kategoriField.setSelectedItem(item);
                }
            }
        }

        JTextField namaField = new JTextField(currentNama, 20);
        JTextField beratField = new JTextField(String.valueOf(currentBerat), 20);
        JTextField poinField = new JTextField(String.valueOf(currentPoin), 20);

        // Add components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Kategori:"), gbc);
        gbc.gridx = 1;
        panel.add(kategoriField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nama Sampah:"), gbc);
        gbc.gridx = 1;
        panel.add(namaField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Berat (kg):"), gbc);
        gbc.gridx = 1;
        panel.add(beratField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Poin:"), gbc);
        gbc.gridx = 1;
        panel.add(poinField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // Action listeners
        updateButton.addActionListener(e -> {
            if (validateInput(kategoriField, namaField, beratField, poinField)) {
                try {
                    SampahModel sampah = new SampahModel();
                    sampah.setIdSampah(id);
                    KategoriModel selectedKategori = (KategoriModel) kategoriField.getSelectedItem();
                    sampah.setIdKategori(selectedKategori.getIdKategori());
                    sampah.setKategori(selectedKategori);
                    sampah.setNamaSampah(namaField.getText().trim());
                    sampah.setBerat(Float.parseFloat(beratField.getText().trim()));
                    sampah.setPoin(Integer.parseInt(poinField.getText().trim()));

                    SampahController.getInstance().update(sampah);
                    loadData();
                    dialog.dispose();

                    JOptionPane.showMessageDialog(this,
                            "Data berhasil diupdate",
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
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Integer id = (Integer) table.getValueAt(row, 0);
                SampahController.getInstance().delete(id);
                loadData();
                JOptionPane.showMessageDialog(this,
                        "Data berhasil dihapus",
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

    private boolean validateInput(JComboBox<KategoriModel> kategoriField,
                                  JTextField namaField,
                                  JTextField beratField,
                                  JTextField poinField) {
        if (kategoriField.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "Kategori harus dipilih",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (namaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama sampah tidak boleh kosong",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            float berat = Float.parseFloat(beratField.getText().trim());
            if (berat <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Berat harus lebih dari 0",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Berat harus berupa angka",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int poin = Integer.parseInt(poinField.getText().trim());
            if (poin < 0) {
                JOptionPane.showMessageDialog(this,
                        "Poin tidak boleh negatif",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Poin harus berupa angka",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}