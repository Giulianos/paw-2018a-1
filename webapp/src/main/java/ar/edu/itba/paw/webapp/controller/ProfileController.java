package ar.edu.itba.paw.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.OrderService;
import ar.edu.itba.paw.interfaces.PublicationService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.webapp.auth.IAuthenticationFacade;
import ar.edu.itba.paw.webapp.form.PublicationForm;

@Controller
public class ProfileController {
	private final static int MY_PUBLICATIONS_PAGE_SIZE = 5;
	private final static int MY_PUBLICATIONS_MAX_PAGE_LINKS = 10;
	private final static int MY_ORDERS_PAGE_SIZE = 5;
	private final static int MY_ORDERS_MAX_PAGE_LINKS = 10;
	@Autowired
	private PublicationService ps;
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
	
	@RequestMapping(value = "/profile")
	public ModelAndView profile() {
		ModelAndView mav = new ModelAndView("profile/profile");
		
		String user = auth.getAuthentication().getName();
		
		mav.addObject("publicationsQuantity", ps.findBySupervisor(user).size());
		mav.addObject("subscriptionsQuantity", ord.findBySubscriber(user).size()-ord.findFinalizedBySubscriber(user).size());
		mav.addObject("finalizedSubscriptionsQuantity", ord.findFinalizedBySubscriber(user).size());
		
		return mav;
	}
	

	@RequestMapping(value = "/profile/publications", method = { RequestMethod.GET })
	public ModelAndView publications(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="newModal", defaultValue="false") Boolean newModal, @ModelAttribute("publicationForm") final PublicationForm form, ModelMap model) {
		int index = 0;
		
		if(start != null) {
			index = start - 1;
		}
		
		String user = auth.getAuthentication().getName();
		List<Publication> publications = ps.findBySupervisor(user, index);
		
		ModelAndView mav = new ModelAndView("profile/publications");

		if(newModal) {
			model.addAttribute("shouldShowModal", true);
		}
		mav.addObject("publications", pubPaginationConfig("publications", index, publications, mav));
		
		return mav;
	}
	
	@RequestMapping(value = "/profile/publications", method = { RequestMethod.POST })
	public ModelAndView createPublication(@Valid @ModelAttribute("publicationForm") final PublicationForm form, final BindingResult errors, ModelMap model) {
		boolean isValid = validPublicationQuantity(errors,form,model);
		
		if (errors.hasErrors() || !isValid) {
			// Add attribute to model to keep pop up form persistent
			model.addAttribute("publicationErrors", true);
			return publications(0, false, form, model);
		}
		// Add attribute to model to show success notification
		model.addAttribute("publicationCreated", true);
		
		String currentUser = auth.getAuthentication().getName();
		
		final Publication p = ps.create(currentUser, form.getDescription(), Float.parseFloat(form.getPrice()), Integer.parseInt(form.getQuantity()), form.getImage(), form.getTags());

		ord.create(p, p.getSupervisor(), Integer.parseInt(form.getOwnerQuantity()));
		
		return publications(0, false, null, model);
	}

	@RequestMapping(value = "/profile/subscriptions", method = { RequestMethod.GET })
	public ModelAndView subscriptions(@RequestParam(value="start", required=false) Integer start, ModelMap model) {
		int index = 0;
		
		if(start != null) {
			index = start - 1;
		}
		
		ModelAndView mav = new ModelAndView("profile/subscriptions");
		
		String user = auth.getAuthentication().getName();
		List<Order> orders = ord.findBySubscriber(user, false);
		Boolean anyHasNoSupervisor = ord.anyHasNoSupervisor(orders);
		
		// The list is reduced to fit into a page after checking for publications with no supervisor.
		orders = orders.subList(index, orders.size());
		
		mav.addObject("anyHasNoSupervisor", anyHasNoSupervisor);
		mav.addObject("subscriptions", ordPaginationConfig("subscriptions", index, orders, mav));
		
		return mav;
	}


	@RequestMapping(value = "/profile/subscriptions-finalized", method = { RequestMethod.GET })
	public ModelAndView subscriptionsFinalized(@RequestParam(value="start", required=false) Integer start, ModelMap model) {
		int index = 0;
		
		if(start != null) {
			index = start - 1;
		}
		
		ModelAndView mav = new ModelAndView("profile/subscriptions-finalized");
		
		String user = auth.getAuthentication().getName();
		List<Order> subscriptions = ord.findFinalizedBySubscriber(user);
		
		// The list is reduced to fit into a page after checking for publications with no supervisor.
		subscriptions = subscriptions.subList(index, subscriptions.size());
		
		mav.addObject("subscriptions", ordPaginationConfig("subscriptions-finalized", index, subscriptions, mav));
		
		return mav;
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
	
	public List<Publication> pubPaginationConfig (String paginationPrefix, int index, List<Publication> results, final ModelAndView mav) {
		int totalPublications = results.size() + index;

		if (results.size() > MY_PUBLICATIONS_PAGE_SIZE) { // I only display up to the number of publications of 1 page.
			results = results.subList(0, MY_PUBLICATIONS_PAGE_SIZE);
		}
		
		paginationGeneralConfig(paginationPrefix, index, totalPublications, MY_PUBLICATIONS_PAGE_SIZE, MY_PUBLICATIONS_MAX_PAGE_LINKS, mav);
		
		return results;
	}
	
	public List<Order> ordPaginationConfig (String paginationPrefix, int index, List<Order> results, final ModelAndView mav) {
		int totalPublications = results.size() + index;

		if (results.size() > MY_ORDERS_PAGE_SIZE) { // I only display up to the number of publications of 1 page.
			results = results.subList(0, MY_ORDERS_PAGE_SIZE);
		}
		
		paginationGeneralConfig(paginationPrefix, index, totalPublications, MY_ORDERS_PAGE_SIZE, MY_ORDERS_MAX_PAGE_LINKS, mav);

		return results;
	}

	public void paginationGeneralConfig (String paginationPrefix, int index, int totalPublications, int pageSize, int maxPages, final ModelAndView mav) {
		if (totalPublications > pageSize) { // There are at least 2 pages.
			mav.addObject("pages", true);
			mav.addObject("paginationPrefix", paginationPrefix);
			
			int first, last, currentPage = (index/pageSize) + 1;
			int maxPage = ((totalPublications-1)/pageSize) + 1;
			
			mav.addObject("currentPage", currentPage);
			
			if (currentPage <= (maxPages/2)) {
				first = 1;
				last = (maxPage > maxPages ? maxPages : maxPage);
				if (currentPage == 1) {
					mav.addObject("prevDisable", true);
				}
			} else {
				first = currentPage - (maxPages/2);
				int auxMaxLimit = currentPage + (maxPages/2);
				last = (maxPage > auxMaxLimit ? auxMaxLimit : maxPage);
			}
			if (currentPage == maxPage) {
				mav.addObject("nextDisable", true);
			}
			mav.addObject("firstPage", first);
			mav.addObject("lastPage", last);
			mav.addObject("currentPageIndex", ((currentPage-1)*pageSize)+1);
			mav.addObject("firstPageIndex", ((first-1)*pageSize)+1);
			mav.addObject("step", pageSize);
		}
	}
}