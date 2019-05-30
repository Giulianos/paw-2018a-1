package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Publication;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Primary
@Repository
public class ImageHibernateDao implements ImageDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Image addToPublication(Publication publication, String base64) {
    Image newImage = new Image(base64);
    publication.addImage(newImage);

    em.persist(newImage);

    return newImage;
  }
}
