package controller;

import model.UserModel;
import model.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import model.MyBatisUtil;
import org.mindrot.jbcrypt.BCrypt;
import view.DashboardView;

import javax.swing.*;
import java.time.LocalDateTime;

public class AuthController {
    private static AuthController instance;
    private UserModel currentUser;

    public static AuthController getInstance() {
        if (instance == null) {
            instance = new AuthController();
        }
        return instance;
    }

    private AuthController() {
    }

    public boolean handleRegister(String username, String password, String confirmPassword,
                                  String namaLengkap, String email) {
        // Validasi input
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                namaLengkap.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Semua field harus diisi!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null,
                    "Password dan konfirmasi password tidak cocok!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try (SqlSession session = MyBatisUtil.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            // Cek username sudah ada
            if (mapper.findByUsername(username) != null) {
                JOptionPane.showMessageDialog(null,
                        "Username sudah digunakan!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Buat user baru
            UserModel newUser = new UserModel();
            newUser.setUsername(username);
            newUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            newUser.setNamaLengkap(namaLengkap);
            newUser.setEmail(email);
            newUser.setCreatedAt(LocalDateTime.now());

            mapper.register(newUser);

            JOptionPane.showMessageDialog(null,
                    "Registrasi berhasil! Silakan login.",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void logout() {
        this.currentUser = null;
        // Tambahkan kode untuk kembali ke login screen jika diperlukan
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }
}