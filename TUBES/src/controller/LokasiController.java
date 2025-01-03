package controller;

import model.LokasiModel;
import model.mapper.LokasiMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class LokasiController {
    private static LokasiController instance;

    public static LokasiController getInstance() {
        if (instance == null) {
            instance = new LokasiController();
        }
        return instance;
    }

    private LokasiController() {
    }

    // Method untuk mendapatkan semua data lokasi
    public List<LokasiModel> getAllLokasi() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            LokasiMapper mapper = session.getMapper(LokasiMapper.class);
            return mapper.findAll();
        } catch (Exception e) {
            throw new Exception("Error mengambil data lokasi: " + e.getMessage());
        }
    }

    // Method untuk menyimpan lokasi baru
    public void save(LokasiModel lokasi) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            LokasiMapper mapper = session.getMapper(LokasiMapper.class);
            mapper.insert(lokasi);
        } catch (Exception e) {
            throw new Exception("Error menyimpan lokasi: " + e.getMessage());
        }
    }

    // Method untuk mengupdate lokasi
    public void update(LokasiModel lokasi) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            LokasiMapper mapper = session.getMapper(LokasiMapper.class);
            mapper.update(lokasi);
        } catch (Exception e) {
            throw new Exception("Error mengupdate lokasi: " + e.getMessage());
        }
    }

    // Method untuk menghapus lokasi
    public void delete(Integer idLokasi) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            LokasiMapper mapper = session.getMapper(LokasiMapper.class);
            mapper.delete(idLokasi);
        } catch (Exception e) {
            throw new Exception("Error menghapus lokasi: " + e.getMessage());
        }
    }
}