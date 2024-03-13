package contest.vk.data;

import contest.vk.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findByUsername(Long id);

}
