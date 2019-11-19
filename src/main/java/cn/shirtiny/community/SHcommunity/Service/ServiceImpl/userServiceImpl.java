package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Utils.Encryption.ShaEncryptor;
import cn.shirtiny.community.SHcommunity.Enum.ShUserInfoCheckType;
import cn.shirtiny.community.SHcommunity.Exception.CreateUserFailedException;
import cn.shirtiny.community.SHcommunity.Exception.UserInfoNotAllowException;
import cn.shirtiny.community.SHcommunity.Exception.UserAlreadyExsitException;
import cn.shirtiny.community.SHcommunity.Mapper.UserMapper;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Transactional
public class userServiceImpl implements IuserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private ShaEncryptor shaEncryptor;
    @Override
    public void addUser(User user) {
        //信息校验
        checkUserInfo(user);
        //数据库查重
        Map<String, Object> map = new HashMap<>();
        map.put("github_id", user.getGithubId());
        map.put("user_name",user.getUserName());
        List<User> users = userMapper.selectByMap(map);
        //若在数据库没有查到该用户
        if (users == null || users.size() == 0) {
            //加密密码
            String encryptedPWD = shaEncryptor.encrypt(user.getPassWord());
            user.setPassWord(encryptedPWD);
            System.out.println(encryptedPWD);
            //设置用户创建和修改时间
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            try{
                //加入数据库
                userMapper.insert(user);
                System.out.println("成功把用户插入数据库");
            }catch (Exception e){
                throw new CreateUserFailedException("用户数据入库失败",e);
            }
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

    @Override
    public boolean userNameIsExsit(String userName) {
        int count = userMapper.selectUserNameCount(userName);
        return count != 0;
    }

    @Override
    public void checkUserInfo(User user) {
        if (user==null || user.getPassWord()==null || user.getEmail()==null){
            throw new UserInfoNotAllowException("待校验用户为空，或必要信息不全");
        }
        //校验用户名
        checkUserName(user.getUserName());
        //校验用户密码
        checkUserPWD(user.getPassWord());
        //校验邮箱
        checkUserEmail(user.getEmail());
    }


    //校验用户信息的其中之一 type为校验类型 传入 ShUserInfoCheckType 的code
    @Override
    public void checkOneOfUserInfo(User user, Integer type) {
        if (user==null){
            throw new UserInfoNotAllowException("待校验用户为空，没有任何信息");
        }
        if (type==ShUserInfoCheckType.UserName.getCode()){
            checkUserName(user.getUserName());
            return;
        }
        if (type==ShUserInfoCheckType.PassWord.getCode()){
            checkUserPWD(user.getPassWord());
            return;
        }
        if (type==ShUserInfoCheckType.Email.getCode()){
            checkUserEmail(user.getEmail());
            return;
        }
        throw new UserInfoNotAllowException("传入的校验类型代码不合法");
    }

    //用户名格式的正则表达式
    private Pattern userNameReg = Pattern.compile("[a-zA-Z0-9]+");
    @Override
    public void checkUserName(String userName) {
        //校验用户名
        if (userName==null || "".equals(userName.trim()) || userName.length()<2 || userName.length()>10){
            throw new UserInfoNotAllowException("用户名为空或长度不符合规范");
        }
        boolean isExsit = userNameIsExsit(userName);
        if (isExsit){
            throw new UserAlreadyExsitException("该用户名已存在");
        }
        //格式检测
        boolean isMatche = userNameReg.matcher(userName).matches();
        if(!isMatche){
            throw new UserInfoNotAllowException("用户名由字母和数字组成、区分大小写,不可使用中文和特殊字符");
        }
    }

    //密码格式的正则表达式
    private Pattern passWordReg = Pattern.compile("[a-zA-Z0-9.!#%^_\\-]+");
    @Override
    public void checkUserPWD(String passWord) {
        //校验用户密码
        if (passWord==null || "".equals(passWord.trim()) || passWord.length()<6 || passWord.length()>20){
            throw new UserInfoNotAllowException("密码不能为空或长度不能超过6-20");
        }

        boolean isMatche = passWordReg.matcher(passWord).matches();
        if (!isMatche){
            throw new UserInfoNotAllowException("密码请使用字母和数字，区分大小写，特殊字符只允许使用 . ! # % ^ _ -");
        }
    }

    //邮箱格式的正则表达式
    private Pattern emailReg = Pattern.compile("^[\\da-zA-Z]+[\\w.\\-]+@[a-zA-Z0-9]+\\.[a-z]+$");
    @Override
    public void checkUserEmail(String email) {
        if (email==null || "".equals(email.trim())){
            throw new UserInfoNotAllowException("邮箱不能为空，也不能全为空格");
        }
        //正则表达式格式匹配
        boolean isMatche = emailReg.matcher(email).matches();
        System.out.println("用户输入的邮箱："+email+"是否满足格式："+isMatche);
        if(!isMatche){
            throw new UserInfoNotAllowException("邮箱格式不匹配");
        }
        //邮箱@前的字符长度的检测
        String emailName = email.split("@")[0];
        if (emailName.length()<3 || emailName.length()>18){
            throw new UserInfoNotAllowException("邮箱用户名最小3个字符，最大18个字符");
        }
        //给邮箱发邮件 或实际的去验证邮箱是否真实
    }
}
