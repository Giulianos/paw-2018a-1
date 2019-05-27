package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.webapp.utils.URLResolver;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPageDTO {
    private List<OrderDTO> orders;
    private Long nextPage;
    private Long totalPages;

    public OrderPageDTO(List<Order> orders, Long currentPage, Long ordersQuantity, Long pageSize) {
        this.orders = orders.stream().map(OrderDTO::new).collect(Collectors.toList());
        this.totalPages = (ordersQuantity + pageSize - 1L) / pageSize;
        this.nextPage = currentPage + 1;
    }

    public OrderPageDTO() {
        // Empty constructor needed by JAX-RS
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public Long getNextPage() {
        return nextPage;
    }

    public void setNextPage(Long nextPage) {
        this.nextPage = nextPage;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
}
