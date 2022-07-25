/**
 *
 */
package JEstebanC.FastFoodApp.repository;

import JEstebanC.FastFoodApp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user_app where user_roles=2 and name like :name order by name LIMIT 5", nativeQuery = true)
    Collection<User> findByNameStartsWith(@Param("name") String name);

    @Query(value = "select * from user_app where user_roles!=2 and name=:name order by name", nativeQuery = true)
    Collection<User> findByNameAdminStartsWith(@Param("name") String name);

    @Query(value = "select * from user_app where user_roles!=2 order by name",
            countQuery = "select COUNT(*) from user_app where user_roles!=2",
            nativeQuery = true)
    Page<User> listUser(Pageable pageable);

    //this query just search the clients
    @Query(value = "select * from user_app where user_roles=2 order by name",
            countQuery = "select COUNT(*) from user_app where user_roles=2 ",
            nativeQuery = true)
    Page<User> list(Pageable pageable);

    User findByIdUser(Long idUser);

    User findByUsername(String username);

    User findByEmail(String email);

}
