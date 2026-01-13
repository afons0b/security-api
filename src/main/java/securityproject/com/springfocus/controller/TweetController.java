package securityproject.com.springfocus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import securityproject.com.springfocus.request.TweetPostRequest;
import securityproject.com.springfocus.response.FeedGetResponse;
import securityproject.com.springfocus.service.TweetService;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/token-jwt")
public class TweetController {
    private final TweetService service;

    @PostMapping("/tweets")
    public ResponseEntity<Void> saveTweet(@RequestBody TweetPostRequest request, JwtAuthenticationToken token){
        service.createTweet(request, token.getName());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id, JwtAuthenticationToken token) {

        var userUuid = UUID.fromString(token.getName());
        var isAdmin = token.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("SCOPE_ADMIN"));

        service.deleteTweet(id, userUuid, isAdmin);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedGetResponse> feedResponse(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){

        var feed = service.getFeed(page, pageSize);

        return ResponseEntity.ok(feed);
    }
}
