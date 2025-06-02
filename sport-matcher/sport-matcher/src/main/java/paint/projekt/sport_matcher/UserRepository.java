package paint.projekt.sport_matcher;

import org.springframework.data.repository.CrudRepository;

import paint.projekt.sport_matcher.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

}