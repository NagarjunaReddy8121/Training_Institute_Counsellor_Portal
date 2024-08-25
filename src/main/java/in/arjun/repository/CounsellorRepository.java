package in.arjun.repository;

import in.arjun.model.entity.Counsellor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CounsellorRepository extends JpaRepository<Counsellor,Integer> {

        Optional<Counsellor> findByEmail(String email);
        Optional<Counsellor> findByEmailAndPassword(String email,String password);
}
