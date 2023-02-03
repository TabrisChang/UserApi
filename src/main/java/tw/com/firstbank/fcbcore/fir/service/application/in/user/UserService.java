package tw.com.firstbank.fcbcore.fir.service.application.in.user;

import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fir.service.adapter.out.repository.UserRepository;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserDto;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;

@AllArgsConstructor
@Service
public class UserService {

  private UserUseCaseMapper mapper;
  private UserRepository userRepo;

  public UserDto createUser(UserDto dto) {
    return mapper.toUserDto(userRepo.save(mapper.toUserEntity(dto)));
  }

  public UserDto getUser(UserDto dto) {
    return  mapper.toUserDto(
        userRepo.findById(mapper.toUserId(dto)).orElse(null)
    );
  }

  public void deleteUser(UserDto dto) {
    userRepo.deleteById(mapper.toUserId(dto));
  }

  public List<UserDto> getAllUser() {
    return userRepo.findAll().stream().map(mapper::toUserDto).toList();
  }

  public List<UserDto> getAllUserByBranchCode(UserDto dto) {
    return userRepo.findByIdBranchCode(dto.getBranchCode())
        .stream().map(mapper::toUserDto).toList();
  }

  public void updateUser(UserDto dto) {
    userRepo.save(mapper.toUserEntity(dto));
  }

  public boolean hasUser(UserDto dto) {
    return userRepo.findById(mapper.toUserId(dto)).isPresent();
  }

  public String getUserNo() {
    Random rand = new Random();
    int no = rand.nextInt(0, 100000);
    return String.format("%05d", no);
  }

}
