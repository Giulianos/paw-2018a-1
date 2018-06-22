package ar.edu.itba.paw.webapp.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.OrderService;
import ar.edu.itba.paw.interfaces.PublicationService;
import ar.edu.itba.paw.interfaces.UserService;
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
	private UserService us;
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
	
	@RequestMapping(value = "/profile/publications&start={first}")
	public ModelAndView publications(@PathVariable("first") String first, @RequestParam(value="newModal", defaultValue="false") Boolean newModal, @ModelAttribute("publicationForm") final PublicationForm form, ModelMap model) {
		if (!Pattern.matches("([2-9]|[1-9][0-9]+)", first)) {
			// If the index is invalid I go to the default search page.
			return new ModelAndView("redirect:/profile/publications");
		}

		int index = Integer.parseInt(first) - 1; // Index displayed in page differs by 1 from array index.
		
		String user = auth.getAuthentication().getName();
		List<Publication> publications = ps.findBySupervisor(user, index);
		
		if (publications.isEmpty() && index > 0) { // There are no results starting with the specified index.
			return new ModelAndView("redirect:/profile/publications");
		}
		
		ModelAndView mav = new ModelAndView("profile/publications");

		for (Publication publication : publications) {
			publication.setRemainingQuantity(ps.remainingQuantity(publication.getId()));
		}
		
		if(newModal) {
			model.addAttribute("shouldShowModal", true);
		}
		mav.addObject("publications", pubPaginationConfig("publications", index, publications, mav));
		
		return mav;
	}
	
	@RequestMapping(value = "/profile/publications")
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
		mav.addObject("publications", pubPaginationConfig("publications", 0, publications, mav));
		
		return mav;
	}
	
	@RequestMapping(value = "/profile/publications", method = { RequestMethod.POST })
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

	@RequestMapping(value = "/profile/subscriptions&start={first}")
	public ModelAndView subscriptions(@PathVariable("first") String first, ModelMap model) {
		if (!Pattern.matches("([2-9]|[1-9][0-9]+)", first)) {
			// If the index is invalid I go to the default search page.
			return new ModelAndView("redirect:/profile/subscriptions");
		}

		int index = Integer.parseInt(first) - 1; // Index displayed in page differs by 1 from array index.
		
		ModelAndView mav = new ModelAndView("profile/subscriptions");
		
		String user = auth.getAuthentication().getName();
		List<Order> subscriptions = ord.findBySubscriber(user, false);
		Boolean anyHasNoSupervisor = false;

		if (index > (subscriptions.size() - 1)) { // Out of bounds.
			return new ModelAndView("redirect:/profile/subscriptions");
		}
		
		for (Order order : subscriptions) {
			order.setPublication(ps.findById(order.getPublication_id()).get());
			order.getPublication().setRemainingQuantity(ps.remainingQuantity(order.getPublication_id()));
			if(!anyHasNoSupervisor && !ps.hasSupervisor(order.getPublication_id())) {
				anyHasNoSupervisor = true;
			}
		}
		
		// The list is reduced to fit into a page after checking for publications with no supervisor.
		subscriptions = subscriptions.subList(index, subscriptions.size());
		
		mav.addObject("anyHasNoSupervisor", anyHasNoSupervisor);
		mav.addObject("subscriptions", ordPaginationConfig("subscriptions", index, subscriptions, mav));
		
		return mav;
	}
	
	@RequestMapping(value = "/profile/subscriptions")
	public ModelAndView subscriptions() {
		ModelAndView mav = new ModelAndView("profile/subscriptions");
		
		String user = auth.getAuthentication().getName();
		List<Order> subscriptions = ord.findBySubscriber(user, false);
		Boolean anyHasNoSupervisor = false;
		
		for (Order order : subscriptions) {
			order.setPublication(ps.findById(order.getPublication_id()).get());
			order.getPublication().setRemainingQuantity(ps.remainingQuantity(order.getPublication_id()));
			if(!anyHasNoSupervisor && !ps.hasSupervisor(order.getPublication_id())) {
				anyHasNoSupervisor = true;
			}
		}
		
		mav.addObject("anyHasNoSupervisor", anyHasNoSupervisor);
		mav.addObject("subscriptions", ordPaginationConfig("subscriptions", 0, subscriptions, mav));
		
		return mav;
	}

	@RequestMapping(value = "/profile/subscriptions-finalized&start={first}")
	public ModelAndView subscriptionsFinalized(@PathVariable("first") String first, ModelMap model) {
		if (!Pattern.matches("([2-9]|[1-9][0-9]+)", first)) {
			// If the index is invalid I go to the default search page.
			return new ModelAndView("redirect:/profile/subscriptions-finalized");
		}

		int index = Integer.parseInt(first) - 1; // Index displayed in page differs by 1 from array index.
		
		ModelAndView mav = new ModelAndView("profile/subscriptions-finalized");
		
		String user = auth.getAuthentication().getName();
		List<Order> subscriptions = ord.findFinalizedBySubscriber(user);
		for (Order order : subscriptions) {
			order.setPublication(ps.findById(order.getPublication_id()).get());
			order.setSubscriberUser(us.findByUsername(order.getSubscriber()).get());
			order.getPublication().setSupervisorUser(us.findByUsername(order.getPublication().getSupervisor()).get());
			ps.loadPublicationSubscribers(order.getPublication());
		}

		if (index > (subscriptions.size() - 1)) { // Out of bounds.
			return new ModelAndView("redirect:/profile/subscriptions-finalized");
		}
		
		// The list is reduced to fit into a page after checking for publications with no supervisor.
		subscriptions = subscriptions.subList(index, subscriptions.size());
		
		mav.addObject("subscriptions", ordPaginationConfig("subscriptions-finalized", index, subscriptions, mav));
		
		return mav;
	}
	
	@RequestMapping(value = "/profile/subscriptions-finalized")
	public ModelAndView subscriptionsFinalized() {
		ModelAndView mav = new ModelAndView("profile/subscriptions-finalized");
		
		String user = auth.getAuthentication().getName();
		
		List<Order> subscriptions = ord.findFinalizedBySubscriber(user);
		for (Order order : subscriptions) {
			order.setPublication(ps.findById(order.getPublication_id()).get());
			order.setSubscriberUser(us.findByUsername(order.getSubscriber()).get());
			order.getPublication().setSupervisorUser(us.findByUsername(order.getPublication().getSupervisor()).get());
			ps.loadPublicationSubscribers(order.getPublication());
		}
		
		mav.addObject("subscriptions", ordPaginationConfig("subscriptions-finalized", 0, subscriptions, mav));
		
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