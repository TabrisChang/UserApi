package tw.com.firstbank.fcbcore.fir.service.application.in.user.api;

import lombok.Data;
import tw.com.firstbank.fcbcore.fir.service.application.in.core.ResponseCommand;

@Data
public class DeleteUserResponseCommand implements ResponseCommand {

  private String statusCode;

}
