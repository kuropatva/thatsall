package me.kuropatva.thatsall.web.server;

import jakarta.servlet.http.HttpServletResponse;
import me.kuropatva.thatsall.web.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class GameController {
    @RequestMapping(value = "/game")
    public String game(@RequestParam String id, HttpServletResponse response, @CookieValue(value = "username") String username, @CookieValue(value = "passcode") String passcode) {
        if (Auth.authorize(id, username, passcode)) {
            return "/game/gameDisplay.html";
        }
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
