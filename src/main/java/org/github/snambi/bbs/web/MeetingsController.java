package org.github.snambi.bbs.web;

import org.github.snambi.bbs.microsoft.*;
import org.github.snambi.bbs.microsoft.beans.Event;
import org.github.snambi.bbs.microsoft.beans.PagedResult;
import org.github.snambi.bbs.microsoft.beans.TokenResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 */
@Controller
public class MeetingsController {

    @RequestMapping("/meetings")
    public String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();
        boolean isLoggedIn = false;
        if( session.getAttribute("userName") != null ){
            isLoggedIn = true;
            model.addAttribute("isLoggedIn", isLoggedIn);
        }

        TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            return "redirect:/";
        }

        String tenantId = (String)session.getAttribute("userTenantId");

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        String email = (String)session.getAttribute("userEmail");

        IOutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        // Sort by start time in descending order
        String sort = "start/dateTime DESC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";
        // Return at most 10 events
        Integer maxResults = 100;

        try {
            PagedResult<Event> events = outlookService.getEvents(
                    sort, properties, maxResults)
                    .execute()
                    .body();

            model.addAttribute("meetings", events.getValue());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        return "tiles.meetings";
    }
}