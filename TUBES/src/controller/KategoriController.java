package controller;

import model.KategoriModel;
import model.mapper.KategoriMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class KategoriController {
    private static KategoriController instance;

    public static KategoriController getInstance() {
        if (instance == null) {
            instance = new KategoriController();
        }
        return instance;
    }

    private KategoriController() {
    }

    public List<KategoriModel> getAllKategori() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KategoriMapper mapper = session.getMapper(KategoriMapper.class);
            return mapper.findAll();
        } catch (Exception e) {
            throw new Exception("Error mengambil data kategori: " + e.getMessage());
        }
    }

    public void save(KategoriModel kategori) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KategoriMapper mapper = session.getMapper(KategoriMapper.class);
            mapper.insert(kategori);
        } catch (Exception e) {
            throw new Exception("Error menyimpan kategori: " + e.getMessage());
        }
    }

    public void update(KategoriModel kategori) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KategoriMapper mapper = session.getMapper(KategoriMapper.class);
            mapper.update(kategori);
        } catch (Exception e) {
            throw new Exception("Error mengupdate kategori: " + e.getMessage());
        }
    }

    public void delete(Integer idKategori) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            KategoriMapper mapper = session.getMapper(KategoriMapper.class);
            mapper.delete(idKategori);
        } catch (Exception e) {
            throw new Exception("Error menghapus kategori: " + e.getMessage());
        }
    }
}