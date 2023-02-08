package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.CreateUserResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.GetUsersResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.UpdateUserResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.CreateUserRequest;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.DeleteUserResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.GetUserResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.UpdateUserRequest;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserRequestCommand;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserControllerMapper {

  CreateUserRequestCommand toCreateUserRequestCommand(CreateUserRequest source);

  CreateUserResponse toCreateUserResponse(CreateUserResponseCommand source);

  GetUserRequestCommand toGetUserRequestCommand(String branchCode, String no);

  GetUserResponse toGetUserResponse(GetUserResponseCommand source);

  GetUsersResponse toGetUsersResponse(GetUsersResponseCommand source);

  DeleteUserRequestCommand toDeleteUserRequestCommand(String branchCode, String no);
  DeleteUserResponse toDeleteUserResponse(DeleteUserResponseCommand source);

  UpdateUserRequestCommand toUpdateUserRequestCommand(String branchCode, String no, UpdateUserRequest source);

  UpdateUserResponse toUpdateUserResponse(UpdateUserResponseCommand source);

  GetUsersRequestCommand toGetUsersRequestCommand(String branchCode, String no);

}
