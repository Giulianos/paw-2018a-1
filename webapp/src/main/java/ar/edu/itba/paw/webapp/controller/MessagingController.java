package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessagingController {

	
	@RequestMapping(value = "profile/messaging/{order}")
	public ModelAndView messagingForOrder(@PathVariable("order") String orderId) {
		ModelAndView mav = new ModelAndView("profile/messaging");
		mav.addObject("order_id", orderId);
		return mav;
	}
	
	
}
