package securityproject.com.springfocus.response;

import lombok.*;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedGetResponse {
    private List<FeedItemResponse> feedItemResponses;
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalElements;

}
