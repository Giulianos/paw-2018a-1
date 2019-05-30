package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.TagService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.Tag;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

      Image addedImage = imageDao.addToPublication(publication.get(), base64);

      return Optional.of(addedImage);
  }
}
