package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.impl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.CreateUserResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.GetUsersResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.UpdateUserResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.UserControllerApi;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.CreateUserRequest;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.DeleteUserResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.GetUserResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.UpdateUserRequest;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.mapper.UserControllerMapper;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersByBranchCodeUseCaseApi;

@AllArgsConstructor
@RestController
public class UserControllerImpl implements UserControllerApi {

  private UserControllerMapper mapper;
  private CreateUserUseCaseApi createUserUseCaseApi;
  private GetUserUseCaseApi getUserUseCaseApi;
  private GetUsersUseCaseApi getUsersUseCaseApi;
  private DeleteUserUseCaseApi deleteUserUseCaseApi;
  private UpdateUserUseCaseApi updateUserUseCaseApi;
  private GetUsersByBranchCodeUseCaseApi getUsersByBranchCodeUseCaseApi;

  @GetMapping("")
  @Override
  public GetUsersResponse getUsers() {
    return mapper.toGetUsersResponse(getUsersUseCaseApi.execute(null));
  }

  @GetMapping("/{branchCode}")
  @Override
  public GetUsersResponse getUsersByBranchCode(@PathVariable String branchCode) {
    return mapper.toGetUsersResponse(getUsersByBranchCodeUseCaseApi.execute(mapper.toGetUsersRequestCommand(branchCode, null)));
  }

  // http://localhost:8080/UserApi/v1/users/{branchCode}/{no}
  @GetMapping("/{branchCode}/{no}")
  @Override
  public GetUserResponse getUser(@PathVariable String branchCode, @PathVariable String no) {
    return mapper.toGetUserResponse(
        getUserUseCaseApi.execute(mapper.toGetUserRequestCommand(branchCode, no))
    );
  }


  // http://localhost:8080/UserApi/v1/users
  @PostMapping("")
  @Override
  public CreateUserResponse createUser(CreateUserRequest request) {
    return mapper.toCreateUserResponse(
        createUserUseCaseApi.execute(mapper.toCreateUserRequestCommand(request))
    );
  }

  @PutMapping("/{branchCode}/{no}")
  @Override
  public UpdateUserResponse updateUser(@PathVariable String branchCode, @PathVariable String no, UpdateUserRequest request) {
    return mapper.toUpdateUserResponse(
        updateUserUseCaseApi.execute(mapper.toUpdateUserRequestCommand(branchCode, no, request))
    );
  }

  @DeleteMapping("/{branchCode}/{no}")
  @Override
  public DeleteUserResponse deleteUser(@PathVariable String branchCode, @PathVariable String no) {
    return mapper.toDeleteUserResponse(
        deleteUserUseCaseApi.execute(mapper.toDeleteUserRequestCommand(branchCode, no))
    );
  }
}
