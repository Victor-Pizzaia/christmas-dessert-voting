package com.christmas.dessert.voting.christmas_dessert_voting.user.mapper;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "favoriteSweets", source = "user.favoriteSweets")
    UserDTO userToUserDto(User user);
}
