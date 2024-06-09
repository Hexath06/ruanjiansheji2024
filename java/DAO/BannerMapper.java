package DAO;

import Entity.Banner;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface BannerMapper {
    @Select("SELECT * FROM banner")
    public ArrayList<Banner> getBanner();
}
