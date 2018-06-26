package ar.edu.itba.paw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.MessageDao;
import ar.edu.itba.paw.interfaces.MessageService;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.User;

@Primary
@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageDao messageDao;

	@Override
	@Transactional
	public Optional<Message> sendMessage(User me, Order order, String text) {
		User from = me;
		User to;
		if(order.getSubscriber().equals(me)) {
			to = order.getPublication().getSupervisor();
		} else {
			to = order.getSubscriber();
		}
		
		return messageDao.create(from, to, order, text);
	}

	@Override
	public List<Message> retreiveFromOrder(Order order) {
		return messageDao.retreiveFromOrder(order);
	}
	
}
