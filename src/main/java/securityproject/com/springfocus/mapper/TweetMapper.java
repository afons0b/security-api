package securityproject.com.springfocus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import securityproject.com.springfocus.domain.Tweet;
import securityproject.com.springfocus.domain.User;
import securityproject.com.springfocus.request.TweetPostRequest;
import securityproject.com.springfocus.response.FeedItemResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TweetMapper {

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "tweetId", source = "id")
    FeedItemResponse toFeedItem(Tweet tweet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTimeStamp", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "content", source = "request.content")
    Tweet toEntity(TweetPostRequest request, User user);
}
