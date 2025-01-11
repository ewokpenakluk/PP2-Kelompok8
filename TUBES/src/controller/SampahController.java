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
            List<SampahModel> list = mapper.findAll();
            if (list == null) {
                throw new Exception("Tidak ada data sampah");
            }
            return list;
        } catch (Exception e) {
            throw new Exception("Error mengambil data sampah: " + e.getMessage());
        }
    }

    public List<SampahModel> getSampahByKategori(Integer kategoriId) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);
            List<SampahModel> list = mapper.findByKategori(kategoriId);
            if (list == null) {
                throw new Exception("Tidak ada data sampah untuk kategori ini");
            }
            return list;
        } catch (Exception e) {
            throw new Exception("Error mengambil data sampah berdasarkan kategori: " + e.getMessage());
        }
    }

    public SampahModel getById(Integer id) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);
            SampahModel sampah = mapper.getById(id);
            if (sampah == null) {
                throw new Exception("Sampah dengan ID " + id + " tidak ditemukan");
            }
            return sampah;
        } catch (Exception e) {
            throw new Exception("Error mengambil data sampah: " + e.getMessage());
        }
    }

    public void save(SampahModel sampah) throws Exception {
        validateSampah(sampah);

        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);
            int result = mapper.insert(sampah);
            if (result <= 0) {
                throw new Exception("Gagal menyimpan data sampah");
            }
        } catch (Exception e) {
            throw new Exception("Error menyimpan data sampah: " + e.getMessage());
        }
    }

    public void update(SampahModel sampah) throws Exception {
        validateSampah(sampah);

        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);
            int result = mapper.update(sampah);
            if (result <= 0) {
                throw new Exception("Gagal mengupdate data sampah");
            }
        } catch (Exception e) {
            throw new Exception("Error mengupdate data sampah: " + e.getMessage());
        }
    }

    public void delete(Integer idSampah) throws Exception {
        if (idSampah == null) {
            throw new Exception("ID sampah tidak boleh kosong");
        }

        try (SqlSession session = MyBatisUtil.openSession()) {
            SampahMapper mapper = session.getMapper(SampahMapper.class);

            // Verifikasi keberadaan data
            SampahModel existing = mapper.getById(idSampah);
            if (existing == null) {
                throw new Exception("Data sampah tidak ditemukan");
            }

            int result = mapper.delete(idSampah);
            if (result <= 0) {
                throw new Exception("Gagal menghapus data sampah");
            }
        } catch (Exception e) {
            throw new Exception("Error menghapus data sampah: " + e.getMessage());
        }
    }

    private void validateSampah(SampahModel sampah) throws Exception {
        if (sampah == null) {
            throw new Exception("Data sampah tidak boleh kosong");
        }

        if (sampah.getIdKategori() == null) {
            throw new Exception("ID kategori tidak boleh kosong");
        }

        if (sampah.getNamaSampah() == null || sampah.getNamaSampah().trim().isEmpty()) {
            throw new Exception("Nama sampah tidak boleh kosong");
        }

        if (sampah.getBerat() == null || sampah.getBerat() <= 0) {
            throw new Exception("Berat sampah harus lebih dari 0");
        }

        if (sampah.getPoin() == null || sampah.getPoin() < 0) {
            throw new Exception("Poin tidak boleh negatif");
        }
    }

    // Helper method untuk mengecek apakah string berisi angka
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}