package com.xiaoha;

import java.sql.*;

/**
 * @author: ZeKai
 * @date: 2025/4/17
 * @description:
 **/
public class SynchronizeMethod implements Runnable {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/movie_ticket_db";
    // 数据库用户名
    private static final String DB_USER = "root";
    // 数据库密码
    private static final String DB_PASSWORD = "lzk100207";
    int ticket=0;

    @Override
    public void run() {

        while(true){
            if (extracted()) break;
        }
    }

    private synchronized boolean extracted() {
        synchronized (SynchronizeMethod.class){
            if(ticket==10000) return true;
            else {

                sellticket(1);

            }
        }
        return false;
    }

    public static int getnum(int ticketId) {
        int num = 0;
        // 修正后的 SQL 语句
        String sql = "SELECT stock FROM movie_tickets WHERE ticket_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            try (ResultSet res = pstmt.executeQuery()) {
                // 判断结果集是否有数据
                if (res.next()) {
                    num = res.getInt("stock");
                    if (num > 0) {
                        System.out.println("剩余： " + num + " 张");
                    } else {
                        System.out.println("已售罄");
                    }
                } else {
                    System.out.println("未找到对应的电影票记录");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }
    public static void sellticket(int ticketId) {
        String sql = "UPDATE movie_tickets SET stock = stock-100 WHERE ticket_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            if(getnum(1)<=0) Thread.currentThread().interrupt();
            if (getnum(1) > 0){
                int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("电影票库存成功卖出");
            } else {
                System.out.println("未找到对应的电影票");
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
