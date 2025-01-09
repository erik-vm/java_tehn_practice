package security;

import java.util.Map;

public class TokenToUserMapper {

    public static User getUserForToken(String token) {
        var dummy = new User("", "nobody", "");

        return Map.of(
                "t1", new User("Alice", "alice", "hs57L/hg"),
                "t2", new User("Bob", "bob", "pa$$")).getOrDefault(token, dummy);
    }

    public static class User {
        public User(String name, String userName, String password) {
            this.name = name;
            this.userName = userName;
            this.password = password;
        }
        public String userName;
        public String name;
        public String password;
    }

}
