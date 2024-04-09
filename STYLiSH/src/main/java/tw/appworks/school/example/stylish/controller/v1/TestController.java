package tw.appworks.school.example.stylish.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TestController {

    @GetMapping("/docker1.png")
    public String testController(){
        log.info("有東西進來喽!");
        return "docker.png";
    }
}
