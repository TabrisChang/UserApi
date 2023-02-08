package tw.com.firstbank.fcbcore.fir.service.application.in.user.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.UserService;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;
import tw.com.firstbank.fcbcore.fir.service.domain.user.type.StatusCode;

@Slf4j
@AllArgsConstructor
@Service
public class GetUserUseCaseImpl implements GetUserUseCaseApi {

  private UserUseCaseMapper mapper;

  private UserService userService;

  @Override
  public GetUserResponseCommand execute(GetUserRequestCommand requestCommand) {
    GetUserResponseCommand resp = new GetUserResponseCommand();
    resp.setStatusCode(StatusCode.UNKNOWN_ERROR);

    try {
      resp = mapper.toGetUserResponseCommand(StatusCode.SUCCESS,
          userService.getUser(mapper.toUserDto(requestCommand)));
    } catch (Exception ex) {
      log.error("Get User Error.", ex);
    }

    return resp;
  }
}
