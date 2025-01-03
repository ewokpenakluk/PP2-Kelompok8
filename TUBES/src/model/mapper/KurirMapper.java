package model.mapper;

import model.KurirModel;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface KurirMapper {
    @Select("SELECT * FROM Kurir WHERE id_kurir = #{idKurir}")
    @Results({
            @Result(property = "idKurir", column = "id_kurir"),
            @Result(property = "namaKurir", column = "nama_kurir"),
            @Result(property = "email", column = "email"),
            @Result(property = "noTelepon", column = "no_telepon")
    })
    KurirModel getById(Integer idKurir);

    @Select("SELECT * FROM Kurir")
    @Results({
            @Result(property = "idKurir", column = "id_kurir"),
            @Result(property = "namaKurir", column = "nama_kurir"),
            @Result(property = "email", column = "email"),
            @Result(property = "noTelepon", column = "no_telepon")
    })
    List<KurirModel> findAll();

    @Insert("INSERT INTO Kurir (nama_kurir, email, no_telepon) " +
            "VALUES (#{namaKurir}, #{email}, #{noTelepon})")
    @Options(useGeneratedKeys = true, keyProperty = "idKurir")
    int insert(KurirModel kurir);

    @Update("UPDATE Kurir SET nama_kurir = #{namaKurir}, email = #{email}, " +
            "no_telepon = #{noTelepon} WHERE id_kurir = #{idKurir}")
    int update(KurirModel kurir);

    @Delete("DELETE FROM Kurir WHERE id_kurir = #{idKurir}")
    int delete(Integer idKurir);
}