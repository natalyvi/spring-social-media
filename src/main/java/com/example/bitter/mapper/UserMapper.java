package com.example.bitter.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class}) // user tweets?
public interface UserMapper {
}
