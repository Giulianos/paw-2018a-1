package ar.edu.itba.paw.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.Users;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.UserForm;

@Controller
public class HelloWorldController {
	
	@Autowired
	private Users us;
	 
	@RequestMapping("/user/{id}")
	public ModelAndView helloWorld(@PathVariable("id") int id) {
		final ModelAndView mav = new ModelAndView("user");
		mav.addObject("user", us.findById(id));
		return mav;
	}
	
	@RequestMapping("/")
	public ModelAndView index(@ModelAttribute("registerForm") final UserForm form) {
		return new ModelAndView("index");
	}
	
	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return index(form);
		}
		if (!uniqueUser(form)) {
			return index(form);
		}
		if (!uniqueEmail(form)) {
			return index(form);
		}
		if (!passwordCheck(form)) {
			return index(form);
		}
		final User u = us.create(form.getUsername(), form.getEmail(), form.getPassword());
		return new ModelAndView("redirect:/user/"+ u.getId());
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}


	private boolean uniqueEmail(UserForm form) {
		return true;
	}

	private boolean uniqueUser(UserForm form) {
		return true;
	}
	private boolean passwordCheck(UserForm form) {
		return form.getPassword().equals(form.getRepeatPassword());
	}
}