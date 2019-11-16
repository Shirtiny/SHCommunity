package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Exception.UserAlreadyExsitException;
import cn.shirtiny.community.SHcommunity.Mapper.UserMapper;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class userServiceImpl implements IuserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void addUser(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("github_id", user.getGithubId());
        map.put("user_name",user.getUserName());
        List<User> users = userMapper.selectByMap(map);
        //若在数据库没有查到该用户
        if (users == null || users.size() == 0) {
            userMapper.insert(user);//加入数据库
        } else {
            System.out.println("该用户之前已保存到数据库中");
            throw new UserAlreadyExsitException("该用户已存在");
        }

    }

    @Override
    public List<User> selectOneUserByGithubId(Long githubId) {
        Map<String,Object> map=new HashMap<>();
        map.put("github_id",githubId);
        //返回查找结果
        return userMapper.selectByMap(map);
    }

    @Override
    public User selectOneUserByUid(Long uid) {
        return userMapper.selectUserByid(uid);
    }

    @Override
    public User selectOneUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }
}
