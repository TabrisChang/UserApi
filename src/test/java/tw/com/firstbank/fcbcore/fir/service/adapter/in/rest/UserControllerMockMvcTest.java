package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tw.com.firstbank.fcbcore.fir.service.ServiceApplication;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.CreateUserRequest;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.mapper.UserControllerMapper;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserRequestCommand;

@AutoConfigureMockMvc
@SpringBootTest(classes = ServiceApplication.class)
public class UserControllerMockMvcTest {

  @Autowired
  private UserControllerMapper mapper;

  @Test
  public void testMapper() {
    //AAA
    //Arrange
    CreateUserRequest source = Instancio.of(CreateUserRequest.class).create();

    //Act
    CreateUserRequestCommand target = mapper.toCreateUserRequestCommand(source);

    //Assert
    assertNotNull(target);
    assertEquals(source.getBirthday(), target.getBirthday());

  }

}
