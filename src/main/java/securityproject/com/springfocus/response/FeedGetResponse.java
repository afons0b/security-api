package securityproject.com.springfocus.response;

import java.util.List;

public record FeedGetResponse(List<FeedItemResponse> feedItemResponses,
                              int page,
                              int pageSize,
                              int totalPages,
                              long totalElements) {
}
