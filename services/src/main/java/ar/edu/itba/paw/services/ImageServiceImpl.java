package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
public class ImageServiceImpl implements ImageService {

  @Autowired
  private ImageDao imageDao;

  @Override
  public Optional<Image> findById(Long id) {
    return imageDao.findById(id);
  }
}
