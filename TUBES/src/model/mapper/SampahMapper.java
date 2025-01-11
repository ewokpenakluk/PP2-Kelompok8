package model.mapper;

import model.SampahModel;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface SampahMapper {
    @Select("SELECT s.*, k.nama_kategori FROM Sampah s " +
            "JOIN Kategori k ON s.id_kategori = k.id_kategori " +
            "WHERE s.id_sampah = #{idSampah}")
    @Results({
            @Result(property = "idSampah", column = "id_sampah"),
            @Result(property = "idKategori", column = "id_kategori"),
            @Result(property = "namaSampah", column = "nama_sampah"),
            @Result(property = "berat", column = "berat"),
            @Result(property = "poin", column = "poin"),
            @Result(property = "kategori.namaKategori", column = "nama_kategori")
    })
    SampahModel getById(Integer idSampah);

    @Select("SELECT s.*, k.nama_kategori FROM Sampah s " +
            "JOIN Kategori k ON s.id_kategori = k.id_kategori")
    @Results({
            @Result(property = "idSampah", column = "id_sampah"),
            @Result(property = "idKategori", column = "id_kategori"),
            @Result(property = "namaSampah", column = "nama_sampah"),
            @Result(property = "berat", column = "berat"),
            @Result(property = "poin", column = "poin"),
            @Result(property = "kategori.namaKategori", column = "nama_kategori")
    })
    List<SampahModel> findAll();

    @Select("SELECT s.*, k.nama_kategori FROM Sampah s " +
            "JOIN Kategori k ON s.id_kategori = k.id_kategori " +
            "WHERE s.id_kategori = #{idKategori}")
    @Results({
            @Result(property = "idSampah", column = "id_sampah"),
            @Result(property = "idKategori", column = "id_kategori"),
            @Result(property = "namaSampah", column = "nama_sampah"),
            @Result(property = "berat", column = "berat"),
            @Result(property = "poin", column = "poin"),
            @Result(property = "kategori.namaKategori", column = "nama_kategori")
    })
    List<SampahModel> findByKategori(Integer idKategori);

    @Insert("INSERT INTO Sampah (id_kategori, nama_sampah, berat, poin) " +
            "VALUES (#{idKategori}, #{namaSampah}, #{berat}, #{poin})")
    @Options(useGeneratedKeys = true, keyProperty = "idSampah")
    int insert(SampahModel sampah);

    @Update("UPDATE Sampah SET id_kategori = #{idKategori}, " +
            "nama_sampah = #{namaSampah}, berat = #{berat}, poin = #{poin} " +
            "WHERE id_sampah = #{idSampah}")
    int update(SampahModel sampah);

    @Delete("DELETE FROM Sampah WHERE id_sampah = #{idSampah}")
    int delete(Integer idSampah);
}