package edu.study.module.springbootwebsocket.controller;

import edu.study.module.springbootwebsocket.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author drew
 * @date 2021/2/6 10:52
 **/
@Controller
public class WebSocketController {

    @Autowired
    private WebSocketServer webSocketServer;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/webSocket")
    public ModelAndView socket() {
        ModelAndView mav = new ModelAndView("/webSocket");
//        mav.addObject("userId", userId);
        return mav;
    }

}
