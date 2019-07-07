package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.PublicationFulfilledException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.TagService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.events.NewSupervisorEvent;
import ar.edu.itba.paw.model.events.PublicationPurchasedEvent;
import ar.edu.itba.paw.model.events.SupervisorLeftEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service
public class PublicationServiceImpl implements PublicationService {

  @Autowired
  private PublicationDao publicationDao;

  @Autowired
  private UserService userService;

  @Autowired
  private TagService tagService;

  @Autowired
  private ImageDao imageDao;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Override
  public Optional<Publication> findById(Long id) {
    return publicationDao.findById(id);
  }

  @Override
  public Publication create(
      final String description,
      final Double unitPrice,
      final Long quantity,
      final String detailedDescription,
      final List<String> tags
  ) {
    SecurityContext securityContext = SecurityContextHolder.getContext();

    final Optional<User> supervisor = userService.findByEmail(
        securityContext.getAuthentication().getName()
    );

    if (supervisor.isPresent()) {

      Publication createdPublication = publicationDao.create(
          supervisor.get(),
          description,
          unitPrice,
          quantity,
          detailedDescription
      );

      /** Add tags to publication */
      tags.stream().map(tagService::createOrRetrieve).forEach(createdPublication::addTag);

      /** Persist those relations */
      publicationDao.update(createdPublication);

      return createdPublication;
    }

    throw new IllegalStateException("User is not logged in but it accessed create publication");
  }

  @Override
  @Transactional
  public Optional<Image> addImage(final String userEmail, final Long publicationId, String base64) throws EntityNotFoundException, UnauthorizedAccessException {

      Optional<Publication> publication = publicationDao.findById(publicationId);

      if(!publication.isPresent()) {
        throw new EntityNotFoundException();
      }

      if(!publication.get().getSupervisor().getEmail().equals(userEmail)) {
        throw new UnauthorizedAccessException("Only supervisor can add images");
      }

      if(publication.get().getImages().size() >= 5) {
        throw new IllegalStateException("Publication can't have more than 5 images");
      }

      Image addedImage = imageDao.addToPublication(publication.get(), base64);

      return Optional.of(addedImage);
  }

    @Override
    @Transactional
    public void leavePublication(Long id) throws EntityNotFoundException, UnauthorizedAccessException, PublicationFulfilledException {
        Optional<Publication> publication = publicationDao.findById(id);

        if(!publication.isPresent()) {
            throw new EntityNotFoundException();
        }

        String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!loggedUserEmail.equals(publication.get().getSupervisor().getEmail())) {
            throw new UnauthorizedAccessException("Only supervisor can leave publication");
        }

        if(publication.get().getState() != PublicationState.IN_PROGRESS) {
            throw new PublicationFulfilledException("Publication can be left only while in progress");
        }

        // If the publication doesn't have orders (or the only one is form the supervisor), delete it
        if(publication.get().getOrders().stream().allMatch(o -> o.getOrderer().getEmail().equals(loggedUserEmail))){
          publicationDao.deleteById(publication.get().getId());
          // End process, we don't notify/update anything
          return;
        }

        publication.get().setState(PublicationState.ORPHAN);
        publication.get().setSupervisor(null);

        // Publish event (for notification purposes)
        eventPublisher.publishEvent(new SupervisorLeftEvent(publication.get()));

        publicationDao.update(publication.get());
    }

  @Override
  @Transactional
  public void adoptPublication(Long id) throws EntityNotFoundException, UnauthorizedAccessException {
    Optional<Publication> publication = findById(id);

    if(!publication.isPresent()) {
      throw new EntityNotFoundException();
    }

    String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();


    // Check if the user trying to adopt the publication is an orderer
    if(publication.get().getOrders().stream().noneMatch(o -> o.getOrderer().getEmail().equals(loggedUserEmail))) {
      throw new UnauthorizedAccessException("Only an orderer can adopt a publication");
    }

    Optional<User> newSupervisor = userService.findByEmail(loggedUserEmail);

    if(!newSupervisor.isPresent()) {
      throw new IllegalStateException();
    }

    publication.get().setSupervisor(newSupervisor.get());
    publication.get().setState(PublicationState.IN_PROGRESS);
    publicationDao.update(publication.get());

    eventPublisher.publishEvent(new NewSupervisorEvent(publication.get()));
  }

  @Override
  public void markAsPurchased(Long id) throws EntityNotFoundException, UnauthorizedAccessException {
    Optional<Publication> publication = findById(id);

    String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

    if(!publication.isPresent()) {
      throw new EntityNotFoundException();
    }

    if(!publication.get().getSupervisor().getEmail().equals(loggedUserEmail)) {
      throw new UnauthorizedAccessException("Only the supervisor can mark the publication as purchased");
    }

    if(!publication.get().getState().equals(PublicationState.FULFILLED)) {
      throw new IllegalStateException();
    }

    publication.get().setState(PublicationState.PURCHASED);
    publicationDao.update(publication.get());

    eventPublisher.publishEvent(new PublicationPurchasedEvent(publication.get()));
  }

  @Override
  @Transactional
  public List<Publication> userPublications() throws UnauthorizedAccessException {
    final String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

    if(loggedUserEmail == null) {
      throw new UnauthorizedAccessException("only logged user can access publications");
    }

    return publicationDao.userPublications(loggedUserEmail);
  }

  @Override
  @Transactional
  public List<Publication> latest(Integer quantity) {
    return publicationDao.latestPublications(quantity);
  }
}
