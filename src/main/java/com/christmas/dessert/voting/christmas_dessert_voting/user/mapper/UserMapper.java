package com.christmas.dessert.voting.christmas_dessert_voting.user.mapper;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    UserDTO userToUserDto(User user);
}
