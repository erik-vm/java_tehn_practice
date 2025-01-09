package spring;

import javax.sql.DataSource;

public interface Dao {

    default DataSource getDataSource() {
        return null;
    }
}
