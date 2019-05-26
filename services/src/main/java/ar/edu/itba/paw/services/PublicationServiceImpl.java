package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Primary
@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationDao publicationDao;

    @Autowired
    private UserService userService;

    @Override
    public Optional<Publication> findById(Long id) {
        return publicationDao.findById(id);
    }

    @Override
    public Publication create(String description, Double unitPrice, Long quantity, String detailedDescription) {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        final Optional<User> supervisor = userService.findByEmail(
                securityContext.getAuthentication().getName()
        );

        if(supervisor.isPresent()) {
            return publicationDao.create(
                    supervisor.get(),
                    description,
                    unitPrice,
                    quantity,
                    detailedDescription
            );
        }

        throw new IllegalStateException("User is not logged in but it accessed create publication");
    }
}
