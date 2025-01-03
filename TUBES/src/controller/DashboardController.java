package controller;

import model.*;
import model.mapper.*;
import org.apache.ibatis.session.SqlSession;
import model.MyBatisUtil;
import view.DashboardView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    private static DashboardController instance;
    private DashboardView dashboardView;
    private UserModel currentUser;

    public static DashboardController getInstance() {
        if (instance == null) {
            instance = new DashboardController();
        }
        return instance;
    }

    private DashboardController() {
    }

    public void setDashboardView(DashboardView view) {
        this.dashboardView = view;
    }

    public void showDashboard(UserModel user) {
        this.currentUser = user;
        if (dashboardView != null) {
            dashboardView.dispose();
        }
        dashboardView = new DashboardView(user);
        dashboardView.setVisible(true);
    }

    public void refreshData() {
        if (dashboardView != null) {
            System.out.println("Refreshing dashboard data...");
            loadData();
        }
    }

    public void loadData() {
        if (dashboardView == null) return;

        try {
            System.out.println("Loading dashboard data...");

            // 1. Mengambil data total
            int totalPenjemputan = getTotalPenjemputan();
            float totalBerat = getTotalBerat();
            int totalPoin = getTotalPoin();
            int totalDropBox = getTotalDropBox();

            System.out.println("Total Penjemputan: " + totalPenjemputan);
            System.out.println("Total Berat: " + totalBerat);
            System.out.println("Total Poin: " + totalPoin);
            System.out.println("Total DropBox: " + totalDropBox);

            // Update statistik dasar
            dashboardView.updateStatistics(
                    totalPenjemputan,
                    totalBerat,
                    totalPoin,
                    totalDropBox
            );

            // 2. Mengambil statistik per kategori
            List<HistoryPenjemputanModel> kategoriStats = getTotalPerKategori();
            System.out.println("Loaded kategori stats: " + (kategoriStats != null ? kategoriStats.size() : 0) + " records");
            if (kategoriStats != null && !kategoriStats.isEmpty()) {
                dashboardView.updateKategoriTable(kategoriStats);
            }

            // 3. Mengambil statistik top kurir
            List<HistoryPenjemputanModel> kurirStats = getTop10Kurir();
            System.out.println("Loaded kurir stats: " + (kurirStats != null ? kurirStats.size() : 0) + " records");
            if (kurirStats != null && !kurirStats.isEmpty()) {
                dashboardView.updateKurirTable(kurirStats);
            }

            // 4. Mengambil statistik top masyarakat
            List<HistoryPenjemputanModel> masyarakatStats = getTop10Masyarakat();
            System.out.println("Loaded masyarakat stats: " + (masyarakatStats != null ? masyarakatStats.size() : 0) + " records");
            if (masyarakatStats != null && !masyarakatStats.isEmpty()) {
                dashboardView.updateMasyarakatTable(masyarakatStats);
            }

            // 5. Mengambil statistik per dropbox
            List<HistoryPenjemputanModel> dropboxStats = getTotalPerDropBox();
            System.out.println("Loaded dropbox stats: " + (dropboxStats != null ? dropboxStats.size() : 0) + " records");
            if (dropboxStats != null && !dropboxStats.isEmpty()) {
                dashboardView.updateDropBoxTable(dropboxStats);
            }

            // 6. Mengambil aktivitas terbaru
            List<HistoryPenjemputanModel> recentActivities = getRecentActivities();
            if (recentActivities != null && !recentActivities.isEmpty()) {
                // Update tabel history jika ada
                System.out.println("Loaded recent activities: " + recentActivities.size() + " records");
            }

            System.out.println("Dashboard data loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading dashboard data: " + e.getMessage());
            showError("Error memuat data dashboard: " + e.getMessage());
        }
    }

    private int getTotalPenjemputan() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            return mapper.getTotalHistoryPenjemputan();
        } catch (Exception e) {
            throw new Exception("Error getting total penjemputan: " + e.getMessage());
        }
    }

    private float getTotalBerat() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            HistoryPenjemputanModel stats = mapper.getTotalSampahDanPoin();
            return stats != null ? stats.getTotalBerat() : 0f;
        } catch (Exception e) {
            throw new Exception("Error getting total berat: " + e.getMessage());
        }
    }

    private int getTotalPoin() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            HistoryPenjemputanModel stats = mapper.getTotalSampahDanPoin();
            return stats != null ? stats.getTotalPoin() : 0;
        } catch (Exception e) {
            throw new Exception("Error getting total poin: " + e.getMessage());
        }
    }

    private int getTotalDropBox() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            DropBoxMapper mapper = session.getMapper(DropBoxMapper.class);
            List<DropBoxModel> dropBoxes = mapper.findAll();
            return dropBoxes != null ? dropBoxes.size() : 0;
        } catch (Exception e) {
            throw new Exception("Error getting total dropbox: " + e.getMessage());
        }
    }

    private List<HistoryPenjemputanModel> getTotalPerKategori() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            List<HistoryPenjemputanModel> stats = mapper.getTotalPerKategori();
            return stats != null ? stats : new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Error getting stats per kategori: " + e.getMessage());
        }
    }

    private List<HistoryPenjemputanModel> getTop10Kurir() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            List<HistoryPenjemputanModel> stats = mapper.getTop10Kurir();
            return stats != null ? stats : new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Error getting top kurir: " + e.getMessage());
        }
    }

    private List<HistoryPenjemputanModel> getTop10Masyarakat() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            List<HistoryPenjemputanModel> stats = mapper.getTop10Masyarakat();
            return stats != null ? stats : new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Error getting top masyarakat: " + e.getMessage());
        }
    }

    private List<HistoryPenjemputanModel> getTotalPerDropBox() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            List<HistoryPenjemputanModel> stats = mapper.getTotalPerDropBox();
            return stats != null ? stats : new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Error getting stats per dropbox: " + e.getMessage());
        }
    }

    public List<HistoryPenjemputanModel> getRecentActivities() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            List<HistoryPenjemputanModel> histories = mapper.findAll();
            return histories != null ? histories : new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Error getting recent activities: " + e.getMessage());
        }
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }

    private void showError(String message) {
        if (dashboardView != null) {
            JOptionPane.showMessageDialog(dashboardView,
                    message,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            System.err.println("Error: " + message);
        }
    }
}