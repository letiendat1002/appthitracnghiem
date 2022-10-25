package Model;

public class User {
    private String user_id;
    private String full_name;
    private String password_hash;
    private boolean is_host;

    public User() {
    }

    public User(String user_id, String full_name, String password_hash, boolean is_host) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.password_hash = password_hash;
        this.is_host = is_host;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPassword() {
        return password_hash;
    }

    public void setPassword(String password_hash) {
        this.password_hash = password_hash;
    }

    public boolean isHost() {
        return is_host;
    }

    public void setHost(boolean is_host) {
        this.is_host = is_host;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", full_name='" + full_name + '\'' +
                ", password_hash='" + password_hash + '\'' +
                ", is_host=" + is_host +
                '}';
    }
}
