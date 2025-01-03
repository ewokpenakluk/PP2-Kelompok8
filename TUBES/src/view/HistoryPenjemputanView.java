package view;

import controller.DashboardController;
import controller.HistoryPenjemputanController;
import model.HistoryPenjemputanModel;
import util.PDFExporter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoryPenjemputanView extends JPanel {
    private JTable historyTable;
    private final DateTimeFormatter dateFormatter;
    private static final Font MENU_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Color SECONDARY_COLOR = new Color(66, 165, 245);
    private static final Color HOVER_COLOR = new Color(100, 181, 246);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);

    public HistoryPenjemputanView() {
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(BACKGROUND_COLOR);

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = createActionButton("Tambah Penjemputan");
        JButton exportButton = createActionButton("Export PDF");
        JButton refreshButton = createActionButton("Refresh");

        toolbar.add(addButton);
        toolbar.add(exportButton);
        toolbar.add(refreshButton);

        // Table
        String[] columns = {
                "ID", "Tanggal", "Masyarakat", "Kurir",
                "Jenis Sampah", "Lokasi", "Drop Box",
                "Total Berat (kg)", "Total Poin"
        };
        historyTable = createTable(columns);
        JScrollPane scrollPane = new JScrollPane(historyTable);

        // Action Listeners
        addButton.addActionListener(e -> new TambahPenjemputanDialog((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true));
        exportButton.addActionListener(e -> exportToPDF());
        refreshButton.addActionListener(e -> loadData());

        add(toolbar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(MENU_FONT);
        button.setBackground(SECONDARY_COLOR);
        button.setForeground(TEXT_LIGHT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }
        });

        return button;
    }

    private JTable createTable(String[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(224, 224, 224));

        // Center align text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Right align for numeric columns
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        // Apply renderers
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

    public void loadData() {
        try {
            DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
            model.setRowCount(0);

            List<HistoryPenjemputanModel> histories =
                    HistoryPenjemputanController.getInstance().getRecentActivities();

            if (histories != null) {
                for (HistoryPenjemputanModel history : histories) {
                    model.addRow(new Object[]{
                            history.getIdHistory(),
                            history.getTanggalPenjemputan().format(dateFormatter),
                            history.getMasyarakat().getNamaLengkap(),
                            history.getKurir().getNamaKurir(),
                            history.getSampah().getNamaSampah(),
                            history.getLokasi().getAlamatLokasi(),
                            history.getDropBox() != null ? history.getDropBox().getNamaDropbox() : "-",
                            formatNumber(history.getTotalBerat()),
                            formatNumber(history.getTotalPoin())
                    });
                }
            }

            DashboardController.getInstance().loadData();
        } catch (Exception e) {
            showError("Error memuat data history: " + e.getMessage());
        }
    }

    private String formatNumber(Number value) {
        if (value instanceof Integer || value instanceof Long) {
            return String.format("%,d", value.longValue());
        } else {
            return String.format("%,.2f", value.doubleValue());
        }
    }

    private void exportToPDF() {
        try {
            PDFExporter.exportTableToPDF(historyTable, "LAPORAN PENJEMPUTAN SAMPAH", dateFormatter);
        } catch (Exception e) {
            showError("Error membuat PDF: " + e.getMessage());
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