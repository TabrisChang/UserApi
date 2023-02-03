package tw.com.firstbank.fcbcore.fir.service.application.in.user.api;

import lombok.Data;
import tw.com.firstbank.fcbcore.fir.service.application.in.core.ResponseCommand;

@Data
public class CreateUserResponseCommand implements ResponseCommand {

  private String statusCode;
  private String no;
  private String branchCode;

}
