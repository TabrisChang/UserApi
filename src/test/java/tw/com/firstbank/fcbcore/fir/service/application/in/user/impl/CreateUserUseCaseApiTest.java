package tw.com.firstbank.fcbcore.fir.service.application.in.user.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.UserService;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserDto;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;
import tw.com.firstbank.fcbcore.fir.service.domain.user.type.StatusCode;

@ExtendWith({MockitoExtension.class})
public class CreateUserUseCaseApiTest {

  private CreateUserUseCaseApi createUserApi;
  @Mock
  private UserUseCaseMapper mapper;
  @Mock
  private UserService userService;

  private CreateUserRequestCommand requestCommand;

  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;

  @BeforeEach
  public void setUp() throws Exception {
    createUserApi = new CreateUserUseCaseImpl(mapper, userService);
    requestCommand = new CreateUserRequestCommand();
    requestCommand.setBranchCode("123");
    requestCommand.setBusinessCategory("45");
    requestCommand.setVerificationCode("1");
    requestCommand.setFirstName("Test");
    requestCommand.setLastName("Chang");
    requestCommand.setBirthday(dateFormat.parse("1990-02-23"));
    requestCommand.setEmail("test.chang@gmai.com");
    requestCommand.setPhone("0912345678");
  }

  @Test
  public void 當branchCode為123時_新增使用者_應新增成功且statusCode為000() {
    //AAA
    //Arrange
    initCorrectCreateUserProcess();

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.SUCCESS, resp.getStatusCode());
  }

  @Test
  public void 當branchCode為404時_新增使用者_應新增失敗且statusCode為600() {
    //AAA
    //Arrange
    String branchCode = "404";
    requestCommand.setBranchCode(branchCode);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.BRANCH_CODE_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當branchCode不為數字時_新增使用者_應新增失敗且statusCode為600() {
    //AAA
    //Arrange
    String branchCode = "abc";
    requestCommand.setBranchCode(branchCode);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.BRANCH_CODE_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當businessCategory為45時_新增使用者_應新增成功且statusCode為000() {
    //AAA
    //Arrange
    initCorrectCreateUserProcess();

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.SUCCESS, resp.getStatusCode());
  }

  @Test
  public void 當businessCategory不為數字_新增使用者_應新增失敗且statusCode為601() {
    //AAA
    //Arrange
    String businessCategory = "TT";
    requestCommand.setBusinessCategory(businessCategory);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.BUSINESS_CATE_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當verificationCode為1時_新增使用者_應新增成功且statusCode為000() {
    //AAA
    //Arrange
    initCorrectCreateUserProcess();

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.SUCCESS, resp.getStatusCode());
  }

  @Test
  public void 當verificationCode不為數字_新增使用者_應新增失敗且statusCode為602() {
    //AAA
    //Arrange
    String verificationCode = "Z";
    requestCommand.setVerificationCode(verificationCode);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.VERIFICATION_CODE_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當firstName為Test時_新增使用者_應新增成功且statusCode為000() {
    //AAA
    //Arrange
    initCorrectCreateUserProcess();

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.SUCCESS, resp.getStatusCode());
  }

  @Test
  public void 當firstName為空時_新增使用者_應新增失敗且statusCode為603() {
    //AAA
    //Arrange
    String firstName = "";
    requestCommand.setFirstName(firstName);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.FIRST_NAME_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當firstName長度超過60時_新增使用者_應新增失敗且statusCode為603() {
    //AAA
    //Arrange
    String firstName = "12345678901234567890123456789012345678901234567890123456789012345678901234567890";
    requestCommand.setFirstName(firstName);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.FIRST_NAME_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當lastName為Chang時_新增使用者_應新增成功且statusCode為000() {
    //AAA
    //Arrange
    initCorrectCreateUserProcess();

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.SUCCESS, resp.getStatusCode());
  }

  @Test
  public void 當lastName為空時_新增使用者_應新增失敗且statusCode為604() {
    //AAA
    //Arrange
    String lastName = "";
    requestCommand.setLastName(lastName);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.LAST_NAME_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當lastName長度超過60時_新增使用者_應新增失敗且statusCode為604() {
    //AAA
    //Arrange
    String lastName = "12345678901234567890123456789012345678901234567890123456789012345678901234567890";
    requestCommand.setLastName(lastName);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.LAST_NAME_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當birthday超過18歲時_新增使用者_應新增成功且statusCode為000() {
    //AAA
    //Arrange
    initCorrectCreateUserProcess();

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.SUCCESS, resp.getStatusCode());
  }

  @Test
  public void 當birthday未滿18歲時_新增使用者_應新增失敗且statusCode為605() {
    //AAA
    //Arrange
    Date now = DateUtils.addYears(new Date(), -1);
    requestCommand.setBirthday(now);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.BIRTHDAY_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當birthday剛好18歲時_新增使用者_應新增成功且statusCode為000() throws ParseException {
    //AAA
    //Arrange
    initCorrectCreateUserProcess();
    Date birthday = DateUtils.addYears(new Date(), -18 );
    requestCommand.setBirthday(birthday);

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.SUCCESS, resp.getStatusCode());
  }

  @Test
  public void 當email格式正確時_新增使用者_應新增成功且statusCode為000() {
    //AAA
    //Arrange
    initCorrectCreateUserProcess();

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.SUCCESS, resp.getStatusCode());
  }

  @Test
  public void 當email格式不正確時_新增使用者_應新增失敗且statusCode為606() {
    //AAA
    //Arrange
    requestCommand.setEmail("dds@fdf@vv.com");

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.EMAIL_ERROR, resp.getStatusCode());
  }


  @Test
  public void 當phone格式正確時_新增使用者_應新增成功且statusCode為000() {
    //AAA
    //Arrange
    initCorrectCreateUserProcess();

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.SUCCESS, resp.getStatusCode());
  }

  @Test
  public void 當phone格式不正確時_新增使用者應新增失敗且statusCode為607() {
    //AAA
    //Arrange
    requestCommand.setPhone("0712345435");

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.PHONE_ERROR, resp.getStatusCode());
  }

  @Test
  public void 當phone不為數字時_新增使用者_應新增失敗且statusCode為607() {
    //AAA
    //Arrange
    requestCommand.setPhone("0912111a11");

    //Act
    CreateUserResponseCommand resp =  createUserApi.execute(requestCommand);

    //Assert
    assertEquals(StatusCode.PHONE_ERROR, resp.getStatusCode());
  }

  private void initCorrectCreateUserProcess() {
    String no = "12345";
    String branchCode = "123";

    when(userService.getUserNo()).thenReturn(no);
    when(userService.createUser(any())).thenReturn(new UserDto());

    CreateUserResponseCommand returnCommand = new CreateUserResponseCommand();
    returnCommand.setStatusCode(StatusCode.SUCCESS);
    returnCommand.setNo(no);
    returnCommand.setBranchCode(branchCode);
    when(mapper.toCreateUserResponseCommand(any(), any(),
        any())).thenReturn(returnCommand);
  }
}
