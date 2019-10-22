package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;
import cn.shirtiny.community.SHcommunity.Exception.CreateInvitationErrException;
import cn.shirtiny.community.SHcommunity.Exception.ShException;
import cn.shirtiny.community.SHcommunity.Mapper.InvitationMapper;
import cn.shirtiny.community.SHcommunity.Mapper.UserMapper;
import cn.shirtiny.community.SHcommunity.Model.Invitation;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
@Slf4j
public class InvitationService implements IinvitationService {

    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UserMapper userMapper;


    //增加帖子
    @Override
    public boolean addInvitation(Invitation invitation) {
        if (invitation == null) {
            return false;
        }
        boolean titleIsEmpty = StringUtils.isEmpty(invitation.getTitle());
        boolean contentIsEmpty = StringUtils.isEmpty(invitation.getContent());
        //判断标题或内容是不是空、标题或内容长度是否超限
        if (titleIsEmpty || contentIsEmpty || invitation.getTitle().length() > 20 || invitation.getContent().length() > 2000) {
            return false;
        } else {
            invitation.setGmtCreated(System.currentTimeMillis());
            invitation.setGmtModified(invitation.getGmtCreated());
            try {
                invitationMapper.insert(invitation);//插入数据库
                return true;
            } catch (Exception e) {
                log.error("帖子提交失败，数据库的异常，在应该是InvitationService里抛出,{},{}",invitation, ShErrorCode.Create_Invitation_Failed_Error);
                throw new CreateInvitationErrException(e.toString(),4502);
            }

        }
    }

    //根据id查询一个帖子的详情
    @Override
    public InvitationDTO selectOneInvitationDetailsById(long id) {
        return invitationMapper.selectOneDtoById(id);
    }

    @Override
    public InvitationDTO selectOneDtoAndCs(long id) {
        //帖子浏览量+1
        invitationMapper.incrViews(id);
        return invitationMapper.selectOneDtoAndCs(id);
    }

    //查询全部帖子
    @Override
    public List<Invitation> selectAll() {

        return invitationMapper.selectList(null);
    }

    //分页查询全部帖子
    @Override
    public IPage<Invitation> selectBypage(Page<Invitation> page) {

        return invitationMapper.selectPage(page, null);
    }

    //分页查询全部帖子，包含用户信息，根据id顺序 升序排列
    @Override
    public IPage<InvitationDTO> selectDtoBypage(Page<InvitationDTO> page,Long sectionId) {
        return invitationMapper.selectDtoByPage(page,sectionId);
    }

    //分页查询全部帖子，包含用户信息，根据最后更新时间 倒序排列
    @Override
    public IPage<InvitationDTO> selectDtoBypageDesc(Page<InvitationDTO> page,Long sectionId) {
        return invitationMapper.selectDtoByPageDesc(page,sectionId);
    }
}
