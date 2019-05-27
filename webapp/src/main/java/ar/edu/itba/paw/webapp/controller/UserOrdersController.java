package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.dto.OrderDTO;
import ar.edu.itba.paw.webapp.dto.OrderPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("users/{id}/orders")
@Controller
public class UserOrdersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PathParam("id")
    private Long userId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("page") final Integer page, @QueryParam("pageSize") final Integer pageSize) {
        if(page == null || pageSize == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<User> user = userService.findById(userId);

        if(!user.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            List<Order> userOrders = orderService.userOrders(user.get(), page, pageSize);
            Long userOrdersQuantity = orderService.userOrdersQuantity(user.get());

            OrderPageDTO pageDTO = new OrderPageDTO(userOrders, new Long(page), userOrdersQuantity, new Long(pageSize));

            return Response.ok(pageDTO).build();
        } catch (UnauthorizedAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
