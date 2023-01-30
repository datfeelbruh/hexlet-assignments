package exercise.controller;
import com.querydsl.core.types.Predicate;
import exercise.model.User;
import exercise.model.QUser;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // BEGIN
    @GetMapping(path = "")
    public Iterable<User> getUsers(@QuerydslPredicate(root = User.class) Predicate predicate) {

//        if (firstName == null && lastName == null) {
//            return userRepository.findAll();
//        }
//
//        if (firstName == null) {
//            return userRepository.findAll(
//                    QUser.user.lastName.containsIgnoreCase(lastName)
//            );
//        }
//
//        if (lastName == null) {
//            return userRepository.findAll(
//                    QUser.user.firstName.containsIgnoreCase(firstName)
//            );
//        }
//
//        return userRepository.findAll(
//                QUser.user.firstName.containsIgnoreCase(firstName)
//                .and(
//                        QUser.user.lastName.containsIgnoreCase(lastName)
//                )
//        );
        return userRepository.findAll(predicate);

    }
    // END
}

