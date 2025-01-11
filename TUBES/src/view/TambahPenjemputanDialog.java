package view;

import controller.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class TambahPenjemputanDialog extends JDialog {
    private JComboBox<MasyarakatModel> masyarakatCombo;
    private JComboBox<KurirModel> kurirCombo;
    private JComboBox<SampahModel> sampahCombo;
    private JComboBox<LokasiModel> lokasiCombo;
    private JComboBox<DropBoxModel> dropboxCombo;
    private JTextField beratField;

    public TambahPenjemputanDialog(JFrame parent) {
        super(parent, "Tambah Penjemputan", true);
        initComponents();
    }

    private void initComponents() {
        setSize(500, 400);
        setLocationRelativeTo(getOwner());

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize components
        masyarakatCombo = new JComboBox<>();
        kurirCombo = new JComboBox<>();
        sampahCombo = new JComboBox<>();
        lokasiCombo = new JComboBox<>();
        dropboxCombo = new JComboBox<>();
        beratField = new JTextField(10);

        // Load data for combos
        loadMasyarakatData();
        loadKurirData();
        loadSampahData();
        loadLokasiData();
        loadDropboxData();

        // Add components to panel
        int gridy = 0;

        addFormField(mainPanel, "Masyarakat:", masyarakatCombo, gbc, gridy++);
        addFormField(mainPanel, "Kurir:", kurirCombo, gbc, gridy++);
        addFormField(mainPanel, "Jenis Sampah:", sampahCombo, gbc, gridy++);
        addFormField(mainPanel, "Lokasi:", lokasiCombo, gbc, gridy++);
        addFormField(mainPanel, "Drop Box:", dropboxCombo, gbc, gridy++);
        addFormField(mainPanel, "Total Berat (kg):", beratField, gbc, gridy++);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Simpan");
        JButton cancelButton = new JButton("Batal");

        saveButton.addActionListener(e -> handleSave());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add button panel
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Set renderers for combo boxes
        setComboRenderers();

        add(mainPanel);
    }

    private void addFormField(JPanel panel, String label, JComponent component,
                              GridBagConstraints gbc, int gridy) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(component, gbc);
        gbc.weightx = 0.0;
    }

    private void loadMasyarakatData() {
        try {
            List<MasyarakatModel> list = MasyarakatController.getInstance().getAllMasyarakat();
            for (MasyarakatModel m : list) {
                masyarakatCombo.addItem(m);
            }
        } catch (Exception e) {
            showError("Error loading masyarakat data: " + e.getMessage());
        }
    }

    private void loadKurirData() {
        try {
            List<KurirModel> list = KurirController.getInstance().getAllKurir();
            for (KurirModel k : list) {
                kurirCombo.addItem(k);
            }
        } catch (Exception e) {
            showError("Error loading kurir data: " + e.getMessage());
        }
    }

    private void loadSampahData() {
        try {
            List<SampahModel> list = SampahController.getInstance().getAllSampah();
            for (SampahModel s : list) {
                sampahCombo.addItem(s);
            }
        } catch (Exception e) {
            showError("Error loading sampah data: " + e.getMessage());
        }
    }

    private void loadLokasiData() {
        try {
            List<LokasiModel> list = LokasiController.getInstance().getAllLokasi();
            for (LokasiModel l : list) {
                lokasiCombo.addItem(l);
            }
        } catch (Exception e) {
            showError("Error loading lokasi data: " + e.getMessage());
        }
    }

    private void loadDropboxData() {
        try {
            List<DropBoxModel> list = DropBoxController.getInstance().getAllDropBox();
            for (DropBoxModel d : list) {
                dropboxCombo.addItem(d);
            }
        } catch (Exception e) {
            showError("Error loading dropbox data: " + e.getMessage());
        }
    }

    private void setComboRenderers() {
        masyarakatCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof MasyarakatModel) {
                    value = ((MasyarakatModel) value).getNamaLengkap();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        kurirCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof KurirModel) {
                    value = ((KurirModel) value).getNamaKurir();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        sampahCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof SampahModel) {
                    value = ((SampahModel) value).getNamaSampah();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        lokasiCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof LokasiModel) {
                    value = ((LokasiModel) value).getAlamatLokasi();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        dropboxCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof DropBoxModel) {
                    value = ((DropBoxModel) value).getNamaDropbox();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
    }

    private void handleSave() {
        if (!validateInput()) {
            return;
        }

        try {
            HistoryPenjemputanModel history = new HistoryPenjemputanModel();

            MasyarakatModel masyarakat = (MasyarakatModel) masyarakatCombo.getSelectedItem();
            KurirModel kurir = (KurirModel) kurirCombo.getSelectedItem();
            SampahModel sampah = (SampahModel) sampahCombo.getSelectedItem();
            LokasiModel lokasi = (LokasiModel) lokasiCombo.getSelectedItem();
            DropBoxModel dropbox = (DropBoxModel) dropboxCombo.getSelectedItem();

            history.setIdMasyarakat(masyarakat.getIdMasyarakat());
            history.setIdKurir(kurir.getIdKurir());
            history.setIdSampah(sampah.getIdSampah());
            history.setIdLokasi(lokasi.getIdLokasi());
            history.setIdDropbox(dropbox.getIdDropbox());
            history.setTanggalPenjemputan(LocalDateTime.now());
            history.setTotalBerat(Float.parseFloat(beratField.getText().trim()));

            // Calculate points based on sampah's point value
            int points = Math.round(history.getTotalBerat() * sampah.getPoin());
            history.setTotalPoin(points);

            HistoryPenjemputanController.getInstance().save(history);

            JOptionPane.showMessageDialog(this,
                    "Data penjemputan berhasil disimpan",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (Exception e) {
            showError("Error saving data: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        if (masyarakatCombo.getSelectedItem() == null ||
                kurirCombo.getSelectedItem() == null ||
                sampahCombo.getSelectedItem() == null ||
                lokasiCombo.getSelectedItem() == null ||
                dropboxCombo.getSelectedItem() == null) {

            showError("Semua field harus dipilih");
            return false;
        }

        try {
            float berat = Float.parseFloat(beratField.getText().trim());
            if (berat <= 0) {
                showError("Berat harus lebih dari 0");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Berat harus berupa angka");
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