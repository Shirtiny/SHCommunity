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

    @Update("update chat_history set message_num=message_num+1 where chat_history_id=#{chatHistoryId}")
    void incrMessageNum(@Param("chatHistoryId") String chatHistoryId);

    //查询出单个用户的所有聊天记录 及其全部消息
    @Select("select * from chat_history where sender_id = #{userId} or recipient_id = #{userId}")
    @Results({
            @Result(column = "chat_history_id",property = "chatHistoryId",id = true),
            @Result(column = "chat_history_id", property = "chatMessages",javaType = List.class ,many = @Many(select = "cn.shirtiny.community.SHcommunity.Mapper.ChatMessageMapper.selectAllDTOByhistoryId"))
    })
    List<ChatHistoryDTO> selectAllChatHistoryByUid(@Param("userId") Long userId);
}
