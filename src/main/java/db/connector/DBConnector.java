package db.connector;

import java.sql.*;

public class DBConnector {
    public static void main(String[] args) {
        System.out.println("Hi, nice to let me play with u =)");

        try {
            /**
             * Embedded
             * jdbc:h2:~/test 'test' in the user home directory
             * jdbc:h2:/data/test 'test' in the directory /data
             * jdbc:h2:test in the current(!) working directory
             *
             * In-Memory
             * jdbc:h2:mem:test multiple connections in one process
             * jdbc:h2:mem: unnamed private; one connection
             *
             * http://www.h2database.com/html/cheatSheet.html
             *
             * https://h2database.com/html/tutorial.html#connecting_using_jdbc
             */
            try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:", "user", "")) {
                Statement stat = conn.createStatement();
                if (true) {
                    stat.execute("CREATE TABLE TEST AS SELECT * FROM CSVREAD('src/main/resources/my_db_data.csv')");
                    try (ResultSet rs = stat.executeQuery("select * from test")) {
                        while (rs.next()) {
                            System.out.println(rs.getString("id"));
                            System.out.println(rs.getString("street"));
                        }
                    }
                } else {
                    stat.execute("create table test(id int primary key, name varchar(255))");
                    stat.execute("insert into test values(1, 'Hello')");
                    stat.execute("insert into test values(2, 'welcome')");
                    stat.execute("insert into test values(3, 'here')");
                    try (ResultSet rs = stat.executeQuery("select * from test")) {
                        while (rs.next()) {
                            System.out.println(rs.getString("name"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
