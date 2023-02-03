package tw.com.firstbank.fcbcore.fir.service.application.in.user.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.UserService;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.UpdateUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserDto;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;
import tw.com.firstbank.fcbcore.fir.service.domain.user.type.StatusCode;

@Slf4j
@AllArgsConstructor
@Service
public class UpdateUserUseCaseImpl implements UpdateUserUseCaseApi {

  private UserUseCaseMapper mapper;
  private UserService userService;

  @Override
  public UpdateUserResponseCommand execute(UpdateUserRequestCommand requestCommand) {
    UpdateUserResponseCommand resp = new UpdateUserResponseCommand();
    resp.setStatusCode(StatusCode.UNKNOWN_ERROR);

    try {
      UserDto dto = mapper.toUserDto(requestCommand);
      if(userService.hasUser(dto)) {
        userService.updateUser(dto);
        resp.setStatusCode(StatusCode.SUCCESS);
      } else {
        resp.setStatusCode(StatusCode.DATA_NOT_FOUND);
      }
    } catch (Exception ex) {
      log.error("Update User Error.", ex);
    }

    return resp;
  }

}
