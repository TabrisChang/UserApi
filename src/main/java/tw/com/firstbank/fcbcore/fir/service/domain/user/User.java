package tw.com.firstbank.fcbcore.fir.service.domain.user;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "FCBUser")
public class User implements Serializable {

  @EmbeddedId
  private UserId id;
  private String businessCategory;
  private String verificationCode;
  private String firstName;
  private String lastName;
  private Date birthday;
  private String email;
  private String phone;

}
