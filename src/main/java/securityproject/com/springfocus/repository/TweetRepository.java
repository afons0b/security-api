package securityproject.com.springfocus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import securityproject.com.springfocus.domain.Role;
import securityproject.com.springfocus.domain.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
