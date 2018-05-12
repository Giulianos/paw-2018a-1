package ar.edu.itba.paw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.ConfirmedOrderDao;
import ar.edu.itba.paw.interfaces.ConfirmedOrders;
import ar.edu.itba.paw.model.ConfirmedOrder;

@Primary
@Service
public class ConfirmedOrdersImpl implements ConfirmedOrders {
	@Autowired
	private ConfirmedOrderDao confirmedOrderDao;

	@Override
	public List<ConfirmedOrder> findByBuyer(String username) {
		return confirmedOrderDao.findByBuyer(username);
	}

	@Override
	public List<ConfirmedOrder> findByPublicationId(long publication_id) {
		return confirmedOrderDao.findByPublicationId(publication_id);
	}

	@Override
	public ConfirmedOrder create(long publication_id, String buyer, int quantity) {
		return confirmedOrderDao.create(publication_id,buyer,quantity);
	}

	@Override
	public boolean confirmPayment(long publication_id, String buyer) {
		return confirmedOrderDao.confirmPayment(publication_id,buyer);
	}

	@Override
	public boolean confirmDelivery(long publication_id, String buyer) {
		return confirmedOrderDao.confirmDelivery(publication_id,buyer);
	}

	@Override
	public boolean areFulfilled(long publication_id) {
		List<ConfirmedOrder> orders = findByPublicationId(publication_id);

		for (ConfirmedOrder order : orders) {
			if (!order.getPaid() || !order.getReceived()) {
				return false;
			}
		}		
		return true;
	}

	@Override
	public boolean delete(long publication_id) {
		return confirmedOrderDao.delete(publication_id);
	}
}