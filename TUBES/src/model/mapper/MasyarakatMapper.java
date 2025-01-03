package model.mapper;

import model.MasyarakatModel;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface MasyarakatMapper {
    @Select("SELECT * FROM Masyarakat WHERE id_masyarakat = #{idMasyarakat}")
    @Results({
            @Result(property = "idMasyarakat", column = "id_masyarakat"),
            @Result(property = "namaLengkap", column = "nama_lengkap"),
            @Result(property = "email", column = "email"),
            @Result(property = "noTelepon", column = "no_telepon"),
            @Result(property = "alamat", column = "alamat")
    })
    MasyarakatModel getById(Integer idMasyarakat);

    @Select("SELECT * FROM Masyarakat")
    @Results({
            @Result(property = "idMasyarakat", column = "id_masyarakat"),
            @Result(property = "namaLengkap", column = "nama_lengkap"),
            @Result(property = "email", column = "email"),
            @Result(property = "noTelepon", column = "no_telepon"),
            @Result(property = "alamat", column = "alamat")
    })
    List<MasyarakatModel> findAll();

    @Insert("INSERT INTO Masyarakat (nama_lengkap, email, no_telepon, alamat) " +
            "VALUES (#{namaLengkap}, #{email}, #{noTelepon}, #{alamat})")
    @Options(useGeneratedKeys = true, keyProperty = "idMasyarakat")
    int insert(MasyarakatModel masyarakat);

    @Update("UPDATE Masyarakat SET nama_lengkap = #{namaLengkap}, " +
            "email = #{email}, no_telepon = #{noTelepon}, " +
            "alamat = #{alamat} WHERE id_masyarakat = #{idMasyarakat}")
    int update(MasyarakatModel masyarakat);

    @Delete("DELETE FROM Masyarakat WHERE id_masyarakat = #{idMasyarakat}")
    int delete(Integer idMasyarakat);
}