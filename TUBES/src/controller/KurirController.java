package controller;

import model.KurirModel;
import model.mapper.KurirMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class KurirController {
    private static KurirController instance;

    public static KurirController getInstance() {
        if (instance == null) {
            instance = new KurirController();
        }
        return instance;
    }

    private KurirController() {
    }

    public List<KurirModel> getAllKurir() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            return mapper.findAll();
        } catch (Exception e) {
            throw new Exception("Error getting kurir data: " + e.getMessage());
        }
    }

    public void save(KurirModel kurir) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            mapper.insert(kurir);
        } catch (Exception e) {
            throw new Exception("Error saving kurir: " + e.getMessage());
        }
    }

    public void update(KurirModel kurir) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            mapper.update(kurir);
        } catch (Exception e) {
            throw new Exception("Error updating kurir: " + e.getMessage());
        }
    }

    public void delete(Integer id) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            mapper.delete(id);
        } catch (Exception e) {
            throw new Exception("Error deleting kurir: " + e.getMessage());
        }
    }

    public KurirModel getById(Integer id) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            return mapper.getById(id);
        } catch (Exception e) {
            throw new Exception("Error getting kurir: " + e.getMessage());
        }
    }

    // Method tambahan untuk statistik dashboard jika diperlukan
    public int getTotalKurir() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            List<KurirModel> kurirs = mapper.findAll();
            return kurirs.size();
        } catch (Exception e) {
            throw new Exception("Error getting total kurir: " + e.getMessage());
        }
    }
}