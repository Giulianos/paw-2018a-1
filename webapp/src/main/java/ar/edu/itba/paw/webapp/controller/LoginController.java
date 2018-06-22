package ar.edu.itba.paw.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService us;
	
	@RequestMapping(value = "/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public ModelAndView login(HttpServletRequest request, ModelMap model) {
		if (!us.findByUsername(request.getParameter("j_username")).isPresent()) {
			model.addAttribute("invalid_loginUser", true);
		} else { // If user is valid then password is invalid.
			model.addAttribute("invalid_loginPassword", true);
		}
		model.addAttribute("failed_username", request.getParameter("j_username"));
		return new ModelAndView("login");
	}
}