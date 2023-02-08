package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.impl.UserControllerImpl;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.mapper.UserControllerMapper;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersByBranchCodeUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserUseCaseApi;

@ExtendWith({MockitoExtension.class})
public class UserControllerApiTest {

  private UserControllerApi userApi;

  @Mock
  private UserControllerMapper mapper;
  @Mock
  private CreateUserUseCaseApi createUserUseCaseApi;
  @Mock
  private GetUserUseCaseApi getUserUseCaseApi;
  @Mock
  private GetUsersUseCaseApi getUsersUseCaseApi;
  @Mock
  private DeleteUserUseCaseApi deleteUserUseCaseApi;
  @Mock
  private UpdateUserUseCaseApi updateUserUseCaseApi;
  @Mock
  private GetUsersByBranchCodeUseCaseApi getUsersByBranchCodeUseCaseApi;

  @BeforeEach
  public void setUp() {
    userApi = new UserControllerImpl(mapper, createUserUseCaseApi, getUserUseCaseApi,
        getUsersUseCaseApi, deleteUserUseCaseApi, updateUserUseCaseApi,
        getUsersByBranchCodeUseCaseApi);
  }

  @Test
  public void 當取得所有使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange

    //Act
    userApi.getUsers();
    //Assert
    verify(getUsersUseCaseApi).execute(any());
  }

  @Test
  public void 當新增使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange

    //Act
    userApi.createUser(any());

    //Assert
    verify(createUserUseCaseApi).execute(any());
  }

  @Test
  public void 當取得使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange

    //Act
    userApi.getUser(any(), any());

    //Assert
    verify(getUserUseCaseApi).execute(any());
  }

  @Test
  public void 當刪除使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange

    //Act
    userApi.deleteUser(any(), any());

    //Assert
    verify(deleteUserUseCaseApi).execute(any());
  }

  @Test
  public void 當修改使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange

    //Act
    userApi.updateUser(any(), any(), any());

    //Assert
    verify(updateUserUseCaseApi).execute(any());
  }

  @Test
  public void 當使用branchCode時取得使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange
    String branchCode = "111";

    //Act
    userApi.getUsersByBranchCode(branchCode);

    //Assert
    verify(getUsersByBranchCodeUseCaseApi).execute(any());
  }



}
