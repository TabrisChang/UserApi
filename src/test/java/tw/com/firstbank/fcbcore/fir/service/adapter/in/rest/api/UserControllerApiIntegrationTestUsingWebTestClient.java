package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.apache.commons.lang3.time.DateUtils;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.test.web.servlet.ResultActions;
import tw.com.firstbank.fcbcore.fir.service.ServiceApplication;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.mapper.UserControllerMapper;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;
import tw.com.firstbank.fcbcore.fir.service.domain.user.User;
import tw.com.firstbank.fcbcore.fir.service.domain.user.UserId;
import tw.com.firstbank.fcbcore.fir.service.domain.user.type.StatusCode;

@SpringBootTest(classes = ServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerApiIntegrationTestUsingWebTestClient {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserControllerMapper controllerMapper;

  @Autowired
  private UserUseCaseMapper useCaseMapper;

  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  private final static String BASE_URL = "/v1/users";

  private static List<User> entityUserList;

  private static final int ENTITY_USER_SIZE = 10;

  private static final int MAIN_TEST_ENTITY_USER_INDEX = (new Random()).nextInt(0, ENTITY_USER_SIZE);

  @BeforeAll
  public static void setup() {
    entityUserList = Instancio.ofList(User.class).size(ENTITY_USER_SIZE)
        .generate(field(UserId::getBranchCode), gen -> gen.text().pattern("1#d#d"))
        .generate(field(User::getBusinessCategory), gen -> gen.text().pattern("#d#d"))
        .generate(field(User::getVerificationCode), gen -> gen.text().pattern("#d"))
        .generate(field(User::getFirstName), gen -> gen.oneOf("Tom", "Lynn", "Alice", "Eric", "Test"))
        .generate(field(User::getLastName), gen -> gen.oneOf("Chen", "Chang", "Wu", "Lee", "Lin"))
        .generate(field(User::getBirthday), gen -> gen.temporal().date().range(getDiffDate(70), getDiffDate(20)))
        .generate(field(User::getEmail), gen -> gen.text().pattern("#c#c#c#c#c#c@example.com"))
        .generate(field(User::getPhone), gen -> gen.text().pattern("09#d#d#d#d#d#d#d#d")).create();
  }


  @Order(0)
  @ParameterizedTest
  @MethodSource("getUserRange")
  public void 當呼叫新增使用者API時_新增使用者_應新增成功並回傳statusCode為000(int entityIndex) throws Exception {
    //AAA
    //Arrange
    CreateUserRequest createUserRequest = getCreateUserRequest(entityIndex);
    User user = entityUserList.get(entityIndex);
    String requestJson = objectMapper.writeValueAsString(createUserRequest);

    //Act
    ResponseSpec callApiResult = webTestClient.post().uri(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestJson).exchange();

    //Assert
    CreateUserResponse response = callApiResult.expectStatus().isOk().returnResult(CreateUserResponse.class)
        .getResponseBody().blockFirst();

    assertEquals(StatusCode.SUCCESS, response.getStatusCode());
    assertEquals(createUserRequest.getBranchCode(), response.getBranchCode());

    //update local test data
    UserId userId = user.getId();
    userId.setNo(response.getNo());
    user.setId(userId);
    entityUserList.set(entityIndex, user);
  }

  @Order(1)
  @Test
  public void 當呼叫新增使用者API時並給定錯誤的email_新增使用者_應新增失敗並回傳statusCode為606() throws Exception {
    //AAA
    //Arrange
    CreateUserRequest createUserRequest = getCreateUserRequest(MAIN_TEST_ENTITY_USER_INDEX);
    String email = "errorEmail";
    createUserRequest.setEmail(email);
    String requestJson = objectMapper.writeValueAsString(createUserRequest);

    UpdateUserResponse response = new UpdateUserResponse();
    response.setStatusCode(StatusCode.EMAIL_ERROR);

    //Act
    ResponseSpec callApiResult = webTestClient.post().uri(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestJson).exchange();

    //Assert
    callApiResult.expectStatus().isOk().expectBody().json(objectMapper.writeValueAsString(response));

  }

  @Order(2)
  @Test
  public void 當呼叫取得使用者API時_取得使用者_應回傳使用者並statusCode為000() throws Exception {
    //AAA
    //Arrange
    User user = entityUserList.get(MAIN_TEST_ENTITY_USER_INDEX);

    //Act
    ResponseSpec callApiResult = webTestClient.get().uri(getRestUrl(user.getId().getBranchCode(), user.getId().getNo()))
        .exchange();

    //Assert
    GetUserResponse response = callApiResult.expectStatus().isOk().returnResult(GetUserResponse.class).getResponseBody()
        .blockFirst();

    assertThat(response).usingRecursiveComparison().ignoringFields("statusCode", "no", "branchCode")
        .withEqualsForFields(
            (source, target) -> source != null && dateFormat.format(source).equals(dateFormat.format(target)),
            "birthday").isEqualTo(user);
    assertEquals(StatusCode.SUCCESS, response.getStatusCode());
    assertEquals(user.getId().getNo(), response.getNo());
    assertEquals(user.getId().getBranchCode(), response.getBranchCode());
  }

  @Order(3)
  @Test
  public void 當呼叫修改使用者API時_修改使用者_應回傳statusCode為000() throws Exception {
    //AAA
    //Arrange
    UpdateUserRequest updateUserRequest = getUpdateUserRequest(MAIN_TEST_ENTITY_USER_INDEX);
    User user = entityUserList.get(MAIN_TEST_ENTITY_USER_INDEX);
    String branchCode = user.getId().getBranchCode();
    String no = user.getId().getNo();
    UpdateUserResponse response = new UpdateUserResponse();
    response.setStatusCode(StatusCode.SUCCESS);
    String email = "simple@gmail.com";
    String phone = "0953357213";
    updateUserRequest.setEmail(email);
    updateUserRequest.setPhone(phone);
    String requestJson = objectMapper.writeValueAsString(updateUserRequest);

    //Act
    ResponseSpec callApiResult = webTestClient.put().uri(getRestUrl(branchCode, no))
        .contentType(MediaType.APPLICATION_JSON).bodyValue(requestJson).exchange();

    GetUserResponse callApiResponse = webTestClient.get().uri(getRestUrl(branchCode, no)).exchange()
        .returnResult(GetUserResponse.class).getResponseBody().blockFirst();

    //Assert
    callApiResult.expectStatus().isOk().expectBody().json(objectMapper.writeValueAsString(response));
    assertEquals(email, callApiResponse.getEmail());
    assertEquals(phone, callApiResponse.getPhone());

    //update local test data
    user.setEmail(email);
    user.setPhone(phone);
    entityUserList.set(MAIN_TEST_ENTITY_USER_INDEX, user);
  }

  @Order(4)
  @Test
  public void 當呼叫取得所有使用者API時_取得所有使用者_應回傳所有使用者並statusCode為000() throws Exception {
    //AAA
    //Arrange
    List<GetUserResponse> expectedResp = entityUserList.stream().map(useCaseMapper::toUserDto)
        .map(useCaseMapper::toGetUserResponseCommand).map(controllerMapper::toGetUserResponse).toList();

    //Act
    ResponseSpec callApiResult = webTestClient.get().uri(BASE_URL).exchange();

    //Assert
    GetUsersResponse response = callApiResult.expectStatus().isOk().returnResult(GetUsersResponse.class)
        .getResponseBody().blockFirst();

    assertEquals(StatusCode.SUCCESS, response.getStatusCode());
    assertThat(response.users).usingRecursiveComparison().ignoringFields("statusCode").withEqualsForFields(
        (actual, expected) -> actual != null && dateFormat.format(actual).equals(dateFormat.format(expected)),
        "birthday").isEqualTo(expectedResp);
  }

  @Order(5)
  @Test
  public void 使用branchCode為條件呼叫取得使用者API時_取得使用者_應回傳使用者並statusCode為000() throws Exception {
    //AAA
    //Arrange
    User user = entityUserList.get(MAIN_TEST_ENTITY_USER_INDEX);
    String branchCode = user.getId().getBranchCode();
    String url = BASE_URL + "/" + branchCode;
    List<GetUserResponse> expectedResp = entityUserList.stream()
        .filter(entity -> user.getId().getBranchCode().equals(entity.getId().getBranchCode()))
        .map(useCaseMapper::toUserDto).map(useCaseMapper::toGetUserResponseCommand)
        .map(controllerMapper::toGetUserResponse).toList();

    //Act
    ResponseSpec callApiResult = webTestClient.get().uri(url).exchange();

    //Assert
    GetUsersResponse response = callApiResult.expectStatus().isOk().returnResult(GetUsersResponse.class)
        .getResponseBody().blockFirst();

    assertEquals(StatusCode.SUCCESS, response.getStatusCode());
    assertThat(response.users).usingRecursiveComparison().ignoringFields("statusCode").withEqualsForFields(
        (actual, expected) -> actual != null && dateFormat.format(actual).equals(dateFormat.format(expected)),
        "birthday").isEqualTo(expectedResp);
  }

  @Order(6)
  @Test
  public void 當呼叫刪除使用者API時_刪除使用者_應回傳statusCode為000() throws Exception {
    //AAA
    //Arrange
    DeleteUserResponse response = new DeleteUserResponse();
    response.setStatusCode(StatusCode.SUCCESS);
    User user = entityUserList.get(MAIN_TEST_ENTITY_USER_INDEX);
    String branchCode = user.getId().getBranchCode();
    String no = user.getId().getNo();
    String url = getRestUrl(branchCode, no);

    //Act
    ResponseSpec callApiResult = webTestClient.delete().uri(url).exchange();

    GetUserResponse callApiResponse = webTestClient.get().uri(getRestUrl(branchCode, no)).exchange()
        .returnResult(GetUserResponse.class).getResponseBody().blockFirst();

    //Assert
    callApiResult.expectStatus().isOk().expectBody().json(objectMapper.writeValueAsString(response));
    assertEquals(StatusCode.DATA_NOT_FOUND, callApiResponse.getStatusCode());
  }

  private static void assertResponse(String expected, ResultActions resultActions) throws Exception {
    resultActions.andExpect(status().isOk()).andExpect(content().json(expected));
  }

  private static Date getDiffDate(int diffYear) {
    return DateUtils.addYears(new Date(), diffYear * -1);
  }

  private static String getRestUrl(String branchCode, String no) {
    return String.format("%1$s/%2$s/%3$s", BASE_URL, branchCode, no);
  }

  private static IntStream getUserRange() {
    return IntStream.range(0, ENTITY_USER_SIZE);
  }

  private static CreateUserRequest getCreateUserRequest(int entityIndex) {
    User user = entityUserList.get(entityIndex);
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setBranchCode(user.getId().getBranchCode());
    createUserRequest.setBusinessCategory(user.getBusinessCategory());
    createUserRequest.setVerificationCode(user.getVerificationCode());
    createUserRequest.setLastName(user.getLastName());
    createUserRequest.setFirstName(user.getFirstName());
    createUserRequest.setBirthday(user.getBirthday());
    createUserRequest.setEmail(user.getEmail());
    createUserRequest.setPhone(user.getPhone());
    return createUserRequest;
  }

  private static UpdateUserRequest getUpdateUserRequest(int entityIndex) {
    User user = entityUserList.get(entityIndex);
    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setBusinessCategory(user.getBusinessCategory());
    updateUserRequest.setVerificationCode(user.getVerificationCode());
    updateUserRequest.setLastName(user.getLastName());
    updateUserRequest.setFirstName(user.getFirstName());
    updateUserRequest.setBirthday(user.getBirthday());
    updateUserRequest.setEmail(user.getEmail());
    updateUserRequest.setPhone(user.getPhone());
    return updateUserRequest;
  }


}
