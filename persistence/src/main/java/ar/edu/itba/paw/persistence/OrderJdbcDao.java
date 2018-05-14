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

import com.sun.org.apache.bcel.internal.generic.RETURN;

import ar.edu.itba.paw.interfaces.OrderDao;
import ar.edu.itba.paw.model.Order;

@Primary
@Repository
public class OrderJdbcDao implements OrderDao {
	private	JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public OrderJdbcDao(final DataSource ds) {
		jdbcTemplate = new	JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
						.withTableName("orders")
						.usingColumns("subscriber", "publication_id", "quantity");
	}

	private final static RowMapper<Order> ROW_MAPPER =
			(ResultSet rs, int rowNum) -> new Order(rs.getLong("publication_id"),rs.getString("subscriber"),rs.getInt("quantity"),rs.getBoolean("is_confirmed"));

	private final static RowMapper<Integer> ORDER_QUANTITY_MAPPER =
			(ResultSet rs, int rowNum) -> rs.getInt("ordersQuantity");

			
	@Override
	public List<Order> findBySubscriber(String username) {
		return	jdbcTemplate.query("SELECT * FROM orders WHERE subscriber = ?;", ROW_MAPPER, username);
	}
	
	public List<Order> findFinalizedBySubscriber(String username) {
		return	jdbcTemplate.query("SELECT * FROM orders WHERE subscriber = ? AND is_confirmed=true;", ROW_MAPPER, username);
	}

	@Override
	public List<Order> findByPublicationId(long publication_id) {
		return	jdbcTemplate.query("SELECT * FROM orders WHERE publication_id = ?;", ROW_MAPPER, publication_id);
	}

	@Override
	public Order create(long publication_id, String subscriber, int quantity) {
		int ordersQuantity =jdbcTemplate.query("SELECT COALESCE(sum(quantity),0) as ordersQuantity FROM orders WHERE publication_id = ? and subscriber = ?;", ORDER_QUANTITY_MAPPER, publication_id,subscriber).get(0);
		if(ordersQuantity > 0) {
			jdbcTemplate.update("UPDATE orders SET quantity = ? WHERE publication_id = ? and subscriber = ?",ordersQuantity+quantity ,publication_id,subscriber);
			return new Order(publication_id, subscriber, ordersQuantity+quantity);
		}
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("publication_id", publication_id);
		args.put("subscriber", subscriber);
		args.put("quantity", quantity);
		
		jdbcInsert.execute(args);
		
		return new Order(publication_id,subscriber,quantity);
	}

	@Override
	public boolean confirm(long publication_id, String subscriber) {
		return jdbcTemplate.update("UPDATE orders SET is_confirmed = ? WHERE publication_id = ? and subscriber = ?",true,publication_id,subscriber) > 0;
	}

	@Override
	public boolean delete(long publication_id) {
		return jdbcTemplate.update("DELETE FROM orders WHERE publication_id = ?",publication_id) > 0;
	}
	
	@Override
	public boolean delete(long publication_id, String subscriber) {
		return jdbcTemplate.update("DELETE FROM orders WHERE publication_id = ? and subscriber = ?",publication_id, subscriber) > 0;
	}
}