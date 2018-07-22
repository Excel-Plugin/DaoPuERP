package suwu.daopuerp.daoimpl.user;

import suwu.daopuerp.dao.user.UserDao;
import suwu.daopuerp.data.fileservice.FileService;
import suwu.daopuerp.data.fileservice.FileServiceImpl;
import suwu.daopuerp.entity.account.User;

public class UserDaoImpl implements UserDao {
    private FileService<User> fileService = new FileServiceImpl<>();

    @Override
    public User save(User user) {
        return fileService.saveTuple(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return fileService.findOne(username, User.class);
    }
}
