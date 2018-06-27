package ar.edu.itba.paw.webapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
	
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET})
	public ModelAndView login(@RequestParam (value = "error", required = false) String error) {
		
		
		ModelAndView modelAndView = new ModelAndView("login");
		if(error != null) {
			modelAndView.addObject("invalid_loginPassword", true);
		}
		return modelAndView;
	}

}