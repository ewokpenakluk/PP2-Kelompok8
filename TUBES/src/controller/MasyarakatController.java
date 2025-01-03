package controller;

import model.MasyarakatModel;
import model.mapper.MasyarakatMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class MasyarakatController {
    private static MasyarakatController instance;

    public static MasyarakatController getInstance() {
        if (instance == null) {
            instance = new MasyarakatController();
        }
        return instance;
    }

    private MasyarakatController() {
    }

    public List<MasyarakatModel> getAllMasyarakat() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            return mapper.findAll();
        } catch (Exception e) {
            throw new Exception("Error getting masyarakat data: " + e.getMessage());
        }
    }

    public List<MasyarakatModel> searchMasyarakat(String keyword) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            List<MasyarakatModel> allData = mapper.findAll();

            // Filter data berdasarkan keyword (nama atau alamat)
            List<MasyarakatModel> filteredData = new ArrayList<>();
            keyword = keyword.toLowerCase();

            for (MasyarakatModel m : allData) {
                if (m.getNamaLengkap().toLowerCase().contains(keyword) ||
                        m.getAlamat().toLowerCase().contains(keyword) ||
                        m.getEmail().toLowerCase().contains(keyword)) {
                    filteredData.add(m);
                }
            }

            return filteredData;
        } catch (Exception e) {
            throw new Exception("Error searching masyarakat: " + e.getMessage());
        }
    }

    public void save(MasyarakatModel masyarakat) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            mapper.insert(masyarakat);
        } catch (Exception e) {
            throw new Exception("Error saving masyarakat: " + e.getMessage());
        }
    }

    public void update(MasyarakatModel masyarakat) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            mapper.update(masyarakat);
        } catch (Exception e) {
            throw new Exception("Error updating masyarakat: " + e.getMessage());
        }
    }

    public void delete(Integer id) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            mapper.delete(id);
        } catch (Exception e) {
            throw new Exception("Error deleting masyarakat: " + e.getMessage());
        }
    }

    public MasyarakatModel getById(Integer id) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            return mapper.getById(id);
        } catch (Exception e) {
            throw new Exception("Error getting masyarakat: " + e.getMessage());
        }
    }

    // Method tambahan untuk dashboard
    public int getTotalMasyarakat() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            List<MasyarakatModel> masyarakatList = mapper.findAll();
            return masyarakatList.size();
        } catch (Exception e) {
            throw new Exception("Error getting total masyarakat: " + e.getMessage());
        }
    }
}