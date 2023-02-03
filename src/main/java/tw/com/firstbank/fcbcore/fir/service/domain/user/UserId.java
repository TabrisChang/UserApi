package tw.com.firstbank.fcbcore.fir.service.domain.user;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserId implements Serializable {

  private String no;
  private String branchCode;

}
