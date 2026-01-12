package securityproject.com.springfocus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import securityproject.com.springfocus.domain.Role;
import securityproject.com.springfocus.domain.Tweet;
import securityproject.com.springfocus.repository.TweetRepository;
import securityproject.com.springfocus.repository.UserRepository;
import securityproject.com.springfocus.request.TweetRequest;
import securityproject.com.springfocus.response.FeedGetResponse;
import securityproject.com.springfocus.response.FeedItemResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/token-jwt")
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

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id, JwtAuthenticationToken token) {

        var tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var userUuid = UUID.fromString(token.getName());

        var isAdmin = token.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("SCOPE_ADMIN"));

        if (isAdmin || tweet.getUser().getUuid().equals(userUuid)) {
            tweetRepository.deleteById(id);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedGetResponse> feedResponse(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        var tweets = tweetRepository
                .findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimeStamp"))
                .map(tweet ->
                        new FeedItemResponse(tweet.getId(), tweet.getContent(), tweet.getUser().getName()));

        return ResponseEntity.ok(new FeedGetResponse(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));
    }
}
