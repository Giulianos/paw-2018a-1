package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.Users;
import ar.edu.itba.paw.services.UsersImpl;

@Controller
public class HelloWorldController {
	
	@Autowired
	private Users us;
	
	@RequestMapping("/")
	public ModelAndView helloWorld() {
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("greeting", us.findById(1).getUsername());
		return mav;
	}
	
	// Request example: http://localhost:8080/?id=9
	//
	//@RequestMapping("/")
	//public ModelAndView helloWorld(@RequestParam("id") int id) {
	//	final ModelAndView mav = new ModelAndView("index");
	//	//mav.addObject("greeting", us.findById(1).getUsername());
	//	mav.addObject("greeting", us.findById(1).getUsername() + " " + id);
	//	return mav;
	//}
	
	// Request example: http://localhost:8080/user/9
	//
	//@RequestMapping("/user/{id}")
	//public ModelAndView helloWorld(@PathVariable("id") int id) {
	//	final ModelAndView mav = new ModelAndView("index");
	//	mav.addObject("greeting", us.findById(1).getUsername() + " " + id);
	//	return mav;
	//}
	
}