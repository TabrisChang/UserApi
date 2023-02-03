package tw.com.firstbank.fcbcore.fir.service.application.in.user.api;

import java.util.List;
import lombok.Data;
import tw.com.firstbank.fcbcore.fir.service.application.in.core.ResponseCommand;

@Data
public class GetUsersResponseCommand implements ResponseCommand {

  private String statusCode;

  private List<GetUserResponseCommand> users;

}
