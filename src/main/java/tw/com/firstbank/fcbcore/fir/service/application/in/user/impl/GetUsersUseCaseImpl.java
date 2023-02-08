package tw.com.firstbank.fcbcore.fir.service.application.in.user.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.domain.user.type.StatusCode;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.UserService;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.GetUsersUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;

@Slf4j
@AllArgsConstructor
@Service
public class GetUsersUseCaseImpl implements GetUsersUseCaseApi {

  private UserUseCaseMapper mapper;
  private UserService userService;

  @Override
  public GetUsersResponseCommand execute(GetUsersRequestCommand requestCommand) {
    GetUsersResponseCommand resp = new GetUsersResponseCommand();
    resp.setStatusCode(StatusCode.UNKNOWN_ERROR);

    try {
      List<GetUserResponseCommand> users = userService.getAllUser().stream().map(mapper::toGetUserResponseCommand)
          .toList();
      resp.setStatusCode(StatusCode.SUCCESS);
      resp.setUsers(users);
    } catch (Exception ex) {
      log.error("Get All User Error.", ex);
    }

    return resp;
  }
}
