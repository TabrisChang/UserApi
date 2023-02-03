package tw.com.firstbank.fcbcore.fir.service.application.in.user.api;

import lombok.Data;
import tw.com.firstbank.fcbcore.fir.service.application.in.core.RequestCommand;

@Data
public class DeleteUserRequestCommand implements RequestCommand {

  private String no;

  private String branchCode;

}
