package securityproject.com.springfocus.request;

import jakarta.websocket.server.ServerEndpoint;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TweetPostRequest {

    private String content;
}
