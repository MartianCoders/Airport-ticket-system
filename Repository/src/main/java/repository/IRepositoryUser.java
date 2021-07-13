package repository;

import model.User;

public interface IRepositoryUser extends IRepository<Integer, User> {
    User findEntityUser(String user);
}
