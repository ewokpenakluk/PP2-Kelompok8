package view;

import controller.*;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardView extends JFrame {
    // Konstanta Font
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Font VALUE_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font MENU_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font TABLE_HEADER_FONT = new Font("Arial", Font.BOLD, 12);
    private static final Font TABLE_FONT = new Font("Arial", Font.PLAIN, 12);

    // Konstanta Warna
    private static final Color PRIMARY_COLOR = new Color(25, 118, 210);
    private static final Color SECONDARY_COLOR = new Color(66, 165, 245);
    private static final Color HOVER_COLOR = new Color(100, 181, 246);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(224, 224, 224);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);

    // Komponen Utama
    private JPanel mainContent;
    private CardLayout cardLayout;
    private UserModel currentUser;
    private static final String DASHBOARD_PANEL = "DASHBOARD";
    private static final String HISTORY_PANEL = "HISTORY";
    private final DateTimeFormatter dateFormatter;

    // Label Statistik
    private JLabel totalPenjemputanLabel;
    private JLabel totalBeratLabel;
    private JLabel totalPointLabel;
    private JLabel totalDropBoxLabel;

    // Tabel
    private JTable kategoriTable;
    private JTable kurirTable;
    private JTable masyarakatTable;
    private JTable dropboxTable;

    public DashboardView(UserModel user) {
        this.currentUser = user;
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        initComponents();
        DashboardController.getInstance().setDashboardView(this);
        DashboardController.getInstance().loadData();
    }

    private void initComponents() {
        // Setup Frame
        setTitle("Dashboard Bank Sampah");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout Utama
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Buat dan Tambah Komponen
        JPanel sidebar = createSidebar();
        mainContent = new JPanel(new CardLayout());
        cardLayout = (CardLayout) mainContent.getLayout();

        mainContent.add(createDashboardPanel(), DASHBOARD_PANEL);
        mainContent.add(new HistoryPenjemputanView(), HISTORY_PANEL);

        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBackground(PRIMARY_COLOR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Bagian Header
        JLabel headerLabel = new JLabel("Bank Sampah");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(TEXT_LIGHT);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Selamat Datang,");
        welcomeLabel.setFont(NORMAL_FONT);
        welcomeLabel.setForeground(TEXT_LIGHT);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(currentUser.getNamaLengkap());
        nameLabel.setFont(TITLE_FONT);
        nameLabel.setForeground(TEXT_LIGHT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tambah Komponen Header
        sidebar.add(headerLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(welcomeLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(nameLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));

        // Menu Buttons
        addMenuButton(sidebar, "Dashboard", e -> cardLayout.show(mainContent, DASHBOARD_PANEL));
        addMenuButton(sidebar, "History Penjemputan", e -> cardLayout.show(mainContent, HISTORY_PANEL));
        addMenuButton(sidebar, "Manajemen Dropbox", e -> new DropBoxView().setVisible(true));
        addMenuButton(sidebar, "Manajemen Kategori", e -> new KategoriView().setVisible(true));
        addMenuButton(sidebar, "Manajemen Sampah", e -> new SampahView().setVisible(true));
        addMenuButton(sidebar, "Manajemen Kurir", e -> new KurirView().setVisible(true));
        addMenuButton(sidebar, "Manajemen Masyarakat", e -> new MasyarakatView().setVisible(true));
        addMenuButton(sidebar, "Manajemen Lokasi", e -> new LokasiView().setVisible(true));

        sidebar.add(Box.createVerticalGlue());
        addMenuButton(sidebar, "Logout", e -> handleLogout());

        return sidebar;
    }

    private void addMenuButton(JPanel sidebar, String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(MENU_FONT);
        button.setMaximumSize(new Dimension(220, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(SECONDARY_COLOR);
        button.setForeground(TEXT_LIGHT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efek Hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }
        });

        button.addActionListener(listener);

        sidebar.add(button);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(BACKGROUND_COLOR);

        // Panel Statistik
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);

        // Inisialisasi Label
        totalPenjemputanLabel = new JLabel("0", SwingConstants.CENTER);
        totalBeratLabel = new JLabel("0.00", SwingConstants.CENTER);
        totalPointLabel = new JLabel("0", SwingConstants.CENTER);
        totalDropBoxLabel = new JLabel("0", SwingConstants.CENTER);

        // Style Label
        totalPenjemputanLabel.setFont(VALUE_FONT);
        totalBeratLabel.setFont(VALUE_FONT);
        totalPointLabel.setFont(VALUE_FONT);
        totalDropBoxLabel.setFont(VALUE_FONT);

        // Tambah Kartu Statistik
        statsPanel.add(createStatCard("Total Penjemputan", totalPenjemputanLabel));
        statsPanel.add(createStatCard("Total Berat (kg)", totalBeratLabel));
        statsPanel.add(createStatCard("Total Poin", totalPointLabel));
        statsPanel.add(createStatCard("Total Drop Box", totalDropBoxLabel));

        // Panel Tabel
        JPanel tablesPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        tablesPanel.setOpaque(false);

        // Inisialisasi Tabel
        String[] kategoriColumns = {"Kategori", "Total Penjemputan", "Total Berat", "Total Poin"};
        String[] kurirColumns = {"Nama Kurir", "Total Penjemputan", "Total Berat"};
        String[] masyarakatColumns = {"Nama", "Total Penjemputan", "Total Poin"};
        String[] dropboxColumns = {"Drop Box", "Total Penjemputan", "Total Berat"};

        kategoriTable = createTable(kategoriColumns);
        kurirTable = createTable(kurirColumns);
        masyarakatTable = createTable(masyarakatColumns);
        dropboxTable = createTable(dropboxColumns);

        // Tambah Tabel ke Panel
        tablesPanel.add(createTablePanel("Statistik per Kategori", kategoriTable));
        tablesPanel.add(createTablePanel("Top 10 Kurir", kurirTable));
        tablesPanel.add(createTablePanel("Top 10 Masyarakat", masyarakatTable));
        tablesPanel.add(createTablePanel("Statistik per Drop Box", dropboxTable));

        panel.add(statsPanel, BorderLayout.NORTH);
        panel.add(tablesPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(SUBTITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(valueLabel);

        return card;
    }

    private JPanel createTablePanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(SUBTITLE_FONT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTable createTable(String[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(TABLE_FONT);
        table.getTableHeader().setFont(TABLE_HEADER_FONT);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(BORDER_COLOR);

        // Perataan Teks di Sel
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Perataan Kanan untuk Kolom Numerik
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        // Terapkan Renderer
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (isNumericColumn(table.getColumnName(i))) {
                table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            } else {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        return table;
    }

    private boolean isNumericColumn(String columnName) {
        return columnName.toLowerCase().contains("total") ||
                columnName.toLowerCase().contains("berat") ||
                columnName.toLowerCase().contains("poin");
    }

    public void updateStatistics(int totalPenjemputan, float totalBerat, int totalPoin, int totalDropBox) {
        SwingUtilities.invokeLater(() -> {
            totalPenjemputanLabel.setText(String.format("%,d", totalPenjemputan));
            totalBeratLabel.setText(String.format("%,.2f", totalBerat));
            totalPointLabel.setText(String.format("%,d", totalPoin));
            totalDropBoxLabel.setText(String.format("%,d", totalDropBox));
        });
    }

    public void updateKategoriTable(List<HistoryPenjemputanModel> stats) {
        updateTable(kategoriTable, stats, (model, stat) -> {
            if (stat.getKategori() != null) {
                model.addRow(new Object[]{
                        stat.getKategori().getNamaKategori(),
                        formatNumber(stat.getJumlahPenjemputan()),
                        formatNumber(stat.getTotalBerat()),
                        formatNumber(stat.getTotalPoin())
                });
            }
        });
    }

    public void updateKurirTable(List<HistoryPenjemputanModel> stats) {
        updateTable(kurirTable, stats, (model, stat) -> {
            if (stat.getKurir() != null) {
                model.addRow(new Object[]{
                        stat.getKurir().getNamaKurir(),
                        formatNumber(stat.getJumlahPenjemputan()),
                        formatNumber(stat.getTotalBerat())
                });
            }
        });
    }

    public void updateMasyarakatTable(List<HistoryPenjemputanModel> stats) {
        updateTable(masyarakatTable, stats, (model, stat) -> {
            if (stat.getMasyarakat() != null) {
                model.addRow(new Object[]{
                        stat.getMasyarakat().getNamaLengkap(),
                        formatNumber(stat.getJumlahPenjemputan()),
                        formatNumber(stat.getTotalPoin())
                });
            }
        });
    }

    public void updateDropBoxTable(List<HistoryPenjemputanModel> stats) {
        updateTable(dropboxTable, stats, (model, stat) -> {
            if (stat.getDropBox() != null) {
                model.addRow(new Object[]{
                        stat.getDropBox().getNamaDropbox(),
                        formatNumber(stat.getJumlahPenjemputan()),
                        formatNumber(stat.getTotalBerat())
                });
            }
        });
    }

    private void updateTable(JTable table, List<HistoryPenjemputanModel> stats,
                             TableUpdater updater) {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            if (stats != null) {
                stats.forEach(stat -> updater.update(model, stat));
            }
        });
    }

    @FunctionalInterface
    private interface TableUpdater {
        void update(DefaultTableModel model, HistoryPenjemputanModel stat);
    }

    private String formatNumber(Number value) {
        if (value instanceof Integer || value instanceof Long) {
            return String.format("%,d", value.longValue());
        } else {
            return String.format("%,.2f", value.doubleValue());
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin keluar?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            AuthController.getInstance().logout();
            new LoginView().setVisible(true);
        }
    }

    private void showError(String message) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(this,
                        message,
                        "Error",
                        JOptionPane.ERROR_MESSAGE));
    }
}