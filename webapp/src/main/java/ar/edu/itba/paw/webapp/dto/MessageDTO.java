package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.User;

import java.util.Date;

public class MessageDTO {
  private String message;
  private UserDTO from;
  private UserDTO to;
  private Date date;
  private Long id;
  private Boolean seen;

  public MessageDTO(Message message) {
    this.message = message.getMessage();
    this.from = new UserDTO(message.getSender());
    this.to = new UserDTO(message.getReceiver());
    this.date = message.getCreatedAt();
    this.id = message.getId();
    this.seen = message.getReadByReceiver();
  }

  public MessageDTO() {
    // Empty constructor needed by JAX-RS
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public UserDTO getFrom() {
    return from;
  }

  public void setFrom(UserDTO from) {
    this.from = from;
  }

  public UserDTO getTo() {
    return to;
  }

  public void setTo(UserDTO to) {
    this.to = to;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getSeen() {
    return seen;
  }

  public void setSeen(Boolean seen) {
    this.seen = seen;
  }
}
