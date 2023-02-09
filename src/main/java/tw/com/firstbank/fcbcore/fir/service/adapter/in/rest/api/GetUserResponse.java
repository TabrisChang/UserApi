package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import java.util.TimeZone;
import lombok.Data;
import org.apache.commons.lang3.time.TimeZones;

@Data
public class GetUserResponse {

  @JsonInclude(Include.NON_NULL)
  private String statusCode;
  private String no;
  private String branchCode;
  private String businessCategory;
  private String verificationCode;
  private String firstName;
  private String lastName;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Taipei")
  private Date birthday;
  private String email;
  private String phone;

}
