package com.TinyTwitt;

import java.util.Collection;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import static com.TinyTwitt.OfyService.ofy;

import com.TinyTwitt.Message;

public class MessageRepository {
	
	private static MessageRepository messageRepository = null;

	static {
		ObjectifyService.register(Message.class);
	}

	private MessageRepository() {
	}

	public static synchronized MessageRepository getInstance() {
		if (null == messageRepository) {
			messageRepository = new MessageRepository();
		}
		return messageRepository;
	}

	public Collection<Message> findMessages() {
		List<Message> messages = ofy().load().type(Message.class).list();
		return messages;
	}

	public Message createMessage(Message message) {
		ofy().save().entity(message).now();
		return message;
	}

	public Message updateMessage(Message updatedMessage) {
		if (updatedMessage.getId() == null) {
			return null;
		}
		Message message = ofy().load().type(Message.class).id(updatedMessage.getId()).now();
		if (updatedMessage.getDate() != null) {
			message.setDate(updatedMessage.getDate());
		}
		if (updatedMessage.getBody() != null) {
			message.setBody(updatedMessage.getBody());
		}
		if (updatedMessage.getOwner() != null) {
		message.setOwner(updatedMessage.getOwner());
		}
		if (updatedMessage.getSender() != null) {
		message.setSender(updatedMessage.getSender());
		}
		ofy().save().entity(message).now();
		return message;
	}
	
	public void removeMessage(Long id) {
		if (id == null) {
			return;
		}
		ofy().delete().type(Message.class).id(id).now();
	}
	
	public Message findMessage(Long id) {
		Message message = ofy().load().type(Message.class).id(id).now();
		return message;
	}
	 
}