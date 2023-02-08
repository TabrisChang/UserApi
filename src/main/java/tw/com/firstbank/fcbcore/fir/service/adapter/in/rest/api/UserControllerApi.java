package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// http://localhost:8080/UserApi/v1/users
@RequestMapping ("/v1/users")
@Validated
public interface UserControllerApi {
  /**
   * 取得所有使用者資訊
   * @return
   */
  GetUsersResponse getUsers();

  /**
   * 取得某一分行的所有使用者資訊
   * @param branchCode
   * @return
   */
  GetUsersResponse getUsersByBranchCode(final String branchCode);


  /**
   * 使用使用者編號取得使用者資訊
   * @param branchCode 分行代碼
   * @param no 使用者編號
   * @return
   */
  GetUserResponse getUser(final String branchCode, final String no);

  /**
   * 新增使用者
   * @param request 使用者資訊
   * @return
   */
  CreateUserResponse createUser(@Valid @RequestBody final CreateUserRequest request);

  /**
   * 修改使用者資訊
   * @param request 使用者資訊
   * @return
   */
  UpdateUserResponse updateUser(final String branchCode, final String no, @Valid @RequestBody final UpdateUserRequest request);

  /**
   * 使用使用者編號刪除使用者資訊
   * @param no 使用者編號
   * @return
   */
  DeleteUserResponse deleteUser(final String branchCode, final String no);
  

}
