package model.mapper;

import model.HistoryPenjemputanModel;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface HistoryPenjemputanMapper {
    // ============= CRUD Operations =============
    @Select("SELECT h.*, m.nama_lengkap, k.nama_kurir, s.nama_sampah, " +
            "l.alamat_lokasi, d.nama_dropbox, kat.nama_kategori " +
            "FROM HistoryPenjemputan h " +
            "JOIN Masyarakat m ON h.id_masyarakat = m.id_masyarakat " +
            "JOIN Kurir k ON h.id_kurir = k.id_kurir " +
            "JOIN Sampah s ON h.id_sampah = s.id_sampah " +
            "JOIN Lokasi l ON h.id_lokasi = l.id_lokasi " +
            "LEFT JOIN DropBox d ON h.id_dropbox = d.id_dropbox " +
            "JOIN Kategori kat ON s.id_kategori = kat.id_kategori " +
            "WHERE h.id_history = #{idHistory}")
    @Results(id = "historyResultMap", value = {
            @Result(property = "idHistory", column = "id_history"),
            @Result(property = "idMasyarakat", column = "id_masyarakat"),
            @Result(property = "idKurir", column = "id_kurir"),
            @Result(property = "idSampah", column = "id_sampah"),
            @Result(property = "idLokasi", column = "id_lokasi"),
            @Result(property = "idDropbox", column = "id_dropbox"),
            @Result(property = "tanggalPenjemputan", column = "tanggal_penjemputan"),
            @Result(property = "totalBerat", column = "total_berat"),
            @Result(property = "totalPoin", column = "total_poin"),
            @Result(property = "masyarakat.namaLengkap", column = "nama_lengkap"),
            @Result(property = "kurir.namaKurir", column = "nama_kurir"),
            @Result(property = "sampah.namaSampah", column = "nama_sampah"),
            @Result(property = "lokasi.alamatLokasi", column = "alamat_lokasi"),
            @Result(property = "dropBox.namaDropbox", column = "nama_dropbox"),
            @Result(property = "kategori.namaKategori", column = "nama_kategori")
    })
    HistoryPenjemputanModel getById(Integer idHistory);

    @Select("SELECT h.*, m.nama_lengkap, k.nama_kurir, s.nama_sampah, " +
            "l.alamat_lokasi, d.nama_dropbox, kat.nama_kategori " +
            "FROM HistoryPenjemputan h " +
            "JOIN Masyarakat m ON h.id_masyarakat = m.id_masyarakat " +
            "JOIN Kurir k ON h.id_kurir = k.id_kurir " +
            "JOIN Sampah s ON h.id_sampah = s.id_sampah " +
            "JOIN Lokasi l ON h.id_lokasi = l.id_lokasi " +
            "LEFT JOIN DropBox d ON h.id_dropbox = d.id_dropbox " +
            "JOIN Kategori kat ON s.id_kategori = kat.id_kategori " +
            "ORDER BY h.tanggal_penjemputan DESC")
    @ResultMap("historyResultMap")
    List<HistoryPenjemputanModel> findAll();

    @Insert("INSERT INTO HistoryPenjemputan (id_masyarakat, id_kurir, id_sampah, " +
            "id_lokasi, id_dropbox, tanggal_penjemputan, total_berat, total_poin) " +
            "VALUES (#{idMasyarakat}, #{idKurir}, #{idSampah}, #{idLokasi}, " +
            "#{idDropbox}, #{tanggalPenjemputan}, #{totalBerat}, #{totalPoin})")
    @Options(useGeneratedKeys = true, keyProperty = "idHistory")
    int insert(HistoryPenjemputanModel history);

    @Update("UPDATE HistoryPenjemputan SET id_masyarakat = #{idMasyarakat}, " +
            "id_kurir = #{idKurir}, id_sampah = #{idSampah}, " +
            "id_lokasi = #{idLokasi}, id_dropbox = #{idDropbox}, " +
            "tanggal_penjemputan = #{tanggalPenjemputan}, " +
            "total_berat = #{totalBerat}, total_poin = #{totalPoin} " +
            "WHERE id_history = #{idHistory}")
    int update(HistoryPenjemputanModel history);

    @Delete("DELETE FROM HistoryPenjemputan WHERE id_history = #{idHistory}")
    int delete(Integer idHistory);

    // ============= Analytics Operations =============

    // Total Keseluruhan
    @Select("SELECT SUM(total_berat) as total_berat, SUM(total_poin) as total_poin " +
            "FROM HistoryPenjemputan")
    @Results({
            @Result(property = "totalBerat", column = "total_berat"),
            @Result(property = "totalPoin", column = "total_poin")
    })
    HistoryPenjemputanModel getTotalSampahDanPoin();

    // Total History
    @Select("SELECT COUNT(*) FROM HistoryPenjemputan")
    int getTotalHistoryPenjemputan();

    // Per Kategori
    @Select("SELECT kat.nama_kategori, kat.id_kategori, " +
            "COUNT(*) as jumlah_penjemputan, " +
            "SUM(h.total_berat) as total_berat, " +
            "SUM(h.total_poin) as total_poin " +
            "FROM HistoryPenjemputan h " +
            "JOIN Sampah s ON h.id_sampah = s.id_sampah " +
            "JOIN Kategori kat ON s.id_kategori = kat.id_kategori " +
            "GROUP BY kat.id_kategori, kat.nama_kategori")
    @Results({
            @Result(property = "kategori.idKategori", column = "id_kategori"),
            @Result(property = "kategori.namaKategori", column = "nama_kategori"),
            @Result(property = "jumlahPenjemputan", column = "jumlah_penjemputan"),
            @Result(property = "totalBerat", column = "total_berat"),
            @Result(property = "totalPoin", column = "total_poin")
    })
    List<HistoryPenjemputanModel> getTotalPerKategori();

    // Per Lokasi
    @Select("SELECT l.alamat_lokasi, l.id_lokasi, " +
            "COUNT(*) as jumlah_penjemputan, " +
            "SUM(h.total_berat) as total_berat, " +
            "SUM(h.total_poin) as total_poin " +
            "FROM HistoryPenjemputan h " +
            "JOIN Lokasi l ON h.id_lokasi = l.id_lokasi " +
            "GROUP BY l.id_lokasi, l.alamat_lokasi")
    @Results({
            @Result(property = "lokasi.idLokasi", column = "id_lokasi"),
            @Result(property = "lokasi.alamatLokasi", column = "alamat_lokasi"),
            @Result(property = "jumlahPenjemputan", column = "jumlah_penjemputan"),
            @Result(property = "totalBerat", column = "total_berat"),
            @Result(property = "totalPoin", column = "total_poin")
    })
    List<HistoryPenjemputanModel> getTotalPerLokasi();

    // Per DropBox
    @Select("SELECT d.nama_dropbox, d.id_dropbox, " +
            "COUNT(*) as jumlah_penjemputan, " +
            "SUM(h.total_berat) as total_berat, " +
            "SUM(h.total_poin) as total_poin " +
            "FROM HistoryPenjemputan h " +
            "JOIN DropBox d ON h.id_dropbox = d.id_dropbox " +
            "GROUP BY d.id_dropbox, d.nama_dropbox")
    @Results({
            @Result(property = "dropBox.idDropbox", column = "id_dropbox"),
            @Result(property = "dropBox.namaDropbox", column = "nama_dropbox"),
            @Result(property = "jumlahPenjemputan", column = "jumlah_penjemputan"),
            @Result(property = "totalBerat", column = "total_berat"),
            @Result(property = "totalPoin", column = "total_poin")
    })
    List<HistoryPenjemputanModel> getTotalPerDropBox();

    // Top 10 Kurir
    @Select("SELECT k.id_kurir, k.nama_kurir, " +
            "COUNT(*) as jumlah_penjemputan, " +
            "SUM(h.total_berat) as total_berat, " +
            "SUM(h.total_poin) as total_poin " +
            "FROM HistoryPenjemputan h " +
            "JOIN Kurir k ON h.id_kurir = k.id_kurir " +
            "GROUP BY k.id_kurir, k.nama_kurir " +
            "ORDER BY jumlah_penjemputan DESC LIMIT 10")
    @Results({
            @Result(property = "kurir.idKurir", column = "id_kurir"),
            @Result(property = "kurir.namaKurir", column = "nama_kurir"),
            @Result(property = "jumlahPenjemputan", column = "jumlah_penjemputan"),
            @Result(property = "totalBerat", column = "total_berat"),
            @Result(property = "totalPoin", column = "total_poin")
    })
    List<HistoryPenjemputanModel> getTop10Kurir();

    // Top 10 Masyarakat
    @Select("SELECT m.id_masyarakat, m.nama_lengkap, " +
            "COUNT(*) as jumlah_penjemputan, " +
            "SUM(h.total_berat) as total_berat, " +
            "SUM(h.total_poin) as total_poin " +
            "FROM HistoryPenjemputan h " +
            "JOIN Masyarakat m ON h.id_masyarakat = m.id_masyarakat " +
            "GROUP BY m.id_masyarakat, m.nama_lengkap " +
            "ORDER BY jumlah_penjemputan DESC LIMIT 10")
    @Results({
            @Result(property = "masyarakat.idMasyarakat", column = "id_masyarakat"),
            @Result(property = "masyarakat.namaLengkap", column = "nama_lengkap"),
            @Result(property = "jumlahPenjemputan", column = "jumlah_penjemputan"),
            @Result(property = "totalBerat", column = "total_berat"),
            @Result(property = "totalPoin", column = "total_poin")
    })
    List<HistoryPenjemputanModel> getTop10Masyarakat();

    // Top 10 Jenis Sampah
    @Select("SELECT s.id_sampah, s.nama_sampah, " +
            "COUNT(*) as jumlah_penjemputan, " +
            "SUM(h.total_berat) as total_berat, " +
            "SUM(h.total_poin) as total_poin " +
            "FROM HistoryPenjemputan h " +
            "JOIN Sampah s ON h.id_sampah = s.id_sampah " +
            "GROUP BY s.id_sampah, s.nama_sampah " +
            "ORDER BY jumlah_penjemputan DESC LIMIT 10")
    @Results({
            @Result(property = "sampah.idSampah", column = "id_sampah"),
            @Result(property = "sampah.namaSampah", column = "nama_sampah"),
            @Result(property = "jumlahPenjemputan", column = "jumlah_penjemputan"),
            @Result(property = "totalBerat", column = "total_berat"),
            @Result(property = "totalPoin", column = "total_poin")
    })
    List<HistoryPenjemputanModel> getTop10JenisSampah();
}