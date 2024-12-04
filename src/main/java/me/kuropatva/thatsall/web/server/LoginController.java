package me.kuropatva.thatsall.web.server;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.lobby.LobbyIDGenerator;
import me.kuropatva.thatsall.model.lobby.LobbyManager;
import me.kuropatva.thatsall.model.player.PlayerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class LoginController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginPage() {
        return "login.html";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public void attemptLogin(@RequestParam String username,
                             @RequestParam String passcode,
                             @RequestParam String gameID,
                             @RequestParam String gameCode,
                             HttpServletResponse response,
                             HttpSession session) {
        boolean success = false;
        Lobby lobby = null;
        if (gameID != null) {
            lobby = LobbyManager.game(gameID);
        }

        if (lobby == null) {
            gameID = LobbyIDGenerator.get();
            lobby = LobbyManager.newGame(gameID);
            if (!gameCode.isBlank()) lobby.setPassword(gameCode);
            var player = PlayerFactory.get(username, passcode).game(lobby).build();
            lobby.addPlayer(player);
            success = true;
        } else if (!lobby.hasPlayerName(username)) {
            var player = PlayerFactory.get(username, passcode).game(lobby).build();
            lobby.addPlayer(player);
            if (lobby.authorize(gameCode)) success = true;
        } else {
            var existingPlayer = lobby.getPlayer(username);
            if (existingPlayer.authorize(passcode)) {
                if (lobby.authorize(gameCode)) success = true;
            }
        }
        if (success) {
            success(username, passcode, gameID, response);
        } else {
            fail(response);
        }
    }

    private void success(String username, String passcode, String gameID, HttpServletResponse response) {
        response.addCookie(new Cookie("username", username));
        response.addCookie(new Cookie("passcode", passcode));
        response.addCookie(new Cookie("gameID", gameID));
        try {
            response.sendRedirect("/game?id=" + gameID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fail(HttpServletResponse response) {
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
