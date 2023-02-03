package tw.com.firstbank.fcbcore.fir.service.adapter.out.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.firstbank.fcbcore.fir.service.domain.user.User;
import tw.com.firstbank.fcbcore.fir.service.domain.user.UserId;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

  List<User> findByIdBranchCode(String branchCode);

}
