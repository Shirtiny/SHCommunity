package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Pre_UserMapper {

    @Select("select * from user")
    List<User> selectAll();

    @Insert("insert into user(id,nickname,password,email,avatarimage,github_id,gmt_create,gmt_modified) values (#{id},#{nickname},#{password},#{email},#{avatarimage},#{github_id},#{gmt_create},#{gmt_modified})")
    void addUser(User user);

}
