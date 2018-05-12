package ar.edu.itba.paw.persistence;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.ConfirmedOrderDao;
import ar.edu.itba.paw.model.ConfirmedOrder;

@Primary
@Repository
public class ConfirmedOrderJdbcDao implements ConfirmedOrderDao {
	private	JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public ConfirmedOrderJdbcDao(final DataSource ds) {
		jdbcTemplate = new	JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
						.withTableName("confirmedOrders")
						.usingColumns("buyer", "publication_id", "quantity");
	}

	private final static RowMapper<ConfirmedOrder> ROW_MAPPER =
			(ResultSet rs, int rowNum) -> new ConfirmedOrder(rs.getLong("publication_id"),rs.getString("buyer"),rs.getInt("quantity"),rs.getBoolean("is_paid"),rs.getBoolean("is_received"));

	@Override
	public List<ConfirmedOrder> findByBuyer(String username) {
		return	jdbcTemplate.query("SELECT * FROM confirmedOrders WHERE buyer = ?;", ROW_MAPPER, username);
	}

	@Override
	public List<ConfirmedOrder> findByPublicationId(long publication_id) {
		return	jdbcTemplate.query("SELECT * FROM confirmedOrders WHERE publication_id = ?;", ROW_MAPPER, publication_id);
	}

	@Override
	public ConfirmedOrder create(long publication_id, String buyer, int quantity) {
		final Map<String, Object> args = new HashMap<>();
		
		args.put("publication_id", publication_id);
		args.put("buyer", buyer);
		args.put("quantity", quantity);
		
		jdbcInsert.execute(args);
		
		return new ConfirmedOrder(publication_id,buyer,quantity);
	}

	@Override
	public boolean confirmPayment(long publication_id, String buyer) {
		return jdbcTemplate.update("UPDATE confirmedOrders SET is_paid = ? WHERE publication_id = ? and buyer = ?",true,publication_id,buyer) > 0;
	}

	@Override
	public boolean confirmDelivery(long publication_id, String buyer) {
		return jdbcTemplate.update("UPDATE confirmedOrders SET is_received = ? WHERE publication_id = ? and buyer = ?",true,publication_id,buyer) > 0;
	}

	@Override
	public boolean delete(long publication_id) {
		return jdbcTemplate.update("DELETE FROM confirmedOrders WHERE publication_id = ?",publication_id) > 0;
	}
}