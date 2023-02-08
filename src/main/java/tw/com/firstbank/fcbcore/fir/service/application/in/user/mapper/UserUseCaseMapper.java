package tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Mappings;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.domain.user.User;
import tw.com.firstbank.fcbcore.fir.service.domain.user.UserId;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserUseCaseMapper {

  @Mappings({
      @Mapping(source = "no", target = "no"),
      @Mapping(source = "source.branchCode", target = "branchCode"),
      @Mapping(source = "source.businessCategory", target = "businessCategory"),
      @Mapping(source = "source.verificationCode", target = "verificationCode"),
      @Mapping(source = "source.firstName", target = "firstName"),
      @Mapping(source = "source.lastName", target = "lastName"),
      @Mapping(source = "source.birthday", target = "birthday"),
      @Mapping(source = "source.email", target = "email"),
      @Mapping(source = "source.phone", target = "phone")
  })
  UserDto toUserDto(String no, CreateUserRequestCommand source);

  CreateUserResponseCommand toCreateUserResponseCommand(String statusCode,
      String no, String branchCode);

  @Mappings({
      @Mapping(source = "dto.no", target = "id.no"),
      @Mapping(source = "dto.branchCode", target = "id.branchCode")
  })
  User toUserEntity(UserDto dto);

  @Mappings({
      @Mapping(source = "id.no", target = "no"),
      @Mapping(source = "id.branchCode", target = "branchCode")
  })
  UserDto toUserDto(User entity);

  UserDto toUserDto(GetUserRequestCommand source);

  GetUserResponseCommand toGetUserResponseCommand(String statusCode, UserDto dto);

  GetUserResponseCommand toGetUserResponseCommand(UserDto dto);

  UserId toUserId(UserDto dto);

  UserDto toUserDto(DeleteUserRequestCommand source);

  UserDto toUserDto(UpdateUserRequestCommand source);

  UserDto toUserDto(GetUsersRequestCommand source);

}
