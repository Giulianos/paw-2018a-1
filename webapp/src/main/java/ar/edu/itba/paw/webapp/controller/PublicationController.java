package ar.edu.itba.paw.webapp.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import ar.edu.itba.paw.interfaces.Orders;
import ar.edu.itba.paw.interfaces.Publications;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.auth.IAuthenticationFacade;
import ar.edu.itba.paw.webapp.form.OrderForm;
import ar.edu.itba.paw.webapp.form.PublicationForm;
import ar.edu.itba.paw.webapp.form.UserForm;

@Controller
public class PublicationController {
	@Autowired
	private Publications ps;
	@Autowired
	private Orders ord;
	@Autowired
	private IAuthenticationFacade auth;
	
	
	private void includeUserTransactions(ModelMap model) {	
		if (auth.getAuthentication().isAuthenticated()) {
			String user = auth.getAuthentication().getName();
			model.addAttribute("publications", ps.findBySupervisor(user));
		}
	}
	
	@RequestMapping(value = "/order", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("orderForm") final OrderForm form, final BindingResult errors, ModelMap model) {
		boolean isValid = validOrder(form,model);

		if (errors.hasErrors() || !isValid) {
			ModelAndView modelAndView = new ModelAndView("error");
			return modelAndView;
		}

		String user = auth.getAuthentication().getName();
		
		ord.create(Long.parseLong(form.getPublicationId()), user, Integer.parseInt(form.getQuantity()));
		return new ModelAndView("redirect:/profile/subscriptions");
	}
	
	public boolean validOrder(OrderForm form, ModelMap model) {
		if(Long.parseLong(form.getQuantity()) > ps.remainingQuantity(Long.parseLong(form.getPublicationId())))
			return false;
		return true;
	}
	
	@RequestMapping(value = "/search/{keywords}")
	public ModelAndView search(@Valid @ModelAttribute("orderForm") final OrderForm form, @PathVariable("keywords") String keywords) {
		List<Publication> results = ps.findByDescription(keywords);
		final ModelAndView mav = new ModelAndView("search");
		for(Publication publication : results) {
			publication.setRemainingQuantity(ps.remainingQuantity(publication.getId()));
		}
		mav.addObject("resultList", results);
		mav.addObject("searchedKeyword", keywords);
		
		return mav;
	}
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public ModelAndView search(@RequestParam(value="keywords", required=false) String keywords, @Valid @ModelAttribute("orderForm") final OrderForm form) {
		if(keywords==null) {
			List<Publication> results = ps.findByDescription("");
			final ModelAndView mav = new ModelAndView("search");
			for(Publication publication : results) {
				publication.setRemainingQuantity(ps.remainingQuantity(publication.getId()));
			}
			mav.addObject("resultList", results);
			return mav;
		}
		return new ModelAndView("redirect:/search/" + keywords);
	}
}
