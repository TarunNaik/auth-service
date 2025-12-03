package com.example.ecommerce.auth_service.adapter.out.mapper;

import com.example.ecommerce.auth_service.domain.entity.User;
import com.example.ecommerce.auth_service.domain.vo.Email;
import com.example.ecommerce.auth_service.domain.vo.HashedPassword;
import com.example.ecommerce.auth_service.domain.vo.Role;
import com.example.ecommerce.auth_service.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    // MapStruct will map by property names and use the helper methods below to convert types.
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "role", source = "role")
    User toDomain(UserEntity entity);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "role", source = "role")
    UserEntity toEntity(User user);

    // String -> VO helpers (used by MapStruct when converting source primitives to domain VOs)
    default Email mapEmail(String email) { return email == null ? null : new Email(email); }
    default HashedPassword mapPassword(String password) { return password == null ? null : new HashedPassword(password); }
    default Role mapRole(String role) { return role == null ? null : new Role(role); }

    // VO -> String helpers (used by MapStruct when converting domain VOs to entity fields)
    default String mapEmailToString(Email email) { return email == null ? null : email.getValue(); }
    default String mapPasswordToString(HashedPassword password) { return password == null ? null : password.getValue(); }
    default String mapRoleToString(Role role) { return role == null ? null : role.getName(); }

}
