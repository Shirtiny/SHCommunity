package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.Mapper.InvitationMapper;
import cn.shirtiny.community.SHcommunity.Mapper.UserMapper;
import cn.shirtiny.community.SHcommunity.Model.Invitation;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javafx.scene.input.DataFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvitationService implements IinvitationService {

    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UserMapper userMapper;



    //增加帖子
    @Override
    public boolean addInvitation(Invitation invitation) {
        if (invitation==null){return  false;}
        boolean titleIsEmpty = StringUtils.isEmpty(invitation.getTitle());
        boolean contentIsEmpty = StringUtils.isEmpty(invitation.getContent());
        //判断标题或内容是不是空、标题或内容长度是否超限
        if (titleIsEmpty||contentIsEmpty||invitation.getTitle().length()>20||invitation.getContent().length()>400){
            return false;
        }else {
            invitation.setGmtCreated(System.currentTimeMillis());
            invitation.setGmtModified(invitation.getGmtCreated());
            invitationMapper.insert(invitation);//插入数据库
            return true;
        }
    }

    //根据id查询一个帖子的详情
    @Override
    public InvitationDTO selectOneInvitationDetailsById(long id) {
        return invitationMapper.selectOneDtoById(id);
    }

    //查询全部帖子
    @Override
    public List<Invitation> selectAll() {

        return invitationMapper.selectList(null);
    }

    //分页查询全部帖子
    @Override
    public IPage<Invitation> selectBypage(Page<Invitation> page) {

        return invitationMapper.selectPage(page,null);
    }

    //分页查询全部帖子，包含用户信息
    @Override
    public IPage<InvitationDTO> selectDtoBypage(Page<InvitationDTO> page) {
        return invitationMapper.selectDtoByPage(page);
    }
}
