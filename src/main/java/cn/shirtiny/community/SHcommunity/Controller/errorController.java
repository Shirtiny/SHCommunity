package cn.shirtiny.community.SHcommunity.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class errorController implements ErrorController {

    @Override
    public String getErrorPath() {

        return "error";
    }

//    @RequestMapping("/error")
    @RequestMapping
    public String toError(HttpServletRequest request,Model model){
        HttpStatus status = getStatus(request);

        model.addAttribute("code",status.value());
        if (status.is4xxClientError()) {
            model.addAttribute("message", "请求出错");
        }
        if (status.is5xxServerError()) {
            model.addAttribute("message", "服务异常");
        }
        return "myError";
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }




}
