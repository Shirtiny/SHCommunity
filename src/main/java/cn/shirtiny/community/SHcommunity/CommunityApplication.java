package cn.shirtiny.community.SHcommunity;

import cn.shirtiny.community.SHcommunity.Utils.Encryption.AESKey;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"cn.shirtiny.community.SHcommunity.Mapper","com.baidu.fsg.uid.worker.dao"})
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
