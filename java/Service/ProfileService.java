package Service;

import DAO.UpdateProfileMapper;
import Entity.User;
import Entity.UserProfile;
import Utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class ProfileService {
    public static UserProfile getUserProfileById(int userId) {
        SqlSession session = null;
        UserProfile userProfile = null;
        try {
            session = MyBatisUtil.getSession();
            UpdateProfileMapper updateProfileMapper = session.getMapper(UpdateProfileMapper.class);
            userProfile = updateProfileMapper.getUserProfileById(userId);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userProfile;
    }

    public static void updateImagePath(String imagePath, int userId) {
        SqlSession session = null;
        try {
            session = MyBatisUtil.getSession();
            UpdateProfileMapper updateProfileMapper = session.getMapper(UpdateProfileMapper.class);
            int row = updateProfileMapper.updateImagePath(imagePath, userId);
            session.commit();
            if(row > 0) {
                System.out.println("Image added into database successfully.");
            } else {
                System.out.println("Failed to upload image.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    public static void updateUserBio(User user) {
        SqlSession session = null;
        try {
            session = MyBatisUtil.getSession();
            UpdateProfileMapper updateProfileMapper = session.getMapper(UpdateProfileMapper.class);
            updateProfileMapper.updateUserBio(user);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
