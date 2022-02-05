package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
	Role findByIdRol(Long idRol);
	Collection<Role> findByDescription(String description);
}
