package tw.com.firstbank.fcbcore.fir.service.application.in.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tw.com.firstbank.fcbcore.fir.service.ServiceApplication;
import tw.com.firstbank.fcbcore.fir.service.adapter.out.repository.UserRepository;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserDto;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;
import tw.com.firstbank.fcbcore.fir.service.domain.user.User;

@AutoConfigureMockMvc
@SpringBootTest(classes = ServiceApplication.class)
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserUseCaseMapper mapper;

  @MockBean
  private UserRepository userRepo;

  @Test
  public void testCreateUser() {
    //AAA
    //Arrange
    UserDto userDto = Instancio.create(UserDto.class);
    User user = mapper.toUserEntity(userDto);
    when(userRepo.save(any())).thenReturn(user);

    //Act
    UserDto resultDto = userService.createUser(userDto);

    //Assert
    verify(userRepo).save(any());
    assertThat(resultDto)
        .usingRecursiveComparison()
        .isEqualTo(userDto);

  }

  @Test
  public void testGetAllUser() {
    //AAA
    //Arrange
    List<User> userList = Instancio.of(User.class)
        .generate(field("email"), gen -> gen.text().pattern("#c#c#c#c#c@gmail.com"))
        .generate(field("verificationCode"), gen -> gen.oneOf("0","1","2","3","4"))
        .stream().limit(10).toList();

    List<UserDto> userDtoList = userList.stream().map(mapper::toUserDto).collect(Collectors.toList());
    when(userRepo.findAll()).thenReturn(userList);

    //Act
    List<UserDto> resultUserDtoList = userService.getAllUser();

    //Assert
    verify(userRepo).findAll();
    assertThat(resultUserDtoList)
        .usingRecursiveComparison()
        .isEqualTo(userDtoList);
  }

}
