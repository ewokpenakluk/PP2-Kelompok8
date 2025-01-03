package model.mapper;

import model.KategoriModel;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface KategoriMapper {
    @Select("SELECT * FROM Kategori WHERE id_kategori = #{idKategori}")
    @Results({
            @Result(property = "idKategori", column = "id_kategori"),
            @Result(property = "namaKategori", column = "nama_kategori")
    })
    KategoriModel getById(Integer idKategori);

    @Select("SELECT * FROM Kategori")
    @Results({
            @Result(property = "idKategori", column = "id_kategori"),
            @Result(property = "namaKategori", column = "nama_kategori")
    })
    List<KategoriModel> findAll();

    @Insert("INSERT INTO Kategori (nama_kategori) VALUES (#{namaKategori})")
    @Options(useGeneratedKeys = true, keyProperty = "idKategori")
    int insert(KategoriModel kategori);

    @Update("UPDATE Kategori SET nama_kategori = #{namaKategori} WHERE id_kategori = #{idKategori}")
    int update(KategoriModel kategori);

    @Delete("DELETE FROM Kategori WHERE id_kategori = #{idKategori}")
    int delete(Integer idKategori);
}
