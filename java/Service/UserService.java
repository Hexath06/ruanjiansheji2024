package Service;

import DAO.UserMapper;
import Entity.User;
import Utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class UserService {
    public static boolean login(User user) {
        SqlSession session = null;
        boolean loginSuccess = false;
        try {
            session = MyBatisUtil.getSession();
            UserMapper userMapper = session.getMapper(UserMapper.class);

            User u = userMapper.getUserByUsername(user.getUsername());

            if (u != null && u.getPassword().equals(user.getPassword())) {
                loginSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return loginSuccess;
    }

    public static User getUserByName(String username) {
        SqlSession session = null;
        User user = null;
        try {
            session = MyBatisUtil.getSession();
            UserMapper userMapper = session.getMapper(UserMapper.class);
            user = userMapper.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public static User getUserById(int userid) {
        SqlSession session = null;
        User user = null;
        try {
            session = MyBatisUtil.getSession();
            UserMapper userMapper = session.getMapper(UserMapper.class);
            user = userMapper.getUserById(userid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public static void signUp(User user) {
        SqlSession session = null;
        try {
            session = MyBatisUtil.getSession();
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.insertUser(user);
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static boolean isUsernameExists(String username) {
        SqlSession session = null;
        boolean exists = false;
        try {
            session = MyBatisUtil.getSession();
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.getUserByUsername(username);
            if (user != null) {
                exists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return exists;
    }
}