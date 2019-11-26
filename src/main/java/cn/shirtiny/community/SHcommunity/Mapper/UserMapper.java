package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where user_id=#{userId} ")
//    UserDTO selectDTObyid(long userId);
    UserDTO selectDTOById(long userId);

    @Select("select * from user where user_id=#{userId} ")
    User selectUserByid(@Param("userId") long userId);

    @Select("select * from user where user_id=#{userId} ")
    UserDTO selectUserDtoByid(@Param("userId") long userId);

    @Select("select * from user where user_name=#{userName}")
    User selectUserByUserName(@Param("userName") String userName);

    @Select("select count(user_id) from `user` where user_name=#{userName}")
    int selectUserNameCount(@Param("userName") String userName);

    //寻找
}
