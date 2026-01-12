package securityproject.com.springfocus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import securityproject.com.springfocus.annotations.EncodeMapping;
import securityproject.com.springfocus.domain.User;
import securityproject.com.springfocus.request.UserPostRequest;
import securityproject.com.springfocus.response.UserPostResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = PassWordEncoderMapper.class)
public interface UserMapper {

    @Mapping(target = "password", qualifiedBy = EncodeMapping.class)
    @Mapping(target = "uuid",ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser (UserPostRequest request);

    UserPostResponse toUserPostResponse (User user);
}
