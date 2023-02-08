package tw.com.firstbank.fcbcore.fir.service.application.in.user.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.DeleteUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.UserService;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;
import tw.com.firstbank.fcbcore.fir.service.domain.user.type.StatusCode;

@AllArgsConstructor
@Slf4j
@Service
public class DeleteUserUseCaseImpl implements DeleteUserUseCaseApi {

  private UserUseCaseMapper mapper;
  private UserService userService;

  @Override
  public DeleteUserResponseCommand execute(DeleteUserRequestCommand requestCommand) {
    DeleteUserResponseCommand resp = new DeleteUserResponseCommand();
    resp.setStatusCode(StatusCode.UNKNOWN_ERROR);

    try {
      userService.deleteUser(mapper.toUserDto(requestCommand));
      resp.setStatusCode(StatusCode.SUCCESS);
    } catch (Exception ex) {
      log.error("Delete User Error.", ex);
    }

    return resp;
  }

}
