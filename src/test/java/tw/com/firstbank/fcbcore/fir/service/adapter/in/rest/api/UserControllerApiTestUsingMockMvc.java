package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tw.com.firstbank.fcbcore.fir.service.ServiceApplication;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.mapper.UserControllerMapper;
import tw.com.firstbank.fcbcore.fir.service.application.in.core.UseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersByBranchCodeUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserUseCaseApi;

@AutoConfigureMockMvc
@SpringBootTest(classes = ServiceApplication.class)
public class UserControllerApiTestUsingMockMvc {

  @Autowired
  private UserControllerApi userApi;
  @Autowired
  private UserControllerMapper mapper;
  @MockBean
  private CreateUserUseCaseApi createUserApi;
  @MockBean
  private GetUserUseCaseApi getUserApi;
  @MockBean
  private GetUsersUseCaseApi getAllUserApi;
  @MockBean
  private DeleteUserUseCaseApi deleteUserApi;
  @MockBean
  private UpdateUserUseCaseApi updateUserApi;
  @MockBean
  private GetUsersByBranchCodeUseCaseApi getUsersByBranchCodeApi;


  @Test
  public void 當取得所有使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange
    GetUsersResponseCommand responseCommand = Instancio.create(GetUsersResponseCommand.class);
    GetUsersResponse expected = mapper.toGetUsersResponse(responseCommand);
    when(getAllUserApi.execute(any())).thenReturn(responseCommand);

    //Act
    GetUsersResponse actual = userApi.getUsers();

    //Assert
    assertResponse(getAllUserApi, actual, expected);
  }

  @Test
  public void 當新增使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange
    CreateUserResponseCommand response = Instancio.create(CreateUserResponseCommand.class);
    CreateUserResponse expected = mapper.toCreateUserResponse(response);
    when(createUserApi.execute(any())).thenReturn(response);

    //Act
    CreateUserResponse actual = userApi.createUser(any());

    //Assert
    assertResponse(createUserApi, actual, expected);
  }

  @Test
  public void 當取得使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange
    GetUserResponseCommand response = Instancio.create(GetUserResponseCommand.class);
    GetUserResponse expected = mapper.toGetUserResponse(response);
    when(getUserApi.execute(any())).thenReturn(response);

    //Act
    GetUserResponse actual = userApi.getUser(response.getBranchCode(), response.getNo());

    //Assert
    assertResponse(getUserApi, actual, expected);
  }

  @Test
  public void 當刪除使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange
    DeleteUserResponseCommand response = Instancio.create(DeleteUserResponseCommand.class);
    DeleteUserResponse expected = mapper.toDeleteUserResponse(response);
    when(deleteUserApi.execute(any())).thenReturn(response);

    //Act
    DeleteUserResponse actual = userApi.deleteUser(Instancio.create(String.class), Instancio.create(String.class));

    //Assert
    assertResponse(deleteUserApi, actual, expected);
  }

  @Test
  public void 當修改使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange
    UpdateUserResponseCommand response = Instancio.create(UpdateUserResponseCommand.class);
    UpdateUserResponse expected = mapper.toUpdateUserResponse(response);
    when(updateUserApi.execute(any())).thenReturn(response);

    //Act
    UpdateUserResponse actual = userApi.updateUser(Instancio.create(String.class), Instancio.create(String.class), any());

    //Assert
    assertResponse(updateUserApi, actual, expected);
  }

  @Test
  public void 當使用branchCode時取得使用者時應要呼叫UseCase的execute() {
    //AAA
    //Arrange
    GetUsersResponseCommand responseCommand = Instancio.create(GetUsersResponseCommand.class);
    GetUsersResponse expected = mapper.toGetUsersResponse(responseCommand);
    when(getUsersByBranchCodeApi.execute(any())).thenReturn(responseCommand);

    //Act
    GetUsersResponse actual = userApi.getUsersByBranchCode(Instancio.create(String.class));

    //Assert
    assertResponse(getUsersByBranchCodeApi, actual, expected);
  }

  public <T> void assertResponse(UseCaseApi useCaseApi, T actual, T expected) {
    verify(useCaseApi).execute(any());
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

}
