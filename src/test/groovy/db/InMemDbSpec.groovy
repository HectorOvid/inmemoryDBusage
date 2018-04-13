package db

import spock.lang.Specification

import java.sql.*

class InMemDbSpec extends Specification {
    def "first mini db creation manually"() {
        expect:
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
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:", "user", "")
            Statement stat = conn.createStatement()
            stat.execute("CREATE TABLE TEST AS SELECT * FROM CSVREAD('src/test/resources/my_db_test_data.csv')");
            try {
                ResultSet rs = stat.executeQuery("select * from test")
                while (rs.next()) {
                    System.out.println(rs.getString("id"));
                    System.out.println(rs.getString("street"));
                }
            } catch (SQLException) {
                println "upsi"
            }
        } catch (Exception e) {
            assert false
        }
    }


    def "create db from csv"() {
        expect:
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
            try {
               Connection conn = DriverManager.getConnection("jdbc:h2:mem:", "user", "")
                Statement stat = conn.createStatement()
                stat.execute("create table test(id int primary key, name varchar(255))");
                stat.execute("insert into test values(1, 'Hello')");
                stat.execute("insert into test values(2, 'welcome')");
                stat.execute("insert into test values(3, 'here')");
                try {
                    ResultSet rs = stat.executeQuery("select * from test")
                    while (rs.next()) {
                        System.out.println(rs.getString("name"))
                    }
                } catch (SQLException e) {
                    e.printStackTrace()
                }
            } catch (Exception e) {
                assert false
            }
    }
}
