package cn.shirtiny.community.SHcommunity.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/error")
public class errorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "myError";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String errorHtml(HttpServletRequest request, String message,Model model) {
        System.out.println(message);

        return "myError";
    }




}
