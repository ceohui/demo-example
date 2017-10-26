package cn.zzh.demo.sb.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


	Page<User> findAll(Pageable pageable);

	User findById(long id);

	User findByName(String name);

	List<User> findBySex(int sex);


	@Query(value = "delete from user where id in (?1)",nativeQuery = true)
	@Modifying
	int[] deleteBy(List<Long> ids);

}
