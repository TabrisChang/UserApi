package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tw.com.firstbank.fcbcore.fir.service.ServiceApplication;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.mapper.UserControllerMapper;
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
public class UserControllerApiTestUsingMockMvcAndCallUrlApi {

  @Autowired
  private MockMvc mockMvc;
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

  @Autowired
  private ObjectMapper objectMapper;

  private final static String BASE_URL = "/v1/users";

  private final static String REST_URL = String.format("%1$s/%2$s/%3$s", BASE_URL, Instancio.create(String.class),
      Instancio.create(String.class));

  @Test
  public void 當取得所有使用者時應要呼叫UseCase的execute() throws Exception {
    //AAA
    //Arrange
    GetUsersResponseCommand responseCommand = Instancio.create(GetUsersResponseCommand.class);
    when(getAllUserApi.execute(any())).thenReturn(responseCommand);
    String expected = objectMapper.writeValueAsString(mapper.toGetUsersResponse(responseCommand));

    //Act
    ResultActions resultActions = mockMvc.perform(get(BASE_URL));

    //Assert
    assertResponse(expected, resultActions);

  }

  @Test
  public void 當新增使用者時應要呼叫UseCase的execute() throws Exception {
    //AAA
    //Arrange
    CreateUserRequest request = Instancio.create(CreateUserRequest.class);
    String requestJson = objectMapper.writeValueAsString(request);
    CreateUserResponseCommand response = Instancio.create(CreateUserResponseCommand.class);
    response.setBranchCode(request.getBranchCode());
    when(createUserApi.execute(any())).thenReturn(response);
    String expected = objectMapper.writeValueAsString(mapper.toCreateUserResponse(response));

    //Act
    ResultActions resultActions = mockMvc.perform(
        post(BASE_URL).contentType(APPLICATION_JSON_VALUE).content(requestJson));

    //Assert
    assertResponse(expected, resultActions);
  }


  @Test
  public void 當取得使用者時應要呼叫UseCase的execute() throws Exception {
    //AAA
    //Arrange
    GetUserResponseCommand response = Instancio.create(GetUserResponseCommand.class);
    when(getUserApi.execute(any())).thenReturn(response);
    String expected = objectMapper.writeValueAsString(mapper.toGetUserResponse(response));

    //Act
    ResultActions resultActions = mockMvc.perform(get(REST_URL));

    //Assert
    assertResponse(expected, resultActions);
  }

  @Test
  public void 當刪除使用者時應要呼叫UseCase的execute() throws Exception {
    //AAA
    //Arrange
    DeleteUserResponseCommand response = Instancio.create(DeleteUserResponseCommand.class);
    when(deleteUserApi.execute(any())).thenReturn(response);
    String expected = objectMapper.writeValueAsString(mapper.toDeleteUserResponse(response));

    //Act
    ResultActions resultActions = mockMvc.perform(delete(REST_URL));

    //Assert
    assertResponse(expected, resultActions);
  }

  @Test
  public void 當修改使用者時應要呼叫UseCase的execute() throws Exception {
    //AAA
    //Arrange
    UpdateUserRequest request = Instancio.create(UpdateUserRequest.class);
    String requestJson = objectMapper.writeValueAsString(request);
    UpdateUserResponseCommand response = Instancio.create(UpdateUserResponseCommand.class);
    when(updateUserApi.execute(any())).thenReturn(response);
    String expected = objectMapper.writeValueAsString(mapper.toUpdateUserResponse(response));

    //Act
    ResultActions resultActions = mockMvc.perform(
        put(REST_URL).contentType(APPLICATION_JSON_VALUE).content(requestJson));

    //Assert
    assertResponse(expected, resultActions);
  }

  @Test
  public void 當使用branchCode時取得使用者時應要呼叫UseCase的execute() throws Exception {
    //AAA
    //Arrange
    GetUsersResponseCommand responseCommand = Instancio.create(GetUsersResponseCommand.class);
    when(getUsersByBranchCodeApi.execute(any())).thenReturn(responseCommand);
    String expected = objectMapper.writeValueAsString(mapper.toGetUsersResponse(responseCommand));
    String url = BASE_URL + "/" + Instancio.create(String.class);

    //Act
    ResultActions resultActions = mockMvc.perform(get(url));

    //Assert
    assertResponse(expected, resultActions);
  }

  private static void assertResponse(String expected, ResultActions resultActions) throws Exception {
    resultActions.andExpect(status().isOk()).andExpect(content().json(expected));
  }

}
