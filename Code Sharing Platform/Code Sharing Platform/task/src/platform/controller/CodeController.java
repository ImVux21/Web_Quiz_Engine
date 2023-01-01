package platform.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import platform.entity.Code;

@RestController
public class CodeController {
    @GetMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCodeHTML() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "/api/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public Code getCodeAPI() {
        return new Code("""
                public static void main(String[] args) {
                    SpringApplication.run(CodeSharingPlatform.class, args);
                }""");
    }
}
