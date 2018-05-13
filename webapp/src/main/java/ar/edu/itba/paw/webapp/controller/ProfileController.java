package ar.edu.itba.paw.webapp.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.Orders;
import ar.edu.itba.paw.interfaces.Publications;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.auth.IAuthenticationFacade;
import ar.edu.itba.paw.webapp.form.OrderForm;
import ar.edu.itba.paw.webapp.form.UserForm;

@Controller
public class ProfileController {
	@Autowired
	private Publications ps;
	@Autowired
	private Orders ord;
	@Autowired
	private IAuthenticationFacade auth;
	
	@RequestMapping(value = "/profile")
	public ModelAndView profile() {
		ModelAndView mav = new ModelAndView("profile/profile");
		
		String user = auth.getAuthentication().getName();
		
		mav.addObject("publicationsQuantity", ps.findBySupervisor(user).size());
		mav.addObject("subscriptionsQuantity", ord.findBySubscriber(user).size());
		mav.addObject("finalizedSubscriptionsQuantity", ord.findFinalizedBySubscriber(user).size());
		
		return mav;
	}
	
	@RequestMapping(value = "/profile/publications")
	public ModelAndView publications() {
		ModelAndView mav = new ModelAndView("profile/publications");
		
		String user = auth.getAuthentication().getName();
		
		List<Publication> publications = ps.findBySupervisor(user);
		for (Publication publication : publications) {
			publication.setRemainingQuantity(ps.remainingQuantity(publication.getId()));
		}
		
		mav.addObject("publications", publications);
		
		return mav;
	}

	@RequestMapping(value = "/profile/subscriptions")
	public ModelAndView subscriptions() {
		ModelAndView mav = new ModelAndView("profile/subscriptions");
		
		String user = auth.getAuthentication().getName();
		
		List<Order> subscriptions = ord.findBySubscriber(user);
		for (Order order : subscriptions) {
			order.setPublication(ps.findById(order.getPublication_id()));
			order.getPublication().setRemainingQuantity(ps.remainingQuantity(order.getPublication_id()));
		}
		
		mav.addObject("subscriptions", subscriptions);
		
		return mav;	}
	
}
