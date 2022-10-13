package exercise.controllers;

import io.javalin.http.Context;
import io.javalin.apibuilder.CrudHandler;
import io.ebean.DB;
import java.util.List;

import exercise.domain.User;
import exercise.domain.query.QUser;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.lang3.StringUtils;

public class UserController implements CrudHandler {

    public void getAll(Context ctx) {
        // BEGIN
        User user = new User();
        List<User> users = new QUser()
                .orderBy()
                .id.asc()
                .findList();

        String json = DB.json().toJson(users);
        ctx.json(json);
        // END
    };

    public void getOne(Context ctx, String id) {

        // BEGIN
        long userId = Long.parseLong(id);
        User user = new QUser()
                .id.eq(userId)
                .findOne();

        String json = DB.json().toJson(user);
        ctx.json(json);
        // END
    };

    public void create(Context ctx) {

        // BEGIN
        User user = ctx.bodyValidator(User.class)
                .check(x -> !x.getFirstName().isEmpty(), "Имя должно содержать хотя бы один символ")
                .check(x -> !x.getLastName().isEmpty(), "Фамилия должна содержать хотя бы один символ")
                .check(x -> !x.getEmail().isEmpty(), "Нужно указать адрес электронной почты")
                .check(x -> EmailValidator.getInstance().isValid(x.getEmail()), "Неправильный адрес электронной почты")
                .check(x -> !x.getPassword().isEmpty(), "Нужно указать пароль")
                .check(x -> StringUtils.isNumeric(x.getPassword()), "В пароле допустимы только цифры")
                .check(x -> x.getPassword().length() > 3, "Пароль не может быть короче четырех символов")
                .get();
        user.save();
        // END
    };

    public void update(Context ctx, String id) {
        // BEGIN
        User user = DB.json().toBean(User.class, ctx.body());
        user.setId(id);
        user.update();
        // END
    };

    public void delete(Context ctx, String id) {
        // BEGIN
        long userId = Long.parseLong(id);

        new QUser()
                .id.eq(userId)
                .delete();
        // END
    };
}
