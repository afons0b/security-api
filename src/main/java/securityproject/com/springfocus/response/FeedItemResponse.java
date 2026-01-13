package securityproject.com.springfocus.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedItemResponse {

    private Long tweetId;
    private String content;
    private String name;
}
