package model.mapper;

import model.LokasiModel;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface LokasiMapper {
    @Select("SELECT * FROM Lokasi WHERE id_lokasi = #{idLokasi}")
    @Results({
            @Result(property = "idLokasi", column = "id_lokasi"),
            @Result(property = "alamatLokasi", column = "alamat_lokasi")
    })
    LokasiModel getById(Integer idLokasi);

    @Select("SELECT * FROM Lokasi")
    @Results({
            @Result(property = "idLokasi", column = "id_lokasi"),
            @Result(property = "alamatLokasi", column = "alamat_lokasi")
    })
    List<LokasiModel> findAll();

    @Insert("INSERT INTO Lokasi (alamat_lokasi) VALUES (#{alamatLokasi})")
    @Options(useGeneratedKeys = true, keyProperty = "idLokasi")
    int insert(LokasiModel lokasi);

    @Update("UPDATE Lokasi SET alamat_lokasi = #{alamatLokasi} WHERE id_lokasi = #{idLokasi}")
    int update(LokasiModel lokasi);

    @Delete("DELETE FROM Lokasi WHERE id_lokasi = #{idLokasi}")
    int delete(Integer idLokasi);
}