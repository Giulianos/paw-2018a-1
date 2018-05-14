package ar.edu.itba.paw.webapp.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
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
import ar.edu.itba.paw.webapp.form.PublicationForm;
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
	
	@RequestMapping(value = "/profile/publications", method = { RequestMethod.GET })
	public ModelAndView publications(@RequestParam(value="newModal", defaultValue="false") Boolean newModal, @ModelAttribute("publicationForm") final PublicationForm form, ModelMap model) {
		ModelAndView mav = new ModelAndView("profile/publications");
		
		String user = auth.getAuthentication().getName();
		
		List<Publication> publications = ps.findBySupervisor(user);
		for (Publication publication : publications) {
			publication.setRemainingQuantity(ps.remainingQuantity(publication.getId()));
		}
		
		if(newModal) {
			model.addAttribute("shouldShowModal", true);
		}
		mav.addObject("publications", publications);
		
		return mav;
	}

	@RequestMapping(value = "/profile/subscriptions")
	public ModelAndView subscriptions() {
		ModelAndView mav = new ModelAndView("profile/subscriptions");
		
		String user = auth.getAuthentication().getName();
		
		List<Order> subscriptions = ord.findBySubscriber(user);
		Boolean anyHasNoSupervisor = false;
		for (Order order : subscriptions) {
			order.setPublication(ps.findById(order.getPublication_id()));
			order.getPublication().setRemainingQuantity(ps.remainingQuantity(order.getPublication_id()));
			if(!anyHasNoSupervisor && !ps.hasSupervisor(order.getPublication_id())) {
				anyHasNoSupervisor = true;
			}
		}
		
		mav.addObject("anyHasNoSupervisor", anyHasNoSupervisor);
		mav.addObject("subscriptions", subscriptions);
		
		return mav;
	}
	
	@RequestMapping(value = "/profile/subscriptions-finalized")
	public ModelAndView subscriptionsFinalized() {
		ModelAndView mav = new ModelAndView("profile/subscriptions-finalized");
		
		String user = auth.getAuthentication().getName();
		
		List<Order> subscriptions = ord.findFinalizedBySubscriber(user);
		for (Order order : subscriptions) {
			order.setPublication(ps.findById(order.getPublication_id()));
		}
		
		mav.addObject("subscriptions", subscriptions);
		
		return mav;
	}
	
	@RequestMapping(value = "/createPublication", method = { RequestMethod.POST })
	public ModelAndView createPublication(@Valid @ModelAttribute("publicationForm") final PublicationForm form, final BindingResult errors, ModelMap model) {
		boolean isValid = validPublicationQuantity(errors,form,model);
		
		if (errors.hasErrors() || !isValid) {
			// Add attribute to model to keep pop up form persistent
			model.addAttribute("publicationErrors", true);
			return publications(false, form, model);
		}
		// Add attribute to model to show success notification
		model.addAttribute("publicationCreated", true);
		
		String currentUser = auth.getAuthentication().getName();
		
		final Publication p = ps.create(currentUser, form.getDescription(), Float.parseFloat(form.getPrice()), Integer.parseInt(form.getQuantity()), form.getImage());

		ord.create(p.getId(), p.getSupervisor(), Integer.parseInt(form.getOwnerQuantity()));
		
		return publications(false, null, model);
	}
	
	private boolean validPublicationQuantity(final BindingResult errors, PublicationForm form, ModelMap model) {
		// If one of the quantities is not a number (form regular expression).
		if (errors.getFieldError("quantity") != null || errors.getFieldError("ownerQuantity") != null) {
			return false;
		}
		
		if (!form.quantityCheck()) {
			model.addAttribute("invalidQuantity", true);
			return false;
		}
		return true;
	}
	
}
