package suwu.daopuerp.dto.account;

public class UserDto {
    private String username;

    public UserDto() {
    }

    public UserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
