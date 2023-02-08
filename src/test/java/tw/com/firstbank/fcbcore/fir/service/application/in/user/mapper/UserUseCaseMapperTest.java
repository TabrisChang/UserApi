package tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tw.com.firstbank.fcbcore.fir.service.ServiceApplication;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.domain.user.UserId;

@AutoConfigureMockMvc
@SpringBootTest(classes = ServiceApplication.class)
public class UserUseCaseMapperTest {

  @Autowired
  private UserUseCaseMapper mapper;

  @Test
  public void testNoAndCreateUserRequestCommandToUserDto() {
    //AAA
    //Arrange
    String no = Instancio.of(String.class).create();
    CreateUserRequestCommand requestCommand = Instancio.of(CreateUserRequestCommand.class).create();

    //Act
    UserDto userDto = mapper.toUserDto(no, requestCommand);

    //Assert
    assertNotNull(userDto);
    assertEquals(no, userDto.getNo());
    assertEquals(requestCommand.getPhone(), userDto.getPhone());
    assertEquals(requestCommand.getBranchCode(), userDto.getBranchCode());
    assertEquals(requestCommand.getLastName(), userDto.getLastName());
    assertEquals(requestCommand.getFirstName(), userDto.getFirstName());
    assertEquals(requestCommand.getEmail(), userDto.getEmail());
    assertEquals(requestCommand.getVerificationCode(), userDto.getVerificationCode());
    assertEquals(requestCommand.getBirthday(), userDto.getBirthday());
    assertEquals(requestCommand.getBusinessCategory(), userDto.getBusinessCategory());

    // Assert
    assertThat(userDto)
        .usingRecursiveComparison()
        .ignoringFields("no")
        .isEqualTo(requestCommand);
  }

  @Test
  public void testUserDtoToUserId() {
    //AAA
    //Arrange
    UserDto userDto = Instancio.create(UserDto.class);

    //Act
    UserId userId = mapper.toUserId(userDto);

    //Assert
    assertThat(userId)
        .usingRecursiveComparison()
        .isEqualTo(userDto);

  }

}
