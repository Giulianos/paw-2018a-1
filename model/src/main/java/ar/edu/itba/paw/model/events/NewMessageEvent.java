package ar.edu.itba.paw.model.events;

import ar.edu.itba.paw.model.Message;
import org.springframework.context.ApplicationEvent;

public class NewMessageEvent extends ApplicationEvent {
  public NewMessageEvent(Message message) {
    super(message);
  }

  public Message getSource() {
    return (Message)super.getSource();
  }
}
