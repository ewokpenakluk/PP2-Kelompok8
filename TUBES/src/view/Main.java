package view;

import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set Look and Feel sesuai sistem operasi
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Set properti untuk font rendering yang lebih baik
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");

            // Jalankan aplikasi dalam Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                try {
                    // Tes koneksi database menggunakan MyBatis
                    testDatabaseConnection();

                    // Tampilkan login screen
                    LoginView loginView = new LoginView();
                    loginView.setVisible(true);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Terjadi kesalahan: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Terjadi kesalahan: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void testDatabaseConnection() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            if (session == null) {
                throw new RuntimeException("Tidak dapat membuat koneksi database");
            }
        } catch (Exception e) {
            throw new RuntimeException("Tidak dapat terhubung ke database: " + e.getMessage());
        }
    }
}