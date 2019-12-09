package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Enum.ShUserInfoCheckType;
import cn.shirtiny.community.SHcommunity.Model.User;

import java.util.List;

public interface IuserService {

    void addUser(User user);

    List<User> selectOneUserByGithubId(Long githubId);

    //根据githubId查询用户是否存在
    boolean userIsExistByGitId(String githubId);

    //根据githubId查出用户
    User selectOneUserByGitId(String githubId);

    User selectOneUserByUid(Long uid);

    UserDTO selectOneUserDtoByUid(Long uid);

    User selectOneUserByUserName(String userName);

    //检查数据库中是否已经存在用户名
    boolean userNameIsExsit(String userName);

    //校验用户信息
    void checkUserInfo(User user);

    //校验用户信息的其中之一 type为校验类型 传入 ShUserInfoCheckType 的code
    void checkOneOfUserInfo(User user, Integer type);

    //校验用户名
    void checkUserName(String userName);

    //校验用户密码
    void checkUserPWD(String passWord);

    //校验用户邮箱
    void checkUserEmail(String email);
}
