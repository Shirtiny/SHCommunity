package cn.shirtiny.community.SHcommunity.Controller;

import com.baidu.fsg.uid.service.UidGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UidGenerateService uidGenService;

    @GetMapping("/shApi/getRandomId")
    public String genUid() {
        return String.valueOf(uidGenService.generateUid());
    }

}
