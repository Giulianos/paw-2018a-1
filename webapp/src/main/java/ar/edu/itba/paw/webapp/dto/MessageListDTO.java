package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageListDTO {
  private List<MessageDTO> messages;

  public MessageListDTO(final List<Message> messages) {
    this.messages = messages.stream().map(MessageDTO::new).collect(Collectors.toList());
  }

  public MessageListDTO() {
    // Empty constructor needed by JAX-RS
  }

  public List<MessageDTO> getMessages() {
    return messages;
  }

  public void setMessages(List<MessageDTO> messages) {
    this.messages = messages;
  }
}
