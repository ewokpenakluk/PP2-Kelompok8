package controller;

import model.SampahModel;
import model.mapper.SampahMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SampahController {
    private static SampahController instance;

    public static SampahController getInstance() {
        if (instance == null) {
            instance = new SampahController();
        }
        return instance;
    }

    private SampahController() {
    }

    public List<SampahModel> getAllSampah() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);
            return mapper.findAll();
        } catch (Exception e) {
            throw new Exception("Error mengambil data sampah: " + e.getMessage());
        }
    }

    public List<SampahModel> getSampahByKategori(Integer kategoriId) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);
            return mapper.findByKategori(kategoriId);
        } catch (Exception e) {
            throw new Exception("Error mengambil data sampah berdasarkan kategori: " + e.getMessage());
        }
    }

    public void save(SampahModel sampah) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);
            mapper.insert(sampah);
        } catch (Exception e) {
            throw new Exception("Error menyimpan data sampah: " + e.getMessage());
        }
    }

    public void update(SampahModel sampah) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);
            mapper.update(sampah);
        } catch (Exception e) {
            throw new Exception("Error mengupdate data sampah: " + e.getMessage());
        }
    }

    public void delete(Integer idSampah) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);
            mapper.delete(idSampah);
        } catch (Exception e) {
            throw new Exception("Error menghapus data sampah: " + e.getMessage());
        }
    }
}