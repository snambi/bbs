package org.github.snambi.bbs.web;


import org.github.snambi.bbs.microsoft.AuthHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Controller
public class HomeController {

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(@RequestParam(value="name", required=false, defaultValue="World") String name,
                       Model model, HttpServletRequest request) {

        Date currentTime = Calendar.getInstance().getTime();

        model.addAttribute("name", name);
        model.addAttribute("date", currentTime);

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        // Save the state and nonce in the session so we can
        // verify after the auth process redirects back
        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);
        boolean isLoggedIn = false;
        if( session.getAttribute("userName") != null ){
            isLoggedIn = true;
        }

        String loginUrl = null;
        String error = null;
        try {
            loginUrl = AuthHelper.getLoginUrl(state, nonce);
        } catch ( MissingRequiredPropertiesException e) {
            e.printStackTrace();
            error = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            error = e.getMessage();
        }

        logger.info("Microsoft Login URL: "+ loginUrl );

        model.addAttribute("loginUrl", loginUrl);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("error", error);

        return "tiles.homepage";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

}