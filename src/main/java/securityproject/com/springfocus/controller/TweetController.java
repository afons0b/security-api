package securityproject.com.springfocus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import securityproject.com.springfocus.domain.Tweet;
import securityproject.com.springfocus.repository.TweetRepository;
import securityproject.com.springfocus.repository.UserRepository;
import securityproject.com.springfocus.request.TweetRequest;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @PostMapping("/tweets")
    public ResponseEntity<Void> saveTweet(@RequestBody TweetRequest request, JwtAuthenticationToken token){

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(request.content());

        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }
}
