package util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PDFExporter {

    public static void exportTableToPDF(JTable table, String title, DateTimeFormatter dateFormatter) throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan PDF");
        fileChooser.setSelectedFile(new java.io.File("laporan_penjemputan.pdf"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (!filename.toLowerCase().endsWith(".pdf")) {
                filename += ".pdf";
            }

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Add title
            Paragraph titleParagraph = new Paragraph(title);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            titleParagraph.setSpacingAfter(20);
            document.add(titleParagraph);

            // Add timestamp
            Paragraph timestamp = new Paragraph(
                    "Dicetak pada: " + LocalDateTime.now().format(dateFormatter)
            );
            timestamp.setSpacingAfter(20);
            document.add(timestamp);

            // Create table
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);

            // Add headers
            TableModel model = table.getModel();
            for (int i = 0; i < model.getColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new Phrase(model.getColumnName(i)));
                cell.setBackgroundColor(new Color(240, 240, 240));
                cell.setPadding(5);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell);
            }

            // Add data
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    PdfPCell cell = new PdfPCell(new Phrase(
                            value != null ? value.toString() : ""
                    ));
                    cell.setPadding(5);

                    // Right align numeric columns
                    if (isNumericColumn(model.getColumnName(col))) {
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    }

                    pdfTable.addCell(cell);
                }
            }

            document.add(pdfTable);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Total Data: " + table.getRowCount()));

            document.close();

            JOptionPane.showMessageDialog(null,
                    "PDF berhasil dibuat: " + filename,
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static boolean isNumericColumn(String columnName) {
        return columnName.toLowerCase().contains("total") ||
                columnName.toLowerCase().contains("berat") ||
                columnName.toLowerCase().contains("poin");
    }
}