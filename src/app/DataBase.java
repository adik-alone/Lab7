package app;

import app.MultiThread.ClientData;
import person.Person;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Random;
import java.util.TreeSet;
import java.util.random.*;

public class DataBase {
    App app;
    String url = "";
    String user = "";
    String password_Admin = "";
    Connection connection;
    TreeSet<Person> set;


    public void setApp(App app) {
        this.app = app;
    }
    public void Connect()throws SQLException{
        connection = DriverManager.getConnection(url, user, password_Admin);
    }

//    public void Start(){
//        set = new TreeSet<>();
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("Select * from g_collection_programming");
//
//        while (resultSet.next()){
////            Person = app.AddPersonToCollection();
//        }
//
//    }

    public void Add(){
//        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into g_collection_programming (name, coordinates_x, coordinates_y...) Values (потом дописать)");
    }

    public boolean Login(){
        return true;
    }
    public boolean Registration(String login, String password){
        try {
            connection = DriverManager.getConnection(url, user, password_Admin);
//            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from g_user");
            ResultSet resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet);
            while(resultSet.next()){
                String user_name = resultSet.getString("user_name");
                if (user_name.equals(login)){
                    return false;
                }
            }
            String salt = getRandomString();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest((password + salt).getBytes("UTF-8"));
            Statement statement = connection.createStatement();
            String name1 = "user_name";
            String name2 = "password";
            String name3 = "salt";
            String sql = "insert into g_user (user_name, password, salt) VALUES (" + "'" + login + "'" + "," + "'" + hash + "'" + ","  + "'" + salt + "'" + ")";
            int res = statement.executeUpdate(sql);
//            PreparedStatement addUser = connection.prepareStatement();
//            addUser.setNString(1, login);
//            addUser.setNString(2, String.valueOf(hash));
//            addUser.setNString(3, salt);
//            int res = addUser.executeUpdate();
            resultSet.close();
            preparedStatement.close();
            statement.close();
            connection.close();
            if (res == 1){
                return true;
            }
            return false;

        }catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void Remove(){}
    public void Update(){}




    public void Work()throws SQLException{
        Statement statement = connection.createStatement();
    }
























    public String getRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int length = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

}
