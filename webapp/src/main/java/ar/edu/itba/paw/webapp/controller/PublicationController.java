package ar.edu.itba.paw.webapp.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.OrderService;
import ar.edu.itba.paw.interfaces.PublicationService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.webapp.auth.IAuthenticationFacade;
import ar.edu.itba.paw.webapp.form.OrderForm;

@Controller
public class PublicationController {
	private final static int PAGE_SIZE = 5;
	private final static int MAX_PAGE_LINKS = 10;
    @Autowired
    private MessageSource messageSource;
	@Autowired
	private PublicationService ps;
	@Autowired
	private OrderService ord;
	@Autowired
	private UserService users;
	@Autowired
	private IAuthenticationFacade auth;
	@Autowired
	private EmailService emails;
	
	@RequestMapping(value = "/order", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("orderForm") final OrderForm form, final BindingResult errors, HttpServletRequest request, ModelMap model) {
		boolean isValid = validOrder(form,model);

		if (errors.hasErrors() || !isValid) {
			ModelAndView modelAndView = new ModelAndView("error");
			return modelAndView;
		}
		
		String username = auth.getAuthentication().getName();
		
		Order order = ord.create(ps.findById(Long.parseLong(form.getPublicationId())).get(), users.findByUsername(username).get(), Integer.parseInt(form.getQuantity()));
		
		boolean confirmed = ps.confirm(Long.parseLong(form.getPublicationId()));
		
		if(confirmed) {
			emails.notifyPublicationFulfillment(order.getPublication());
		}

		return new ModelAndView("redirect:/profile/subscriptions");
	}

	public boolean validOrder(OrderForm form, ModelMap model) {
		
		Integer quantity = Integer.parseInt(form.getQuantity());
		Long publicationId = Long.parseLong(form.getPublicationId());
		Integer publicationRemainingQuantity = ps.findById(publicationId).get().getRemainingQuantity();
		
		if(quantity > publicationRemainingQuantity)
			return false;
		
		return true;
	}
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public ModelAndView search(@Valid @ModelAttribute("orderForm") final OrderForm form, @RequestParam(value="keywords", required=false) String keywords, @RequestParam(value="start", required=false) Integer start) {
		int index = 0;
		
		if(start != null) {
			index = start - 1;
		}
		
		if(keywords == null) {
			keywords="";
		}

		List<Publication> results = ps.findByDescription(keywords, index, true, true);

		if (results.isEmpty() && index > 0) { // There are no results starting with the specified index.
			return new ModelAndView("redirect:/search/" + keywords);
		}

		final ModelAndView mav = new ModelAndView("search");

		mav.addObject("resultList", paginationConfig(index, results, mav));
		mav.addObject("searchedKeyword", keywords);
		mav.addObject("paginationPrefix", keywords);

		return mav;
	}
	
	@RequestMapping(value = "/profile/subscriptions/supervise", method = { RequestMethod.POST })
	public ModelAndView superviseSubscription(@RequestParam(value="publication_id") Integer publication_id) {
		String user = auth.getAuthentication().getName();
		
		ps.setNewSupervisor(user, publication_id);
		
		return new ModelAndView("redirect:/profile/subscriptions");
	}
	
	@RequestMapping(value = "/profile/subscriptions/erase", method = { RequestMethod.POST })
	public ModelAndView eraseSubscription(@RequestParam(value="publication_id") Integer publication_id) {
		
		if(!ord.areConfirmed(publication_id)) {
			ord.delete(publication_id, auth.getAuthentication().getName());
		}
		
		return new ModelAndView("redirect:/profile/subscriptions");
	}
	
	@RequestMapping(value = "/profile/publications/erase", method = { RequestMethod.POST })
	public ModelAndView erasePublication(@RequestParam(value="publication_id") Integer publication_id) {
		
		List<Order> list = ord.findByPublicationId(publication_id);
		if(list.size() == 0) {
			ps.delete(publication_id);
		} else if(list.size() == 1) {
			Order order = list.get(0);
			if(order.getSubscriber().getUsername().equals(auth.getAuthentication().getName())) {
				if(order.getPublication().getRemainingQuantity() != 0) {
					ps.delete(publication_id);
				}
			}
		} else {
			ps.setNewSupervisor(null, publication_id);
			ord.delete(publication_id, auth.getAuthentication().getName());
		}
		
		return new ModelAndView("redirect:/profile/publications");
	}
	
	@RequestMapping(value = "/profile/publications/check", method = { RequestMethod.POST })
	public ModelAndView checkPublication(@RequestParam(value="publication_id") Integer publication_id) {
		
		ps.check(publication_id);
		
		return new ModelAndView("redirect:/profile/publications");
	}
	
	public List<Publication> paginationConfig (int index, List<Publication> results, final ModelAndView mav) {
		int totalPublications = results.size() + index;

		if (results.size() > PAGE_SIZE) { // I only display up to the number of publications of 1 page.
			results = results.subList(0, PAGE_SIZE);
		}
		
		if (totalPublications > PAGE_SIZE) { // There are at least 2 pages.
			mav.addObject("pages", true);
			
			int first, last, currentPage = (index/PAGE_SIZE) + 1;
			int maxPage = ((totalPublications-1)/PAGE_SIZE) + 1;
			
			mav.addObject("currentPage", currentPage);
			
			if (currentPage <= (MAX_PAGE_LINKS/2)) {
				first = 1;
				last = (maxPage > MAX_PAGE_LINKS ? MAX_PAGE_LINKS : maxPage);
				if (currentPage == 1) {
					mav.addObject("prevDisable", true);
				}
			} else {
				first = currentPage - (MAX_PAGE_LINKS/2);
				int auxMaxLimit = currentPage + (MAX_PAGE_LINKS/2);
				last = (maxPage > auxMaxLimit ? auxMaxLimit : maxPage);
			}
			if (currentPage == maxPage) {
				mav.addObject("nextDisable", true);
			}
			mav.addObject("firstPage", first);
			mav.addObject("lastPage", last);
			mav.addObject("currentPageIndex", ((currentPage-1)*PAGE_SIZE)+1);
			mav.addObject("firstPageIndex", ((first-1)*PAGE_SIZE)+1);
			mav.addObject("step", PAGE_SIZE);
		}
		return results;
	}
}