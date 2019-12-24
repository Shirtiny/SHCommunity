package cn.shirtiny.community.community;

import cn.shirtiny.community.SHcommunity.Mapper.Pre_UserMapper;
import cn.shirtiny.community.SHcommunity.Mapper.UserMapper;
import cn.shirtiny.community.SHcommunity.Model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class CommunityApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}
