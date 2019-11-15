package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.User;

import java.util.List;

public interface IuserService {

    void addUser(User user);
    List<User> selectOneUserByGithubId(Long githubId);
    User selectOneUserByUid(Long uid);
}
