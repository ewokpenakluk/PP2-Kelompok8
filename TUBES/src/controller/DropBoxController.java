package controller;

import model.DropBoxModel;
import model.mapper.DropBoxMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class DropBoxController {
    private static DropBoxController instance;

    public static DropBoxController getInstance() {
        if (instance == null) {
            instance = new DropBoxController();
        }
        return instance;
    }

    private DropBoxController() {
    }

    public List<DropBoxModel> getAllDropBox() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            DropBoxMapper mapper = session.getMapper(DropBoxMapper.class);
            return mapper.findAll();
        } catch (Exception e) {
            throw new Exception("Error getting dropbox data: " + e.getMessage());
        }
    }

    public int getTotalDropBox() throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            DropBoxMapper mapper = session.getMapper(DropBoxMapper.class);
            List<DropBoxModel> dropBoxes = mapper.findAll();
            return dropBoxes.size();
        } catch (Exception e) {
            throw new Exception("Error getting total dropbox: " + e.getMessage());
        }
    }

    public void save(DropBoxModel dropBox) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            DropBoxMapper mapper = session.getMapper(DropBoxMapper.class);
            mapper.insert(dropBox);
        } catch (Exception e) {
            throw new Exception("Error saving dropbox: " + e.getMessage());
        }
    }

    public void update(DropBoxModel dropBox) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            DropBoxMapper mapper = session.getMapper(DropBoxMapper.class);
            mapper.update(dropBox);
        } catch (Exception e) {
            throw new Exception("Error updating dropbox: " + e.getMessage());
        }
    }

    public void delete(Integer id) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            DropBoxMapper mapper = session.getMapper(DropBoxMapper.class);
            mapper.delete(id);
        } catch (Exception e) {
            throw new Exception("Error deleting dropbox: " + e.getMessage());
        }
    }

    public DropBoxModel getById(Integer id) throws Exception {
        try (SqlSession session = MyBatisUtil.openSession()) {
            DropBoxMapper mapper = session.getMapper(DropBoxMapper.class);
            return mapper.getById(id);
        } catch (Exception e) {
            throw new Exception("Error getting dropbox: " + e.getMessage());
        }
    }
}