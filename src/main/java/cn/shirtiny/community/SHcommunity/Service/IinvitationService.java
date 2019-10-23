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

    InvitationDTO selectOneDtoAndCs(long id);//根据帖子id查询一个帖子的包括评论在内的详细信息


    IPage<Invitation> selectBypage(Page<Invitation> page);//分页查询

    IPage<InvitationDTO> selectDtoBypage(Page<InvitationDTO> page,Long sectionId);//分页查询，并查出作者

    //分页查询全部帖子，包含用户信息，根据最后更新时间 倒序排列
    IPage<InvitationDTO> selectDtoBypageDesc(Page<InvitationDTO> page,Long sectionId);

    //查询出指定版块的一个最新创建的帖子（该帖应有最大的帖子id），包含创建者、标题等
    InvitationDTO selectOneLatestDto(long sectionId);

    //查询出版块列表的每一个最新创建的帖子（该帖应有最大的帖子id），包含创建者、标题等
    List<InvitationDTO> selectAllLatestDto(List<Long> sectionIds);


}
