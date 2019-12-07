package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.DTO.ChatHistoryDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {

    //根据记录名查找聊天记录
    @Select("select * from chat_history where chat_history_name=#{chatHistoryName}")
    ChatHistoryDTO selectOneHistory(@Param("chatHistoryName") String chatHistoryName);

    //根据记录id查找聊天记录
    @Select("select * from chat_history where chat_history_id=#{chatHistoryId}")
    ChatHistoryDTO selectOneHistoryById(@Param("chatHistoryId") String chatHistoryId);

    //根据id查询记录数，确认记录是否已经存在
    @Select("select count(1) from chat_history where chat_history_id = #{chatHistoryId}")
    int isExist(@Param("chatHistoryId") String chatHistoryId);

    //使消息数自增
    @Update("update chat_history set message_num=message_num+1 where chat_history_id=#{chatHistoryId}")
    void incrMessageNum(@Param("chatHistoryId") String chatHistoryId);

    //更新聊天记录的修改时间
    @Update("update chat_history set gmt_modified=#{gmtModified} where chat_history_id=#{chatHistoryId}")
    void updateGmtModified(@Param("chatHistoryId") String chatHistoryId,@Param("gmtModified") Long gmtModified);

    //查询出单个用户的所有聊天记录及其内容 更新时间倒序 及其全部消息
    @Select("select * from chat_history where sender_id = #{userId} or recipient_id = #{userId}  ORDER BY gmt_modified DESC")
    @Results({
            @Result(column = "chat_history_id",property = "chatHistoryId",id = true),
            @Result(column = "chat_history_id", property = "chatMessages",javaType = List.class ,many = @Many(select = "cn.shirtiny.community.SHcommunity.Mapper.ChatMessageMapper.selectAllDTOByhistoryId")),
            @Result(column = "chat_history_id", property = "unReadCount",javaType = Integer.class ,one = @One(select = "cn.shirtiny.community.SHcommunity.Mapper.ChatHistoryMapper.selectReadCountByHistoryId"))
    })
    List<ChatHistoryDTO> selectAllChatHistoryByUid(@Param("userId") Long userId);

    //查询出单个用户的所有聊天记录 的所有已读/未读消息数
    @Select("SELECT count(1) FROM " +
            "(SELECT m.readed,m.recipient_id FROM chat_history h JOIN chat_message m on h.chat_history_id=m.chat_history_id " +
            "WHERE  h.sender_id = #{userId} or h.recipient_id = #{userId}) r WHERE r.readed = #{readed} AND r.recipient_id=#{userId}")
    int selectReadCountByUid(@Param("userId") Long userId,@Param("readed") boolean readed);

    //查询某个聊天记录 的已读/未读消息数
    @Select("SELECT count(1) FROM chat_message WHERE chat_history_id=#{chatHistoryId} AND readed=#{readed}")
    int selectReadCountByHistoryId(@Param("chatHistoryId") String chatHistoryId,@Param("readed") boolean readed);
}
