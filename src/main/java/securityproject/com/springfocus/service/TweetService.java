package securityproject.com.springfocus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import securityproject.com.springfocus.mapper.TweetMapper;
import securityproject.com.springfocus.repository.TweetRepository;
import securityproject.com.springfocus.repository.UserRepository;
import securityproject.com.springfocus.request.TweetPostRequest;
import securityproject.com.springfocus.response.FeedGetResponse;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final TweetMapper mapper;

    public void createTweet(TweetPostRequest request, String userUuid) {
        var user = userRepository.findById(UUID.fromString(userUuid))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        var tweet = mapper.toEntity(request, user);

        tweetRepository.save(tweet);
    }

    public void deleteTweet(Long tweetId, UUID userUuid, boolean isAdmin) {
        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet not found"));

        var isOwner = tweet.getUser().getUuid().equals(userUuid);

        if (isAdmin || isOwner) {
            tweetRepository.deleteById(tweetId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete this tweet");
        }
    }

    public FeedGetResponse getFeed(int page, int pageSize) {
        //Busca paginada ordenada por data (mais recentes primeiro)
        var tweetsPage = tweetRepository.findAll(
                PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimeStamp")
        );

        var tweetDtos = tweetsPage.map(mapper::toFeedItem);

        // 3. Monta o objeto de resposta final com os metadados da página
        return new FeedGetResponse(
                tweetDtos.getContent(),
                page,
                pageSize,
                tweetsPage.getTotalPages(),
                tweetsPage.getTotalElements()
        );
    }
}
