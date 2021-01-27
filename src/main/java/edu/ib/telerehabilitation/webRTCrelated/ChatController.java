package edu.ib.telerehabilitation.webRTCrelated;

import edu.ib.telerehabilitation.model.User;
import edu.ib.telerehabilitation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {

	private UserService userService;

	@Autowired
	public ChatController(UserService userService) {
		this.userService = userService;
	}

	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message sendMessage(Message message, Authentication authentication) throws Exception {
		User user = userService.getCurrentUser(authentication);
        Thread.sleep(1000); // simulated delay
        return new Message(user.getUserName()+ ": " + HtmlUtils.htmlEscape(message.getContent()) + " ");
    }

}
