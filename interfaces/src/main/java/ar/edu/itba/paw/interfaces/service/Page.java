package ar.edu.itba.paw.interfaces.service;

import java.math.BigInteger;
import java.util.List;

public class Page<T> {
  List<T> result;
  Integer totalResultSize;
  Integer currentPage;
  Integer pageSize;

  public Page(List<T> result, Integer totalResultSize, Integer currentPage, Integer pageSize) {
    this.result = result;
    this.totalResultSize = totalResultSize;
    this.currentPage = currentPage;
    this.pageSize = pageSize;
  }

  public List<T> getResult() {
    return result;
  }

  public Integer getTotalResultSize() {
    return totalResultSize;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public Integer getPageSize() {
    return pageSize;
  }
}
