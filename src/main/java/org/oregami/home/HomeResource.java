package org.oregami.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@Controller
public class HomeResource {

	public HomeResource() {
	}

    @GetMapping(value = "/")
	public String home(Principal principal) {
	    return "index";
	}


    @GetMapping(value = "/logout")
    public RedirectView logout(Model model, HttpServletRequest request) throws ServletException {
        request.logout();
        return new RedirectView("/");
    }


    @GetMapping(value = "/login")
    public RedirectView login(Model model, HttpServletRequest request) throws ServletException {
        return new RedirectView("/");
    }
}
