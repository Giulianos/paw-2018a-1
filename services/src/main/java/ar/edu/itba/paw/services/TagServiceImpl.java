package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.TagDao;
import ar.edu.itba.paw.interfaces.service.TagService;
import ar.edu.itba.paw.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class TagServiceImpl implements TagService {

  @Autowired
  private TagDao tagDao;

  @Override
  public List<Tag> list(String keyword, Integer quantity) {
    return tagDao.list(keyword, quantity);
  }
}
