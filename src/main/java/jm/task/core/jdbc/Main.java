package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.cleanUsersTable();

        userService.createUsersTable();

        userService.saveUser("Den", "Balan", (byte) 39);
        userService.saveUser("Jack", "Grealish", (byte) 28);
        userService.saveUser("Phill", "Foden", (byte) 21);
        userService.saveUser("Pep", "Guardiola", (byte) 49);

        userService.removeUserById(1);

        userService.getAllUsers();
    }
}
