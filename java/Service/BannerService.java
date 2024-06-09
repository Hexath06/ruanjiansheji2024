package Service;

import DAO.BannerMapper;
import DAO.CategoryMapper;
import Entity.Banner;
import Entity.Category;
import Utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

public class BannerService {
    public static ArrayList<Banner> getBanner() {
        ArrayList<Banner> banner_arraylist = null;
        SqlSession session = null;

        try {
            session = MyBatisUtil.getSession();
            BannerMapper bannerMapper = session.getMapper(BannerMapper.class);
            banner_arraylist = bannerMapper.getBanner();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return banner_arraylist;
    }

    public static ArrayList<Category> getCategory() {
        ArrayList<Category> category_arraylist = null;
        SqlSession session = null;

        try {
            session = MyBatisUtil.getSession();
            CategoryMapper bannerMapper = session.getMapper(CategoryMapper.class);
            category_arraylist = bannerMapper.getCategory();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return category_arraylist;
    }
}
