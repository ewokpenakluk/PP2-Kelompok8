package model.mapper;

import model.DropBoxModel;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface DropBoxMapper {
    @Select("SELECT * FROM DropBox WHERE id_dropbox = #{idDropbox}")
    @Results({
            @Result(property = "idDropbox", column = "id_dropbox"),
            @Result(property = "namaDropbox", column = "nama_dropbox"),
            @Result(property = "alamat", column = "alamat")
    })
    DropBoxModel getById(Integer idDropbox);

    @Select("SELECT * FROM DropBox")
    @Results({
            @Result(property = "idDropbox", column = "id_dropbox"),
            @Result(property = "namaDropbox", column = "nama_dropbox"),
            @Result(property = "alamat", column = "alamat")
    })
    List<DropBoxModel> findAll();

    @Insert("INSERT INTO DropBox (nama_dropbox, alamat) VALUES (#{namaDropbox}, #{alamat})")
    @Options(useGeneratedKeys = true, keyProperty = "idDropbox")
    int insert(DropBoxModel dropBox);

    @Update("UPDATE DropBox SET nama_dropbox = #{namaDropbox}, alamat = #{alamat} " +
            "WHERE id_dropbox = #{idDropbox}")
    int update(DropBoxModel dropBox);

    @Delete("DELETE FROM DropBox WHERE id_dropbox = #{idDropbox}")
    int delete(Integer idDropbox);
}
