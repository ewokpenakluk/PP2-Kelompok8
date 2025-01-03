package controller;

import model.HistoryPenjemputanModel;
import model.mapper.HistoryPenjemputanMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class HistoryPenjemputanController {
    private static HistoryPenjemputanController instance;

    public static HistoryPenjemputanController getInstance() {
        if (instance == null) {
            instance = new HistoryPenjemputanController();
        }
        return instance;
    }

    private HistoryPenjemputanController() {
    }

    public List<HistoryPenjemputanModel> getRecentActivities() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            return mapper.findAll();
        } catch (Exception e) {
            throw new Exception("Error getting recent activities: " + e.getMessage());
        }
    }

    public int getTotalPenjemputan() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            return mapper.getTotalHistoryPenjemputan();
        } catch (Exception e) {
            throw new Exception("Error getting total penjemputan: " + e.getMessage());
        }
    }

    public float getTotalBerat() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            HistoryPenjemputanModel stats = mapper.getTotalSampahDanPoin();
            return stats != null ? stats.getTotalBerat() : 0f;
        } catch (Exception e) {
            throw new Exception("Error getting total berat: " + e.getMessage());
        }
    }

    public int getTotalPoin() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            HistoryPenjemputanModel stats = mapper.getTotalSampahDanPoin();
            return stats != null ? stats.getTotalPoin() : 0;
        } catch (Exception e) {
            throw new Exception("Error getting total poin: " + e.getMessage());
        }
    }

    public List<HistoryPenjemputanModel> getTotalPerKategori() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            return mapper.getTotalPerKategori();
        } catch (Exception e) {
            throw new Exception("Error getting stats per kategori: " + e.getMessage());
        }
    }

    public List<HistoryPenjemputanModel> getTop10Kurir() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            return mapper.getTop10Kurir();
        } catch (Exception e) {
            throw new Exception("Error getting top kurir: " + e.getMessage());
        }
    }

    public List<HistoryPenjemputanModel> getTop10Masyarakat() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            return mapper.getTop10Masyarakat();
        } catch (Exception e) {
            throw new Exception("Error getting top masyarakat: " + e.getMessage());
        }
    }

    public List<HistoryPenjemputanModel> getTotalPerDropBox() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            return mapper.getTotalPerDropBox();
        } catch (Exception e) {
            throw new Exception("Error getting stats per dropbox: " + e.getMessage());
        }
    }

    public void save(HistoryPenjemputanModel history) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            mapper.insert(history);
        } catch (Exception e) {
            throw new Exception("Error saving history: " + e.getMessage());
        }
    }

    public void update(HistoryPenjemputanModel history) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            mapper.update(history);
        } catch (Exception e) {
            throw new Exception("Error updating history: " + e.getMessage());
        }
    }

    public void delete(Integer id) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            mapper.delete(id);
        } catch (Exception e) {
            throw new Exception("Error deleting history: " + e.getMessage());
        }
    }

    public HistoryPenjemputanModel getById(Integer id) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            HistoryPenjemputanMapper mapper = session.getMapper(HistoryPenjemputanMapper.class);
            return mapper.getById(id);
        } catch (Exception e) {
            throw new Exception("Error getting history: " + e.getMessage());
        }
    }
}