package com.sgi.user.mapper;

import com.sgi.dto.AuthenticationResponse;
import com.sgi.dto.UserResponse;
import com.sgi.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse map(User user);

    AuthenticationResponse map(User user, String token);

    default OffsetDateTime map(Instant instant) {
        return instant != null ? instant.atOffset(ZoneOffset.UTC) : null;
    }

}
