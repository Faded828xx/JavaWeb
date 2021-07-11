package Chapter8;

import java.sql.*;

public class BookDB {

    static String dbUrl = "jdbc:mysql://localhost:3306/BookDB";
    static String dbUser = "root";
    static String dbPwd = "zyh.2998..";
    static String driverName = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        java.sql.DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection connection = java.sql.DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        String selectStatement = "Select * from BOOKS";
//        String insertStatement = "insert into BOOKS values('211', '小孩子', 'MySQL是怎样运行的', '99', '2019');";
        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
//        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
//        preparedStatement.executeUpdate();
        while (resultSet.next()) {
            String book = "";
            for (int i = 1; i <= 5; i++) {
                book += resultSet.getString(i) + " ";
            }
            System.out.println(book);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }


}
