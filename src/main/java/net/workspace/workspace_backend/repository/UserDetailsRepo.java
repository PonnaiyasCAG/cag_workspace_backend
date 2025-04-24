package net.workspace.workspace_backend.repository;

import net.workspace.workspace_backend.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> {
    UserDetails findByMailId(String mailId);  // Custom query method to find by mailId
}
