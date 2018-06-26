package ar.edu.itba.paw.webapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheOperationInvoker.ThrowableWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import ar.edu.itba.paw.interfaces.MessageService;
import ar.edu.itba.paw.interfaces.OrderService;
import ar.edu.itba.paw.interfaces.PublicationService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.auth.IAuthenticationFacade;

@Controller
public class MessagingController {
	
	@Autowired
	private IAuthenticationFacade auth;
	
	@Autowired
	MessageService ms;
	
	@Autowired
	OrderService os;
	
	@Autowired
	PublicationService ps;
	
	@Autowired
	UserService us;
	
	@RequestMapping(value = "profile/messaging/{publication_id}")
	public ModelAndView messagingForOrder(@PathVariable("publication_id") Long publication_id) {
		ModelAndView mav = new ModelAndView("profile/messaging");
		
		Optional<Order> order = os.findByPublicationAndSubscriber(publication_id, auth.getAuthentication().getName());
		
		List<Message> messages = ms.retreiveFromOrder(order.get());
		
		mav.addObject("messages", messages);
		mav.addObject("my_username", auth.getAuthentication().getName());
		mav.addObject("order", order.get());
		return mav;
	}
	
	@RequestMapping(value = "profile/messaging/{publication_id}", method= {RequestMethod.POST})
	public ModelAndView messagingForOrder(@PathVariable("publication_id") Long publication_id, @RequestParam("message_body") String message_body) {
		Optional<User> me = us.findByUsername(auth.getAuthentication().getName());
		Optional<Order> order = os.findByPublicationAndSubscriber(publication_id, auth.getAuthentication().getName());
		
		ms.sendMessage(me.get(), order.get(), HtmlUtils.htmlEscape(message_body));
		
		return new ModelAndView("redirect:/profile/messaging/"+publication_id);
	}
	
	/* Used by supervisor */
	
	@RequestMapping(value = "profile/messaging/{publication_id}/{subscriber_id}")
	public ModelAndView messagingForOrder(@PathVariable("subscriber_id") Long subscriber_id, @PathVariable("publication_id") Long publication_id) {
		
		ModelAndView mav = new ModelAndView("profile/messaging");
		
		Optional<User> subscriber = us.findById(subscriber_id);
		Optional<Order> order = os.findByPublicationAndSubscriber(publication_id, subscriber.get().getUsername());
		
		/** Allow only supervisor to communicate with other subscribers */
		if(!order.get().getPublication().getSupervisor().getUsername().equals(auth.getAuthentication().getName())) {
			return new ModelAndView("redirect:/profile/messaging/"+publication_id);
		}
		
		List<Message> messages = ms.retreiveFromOrder(order.get());
		
		mav.addObject("messages", messages);
		mav.addObject("my_username", auth.getAuthentication().getName());
		mav.addObject("order", order.get());
		return mav;
	}
	
	@RequestMapping(value = "profile/messaging/{publication_id}/{subscriber_id}", method= {RequestMethod.POST})
	public ModelAndView messagingForOrder(@PathVariable("subscriber_id") Long subscriber_id, @PathVariable("publication_id") Long publication_id, @RequestParam("message_body") String message_body) {
		Optional<User> me = us.findByUsername(auth.getAuthentication().getName());
		Optional<User> subscriber = us.findById(subscriber_id);
		Optional<Order> order = os.findByPublicationAndSubscriber(publication_id, subscriber.get().getUsername());
		
		/** Allow only supervisor to communicate with other subscribers */
		if(!order.get().getPublication().getSupervisor().getUsername().equals(auth.getAuthentication().getName())) {
			return new ModelAndView("redirect:/profile/messaging/"+publication_id);
		}
		
		ms.sendMessage(me.get(), order.get(), HtmlUtils.htmlEscape(message_body));
		
		return new ModelAndView("redirect:/profile/messaging/"+publication_id+"/"+subscriber_id.toString());
	}
	
}
