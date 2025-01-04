package jdbc;

import util.ConnectionInfo;

public class PersonDao {

    private ConnectionInfo connectionInfo;

    public PersonDao(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

}
