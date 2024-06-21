package DAO;

import Entity.User;
import Entity.UserProfile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UpdateProfileMapper {
    // update profile image
    @Update("UPDATE user SET image = #{image} WHERE id = #{id}")
    void updateProfilePicture(@Param("id") int userId, @Param("image") String image);

    @Select("SELECT * FROM user_profile_pictures WHERE userid = #{userId}")
    UserProfile getUserProfileById(int userId);

    @Update("UPDATE user_profile_pictures SET image = #{image} WHERE userid = #{userid}")
    int updateImagePath(@Param("image") String image, @Param("userid") int userid);

    // update user bio
    @Update("UPDATE user SET username=#{user.username}, DoB=#{user.dob}, phoneNum=#{user.phoneNum}, email=#{user.email} WHERE id=#{user.id}")
    void updateUserBio(@Param("user") User user);

    @Insert("INSERT INTO user_profile_pictures (userid, image) VALUES (#{userid}, 'Images/Asset/Default Profile picture/default.jpg')")
    public void insertDefaultProfilePicture(@Param("userid") int userid);
}
