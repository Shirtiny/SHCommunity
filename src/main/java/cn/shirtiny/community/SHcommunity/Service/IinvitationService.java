package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.Model.Invitation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface IinvitationService {

    boolean addInvitation(Invitation invitation);//增加一个帖子

    List<Invitation> selectAll();//查询全部帖子

    InvitationDTO selectOneInvitationDetailsById(long id);//根据id查询一个帖子的详情

    IPage<Invitation> selectBypage(Page<Invitation> page);//分页查询

    IPage<InvitationDTO> selectDtoBypage(Page<InvitationDTO> page);//分页查询，并查出作者
}
