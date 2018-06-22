package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.OrderService;
import ar.edu.itba.paw.interfaces.PublicationService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.auth.IAuthenticationFacade;

@Controller
public class HomePageController {
	
	@Autowired
	private UserService us;
	@Autowired
	private PublicationService pu;
	@Autowired
	private OrderService ord;
	@Autowired
	private IAuthenticationFacade auth;
	@Autowired
	private Validator validator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	  binder.setValidator(validator);
	}
	
	@RequestMapping("/")
	public ModelAndView index() {
		
		return new ModelAndView("index");
	}
}