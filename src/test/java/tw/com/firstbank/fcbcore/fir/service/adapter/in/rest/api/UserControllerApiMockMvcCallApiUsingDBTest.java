package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.time.DateUtils;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tw.com.firstbank.fcbcore.fir.service.ServiceApplication;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.mapper.UserControllerMapper;
import tw.com.firstbank.fcbcore.fir.service.adapter.out.repository.UserRepository;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;
import tw.com.firstbank.fcbcore.fir.service.domain.user.User;
import tw.com.firstbank.fcbcore.fir.service.domain.user.UserId;
import tw.com.firstbank.fcbcore.fir.service.domain.user.type.StatusCode;

@AutoConfigureMockMvc
@SpringBootTest(classes = ServiceApplication.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerApiMockMvcCallApiUsingDBTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserControllerMapper controllerMapper;

  @Autowired
  private UserUseCaseMapper useCaseMapper;

  @Autowired
  private ObjectMapper objectMapper;

  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;

  private final static String BASE_URL = "/v1/users";

  private static List<User> entityUserList;

  private static List<User> entityUserSameBranchCodeList;

  private static User entityUser;

  private static CreateUserRequest createUserRequest;

  private static UpdateUserRequest updateUserRequest;


  @BeforeAll
  public static void setup() {
    entityUserList = Instancio.ofList(User.class).size(10)
        .generate(field(UserId::getNo), gen -> gen.text().pattern("#d#d#d#d#d"))
        .generate(field(UserId::getBranchCode), gen -> gen.text().pattern("1#d#d"))
        .generate(field(User::getBusinessCategory), gen -> gen.text().pattern("#d#d"))
        .generate(field(User::getVerificationCode), gen -> gen.text().pattern("#d"))
        .generate(field(User::getFirstName), gen -> gen.oneOf("Tom", "Lynn", "Alice", "Eric", "Test"))
        .generate(field(User::getLastName), gen -> gen.oneOf("Chen", "Chang", "Wu", "Lee", "Lin"))
        .generate(field(User::getBirthday), gen -> gen.temporal().date().range(getDiffDate(70), getDiffDate(20)))
        .generate(field(User::getEmail), gen -> gen.text().pattern("#c#c#c#c#c#c@example.com"))
        .generate(field(User::getPhone), gen -> gen.text().pattern("09#d#d#d#d#d#d#d#d")).create();

    entityUser = entityUserList.get(0);

    entityUserSameBranchCodeList = entityUserList.stream().map(user -> {
      UserId userId = user.getId();
      userId.setBranchCode(entityUser.getId().getBranchCode());
      user.setId(userId);
      return user;
    }).toList();

    createUserRequest = new CreateUserRequest();
    createUserRequest.setBranchCode(entityUser.getId().getBranchCode());
    createUserRequest.setBusinessCategory(entityUser.getBusinessCategory());
    createUserRequest.setVerificationCode(entityUser.getVerificationCode());
    createUserRequest.setLastName(entityUser.getLastName());
    createUserRequest.setFirstName(entityUser.getFirstName());
    createUserRequest.setBirthday(entityUser.getBirthday());
    createUserRequest.setEmail(entityUser.getEmail());
    createUserRequest.setPhone(entityUser.getPhone());

    updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setBusinessCategory(entityUser.getBusinessCategory());
    updateUserRequest.setVerificationCode(entityUser.getVerificationCode());
    updateUserRequest.setLastName(entityUser.getLastName());
    updateUserRequest.setFirstName(entityUser.getFirstName());
    updateUserRequest.setBirthday(entityUser.getBirthday());
    updateUserRequest.setEmail(entityUser.getEmail());
    updateUserRequest.setPhone(entityUser.getPhone());
  }

  @Order(0)
  @Test
  public void 當呼叫新增使用者API時_新增使用者_應新增成功並回傳statusCode為000() throws Exception {
    //AAA
    //Arrange
    String requestJson = objectMapper.writeValueAsString(createUserRequest);

    //Act
    ResultActions resultActions = mockMvc.perform(
        post(BASE_URL).contentType(APPLICATION_JSON_VALUE).content(requestJson));

    //Assert
    resultActions.andExpect(status().isOk());
    String responseJsonStr = resultActions.andReturn().getResponse().getContentAsString();
    CreateUserResponse response = objectMapper.readValue(responseJsonStr, CreateUserResponse.class);
    assertEquals(StatusCode.SUCCESS, response.getStatusCode());
    assertEquals(createUserRequest.getBranchCode(), response.getBranchCode());
    UserId userId = entityUser.getId();
    userId.setNo(response.getNo());
    entityUser.setId(userId);
  }

  @Order(1)
  @Test
  public void 當呼叫取得使用者API時_取得使用者_應回傳使用者並statusCode為000() throws Exception {
    //AAA
    //Arrange
    //Act
    ResultActions resultActions = mockMvc.perform(
        get(getRestUrl(entityUser.getId().getBranchCode(), entityUser.getId().getNo())));

    //Assert
    resultActions.andExpect(status().isOk());
    String responseJsonStr = resultActions.andReturn().getResponse().getContentAsString();
    GetUserResponse response = objectMapper.readValue(responseJsonStr, GetUserResponse.class);
    assertThat(response)
        .usingRecursiveComparison()
        .ignoringFields("statusCode","no", "branchCode", "birthday")
        .isEqualTo(entityUser);
    assertEquals(StatusCode.SUCCESS, response.getStatusCode());
    assertEquals(entityUser.getId().getNo(), response.getNo());
    assertEquals(entityUser.getId().getBranchCode(), response.getBranchCode());
    assertEquals(dateFormat.format(entityUser.getBirthday()), dateFormat.format(response.getBirthday()));
  }

  @Order(2)
  @Test
  public void 當呼叫修改使用者API時_修改使用者_應回傳statusCode為000() throws Exception {
    //AAA
    //Arrange
    UpdateUserResponse response = new UpdateUserResponse();
    response.setStatusCode(StatusCode.SUCCESS);
    String email = "simple@gmail.com";
    String phone = "0953357213";
    updateUserRequest.setEmail(email);
    updateUserRequest.setPhone(phone);
    String requestJson = objectMapper.writeValueAsString(updateUserRequest);

    //Act
    ResultActions resultActions = mockMvc.perform(
        put(getRestUrl(entityUser.getId().getBranchCode(), entityUser.getId().getNo())).contentType(
            APPLICATION_JSON_VALUE).content(requestJson));

    //Assert
    resultActions.andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

//  @Test
//  public void 當呼叫取得所有使用者API時_取得所有使用者_應回傳所有使用者並statusCode為000() throws Exception {
//    //AAA
//    //Arrange
//    when(userRepo.findAll()).thenReturn(entityUserList);
//    List<GetUserResponse> users = entityUserList.stream().map(useCaseMapper::toUserDto)
//        .map(useCaseMapper::toGetUserResponseCommand).map(controllerMapper::toGetUserResponse).toList();
//    GetUsersResponse response = new GetUsersResponse();
//    response.setStatusCode(StatusCode.SUCCESS);
//    response.setUsers(users);
//    String expected = objectMapper.writeValueAsString(response);
//
//    //Act
//    ResultActions resultActions = mockMvc.perform(get(BASE_URL));
//
//    //Assert
//    assertResponse(expected, resultActions);
//
//  }
//
//
//
//  @Test
//  public void 當呼叫新增使用者API時並給定錯誤的email_新增使用者_應新增失敗並回傳statusCode為606() throws Exception {
//    //AAA
//    //Arrange
//    String no = "12345";
//    createUserRequest.setEmail("error_email");
//    String requestJson = objectMapper.writeValueAsString(createUserRequest);
//    User entity = useCaseMapper.toUserEntity(
//        useCaseMapper.toUserDto(no, controllerMapper.toCreateUserRequestCommand(createUserRequest)));
//
//    when(userRepo.save(any())).thenReturn(entity);
//
//    CreateUserResponse response = new CreateUserResponse();
//    response.setStatusCode(StatusCode.EMAIL_ERROR);
//    String expected = objectMapper.writeValueAsString(response);
//
//    //Act
//    ResultActions resultActions = mockMvc.perform(
//        post(BASE_URL).contentType(APPLICATION_JSON_VALUE).content(requestJson));
//
//    //Assert
//    assertResponse(expected, resultActions);
//
//  }
//

//
//  @Test
//  public void 當呼叫刪除使用者API時_刪除使用者_應回傳statusCode為000() throws Exception {
//    //AAA
//    //Arrange
//    DeleteUserResponse response = new DeleteUserResponse();
//    response.setStatusCode(StatusCode.SUCCESS);
//    String expected = objectMapper.writeValueAsString(response);
//
//    //Act
//    ResultActions resultActions = mockMvc.perform(
//        delete(getRestUrl(entityUser.getId().getBranchCode(), entityUser.getId().getNo())));
//
//    //Assert
//    assertResponse(expected, resultActions);
//  }
//

//
//  @Test
//  public void 使用branchCode為條件呼叫取得使用者API時_取得使用者_應回傳使用者並statusCode為000() throws Exception {
//    //AAA
//    //Arrange
//    when(userRepo.findByIdBranchCode(any())).thenReturn(entityUserSameBranchCodeList);
//    String branchCode = entityUser.getId().getBranchCode();
//    GetUsersResponse response = Instancio.create(GetUsersResponse.class);
//    List<GetUserResponse> users = entityUserSameBranchCodeList.stream().map(useCaseMapper::toUserDto)
//        .map(useCaseMapper::toGetUserResponseCommand).map(controllerMapper::toGetUserResponse).toList();
//    response.setStatusCode(StatusCode.SUCCESS);
//    response.setUsers(users);
//    String expected = objectMapper.writeValueAsString(response);
//    String url = BASE_URL + "/" + branchCode;
//
//    //Act
//    ResultActions resultActions = mockMvc.perform(get(url));
//
//    //Assert
//    assertResponse(expected, resultActions);
//  }

  private static void assertResponse(String expected, ResultActions resultActions) throws Exception {
    resultActions.andExpect(status().isOk()).andExpect(content().json(expected));
  }

  private static Date getDiffDate(int diffYear) {
    return DateUtils.addYears(new Date(), diffYear * -1);
  }

  private static String getRestUrl(String branchCode, String no) {
    return String.format("%1$s/%2$s/%3$s", BASE_URL, branchCode, no);
  }


}
