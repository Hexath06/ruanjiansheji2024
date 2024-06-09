package DAO;

import Entity.Category;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface CategoryMapper {
    @Select("SELECT * FROM category")
    public ArrayList<Category> getCategory();
}
