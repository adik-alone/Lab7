package app;

import app.MultiThread.ClientData;
import app.MultiThread.SendAnswerThread;
import person.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.random.*;

public class DataBase {
    App app;
    CreatorServer creator;
    String url = "jdbc:postgresql://localhost:5432/studs";
    String user = "s367600";
    String password_Admin = "flTGJGDSl6vRmGtQ";
    Connection connection;
    TreeSet<Person> set;
    LocalDateTime CollectionTime;


    public void setApp(App app) {
        this.app = app;
    }
    public void Connect()throws SQLException{
        connection = DriverManager.getConnection(url, user, password_Admin);
    }
    public void Disconnect() throws SQLException{
        connection.close();
    }
    public void Start()throws SQLException{
        creator = new CreatorServer(app);
        set = new TreeSet<>();
        CollectionTime = LocalDateTime.now();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from g_persons");
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Long coordinatesX = resultSet.getLong("coordinates_x");
            double coordinatesY = resultSet.getDouble("coordinates_y");
            double height = resultSet.getDouble("height");
            LocalDateTime birthday = resultSet.getTimestamp("birthday").toLocalDateTime();
            ColorEye eye = ColorEye.valueOf(resultSet.getString("eyecolor"));
            ColorHair hair = ColorHair.valueOf(resultSet.getString("haircolor"));
            int location_x = resultSet.getInt("locationx");
            double location_y = resultSet.getDouble("locationy");
            double location_z = resultSet.getDouble("locationz");
            LocalDateTime createdTime = resultSet.getTimestamp("createdtime").toLocalDateTime();
            Person p = creator.createPerson(id, name, coordinatesX, coordinatesY, height, birthday, eye, hair, createdTime, location_x, location_y, location_z);
            set.add(p);
        }
        System.out.println("Collection created done");
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

//    public void Add(){
//        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into g_collection_programming (name, coordinates_x, coordinates_y...) Values (потом дописать)");
//    }

    public synchronized void Login(ClientData clientData, String login, String password){
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        SendAnswerThread send = new SendAnswerThread(clientData, queue);
        send.start();

        System.out.println(send);
        System.out.println("here3");
        try {
            System.out.println("here");
            MessageDigest md = MessageDigest.getInstance("MD5");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("Select * from g_user");
            while (result.next()) {
                String name_user = result.getString("user_name");
                System.out.println("user: " + name_user);
//                System.out.println("start find user");
                if (name_user.equals(login)) {
                    String password_user = result.getString("password");
                    System.out.println(password_user);
                    String salt = result.getString("salt");
                    byte[] hash_password = md.digest((password + salt).getBytes("UTF-8"));
//                    System.out.println(hash_password);
                    String user_passwd = new String(hash_password, StandardCharsets.UTF_8);
                    if (user_passwd.equals(password_user)) {
                        result.close();
                        statement.close();
                        queue.put("true");
                        queue.put("Авторизация пройдена успешно");
                        queue.put("+++SUCCESS+++");
                        return;
                    }else{
                        queue.put("false");
                        queue.put("Авторизация провалена, попробуйте снова");
                        queue.put("+++SUCCESS+++");
                        return;
                    }
                }
            }
            queue.put("false");
            queue.put("Произошла неизвестная ошибка");
            queue.put("+++SUCCESS+++");
        }catch (SQLException | InterruptedException | NoSuchAlgorithmException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
    public synchronized void Registration(ClientData clientData, String login, String password){
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        SendAnswerThread send = new SendAnswerThread(clientData, queue);
        send.start();
        System.out.println(send);

        try {
//            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from g_user");
            ResultSet resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet);
            while(resultSet.next()){
                String user_name = resultSet.getString("user_name");
                if (user_name.equals(login)){
                    queue.put("false");
                    queue.put("Имя пользователя уже занято");
                    queue.put("+++SUCCESS+++");
                    return;
                }
            }
            String salt = getRandomString();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest((password + salt).getBytes("UTF-8"));
            Statement statement = connection.createStatement();
            String passwd = new String(hash, StandardCharsets.UTF_8);
            System.out.println("User: " + login + " :: password: " + passwd);
            String sql = "insert into g_user (user_name, password, salt) VALUES (" + "'" + login + "'" + "," + "'" + passwd + "'" + ","  + "'" + salt + "'" + ")";
            int res = statement.executeUpdate(sql);
//            PreparedStatement addUser = connection.prepareStatement();
//            addUser.setNString(1, login);
//            addUser.setNString(2, String.valueOf(hash));
//            addUser.setNString(3, salt);
//            int res = addUser.executeUpdate();
            resultSet.close();
            preparedStatement.close();
            statement.close();
//            connection.close();
            if (res == 1){
                queue.put("true");
                queue.put("Регистрация успешна");
                queue.put("+++SUCCESS+++");
                return;
            }
            queue.put("false");
            queue.put("Произошла неизвестная ошибка");
            queue.put("+++SUCCESS+++");

        }catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException | InterruptedException e) {
            e.printStackTrace();
            try {
                queue.put("false");
                queue.put("Произошла опознанная ошибка. Мы уже работаем над её устранением");
                queue.put("+++SUCCESS+++");
            } catch (InterruptedException ex) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void Info(ClientData data){
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        SendAnswerThread send = new SendAnswerThread(data, queue);
        send.start();
        try {
            queue.put("Информация о коллекции");
            queue.put("Размер коллекции: " + set.size());
            queue.put("Тип коллекции: " + set.getClass());
            queue.put("Время создания: " + CollectionTime);
            queue.put("+++SUCCESS+++");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void Show(ClientData clientData){
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        SendAnswerThread send = new SendAnswerThread(clientData, queue);
        send.start();
        try {
            queue.put("Показываем коллекцию");
            set.stream().sorted().forEach(e -> {
                try {
                    queue.put(String.valueOf(e) + "\n=============================");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            });
            queue.put("+++SUCCESS+++");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public synchronized void Add(ClientData clientData, Person person){
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        SendAnswerThread send = new SendAnswerThread(clientData, queue);
        send.start();


        String name = person.getName();
        Long coordinatesX = person.getCoordinates().getX();
        double coordinatesY = person.getCoordinates().getY();
        double height = person.getHeight();
        LocalDateTime birthday = person.getBirthday();
        ColorEye eye = person.getEyeColor();
        ColorHair hair =person.getHairColor();
        int location_x = person.getLocation().getX();
        double location_y = person.getLocation().getY();
        double location_z = person.getLocation().getZ();
        LocalDateTime createdTime = person.getCreationDate();

        try {
            int id = LastPersonId();
            Statement statement = connection.createStatement();
            int user_id;
            Statement statement_user = connection.createStatement();
            ResultSet result = statement_user.executeQuery("select id from g_user where user_name = '" + clientData.getUser_name() + "'");
            result.next();
            user_id = result.getInt(1);

            String query = "insert into g_persons(name, coordinates_x, coordinates_y, height, birthday, eyecolor, haircolor, locationx, locationy, locationz, createdtime, user_id) values('" + name + "', '" + coordinatesX + "', '" + coordinatesY + "', '" + height + "', '" + birthday + "', '" + eye + "', '" + hair + "', '" + location_x + "', '" + location_y + "', '" + location_z + "', '" + createdTime + "', '" + user_id + "')";
            int res = statement.executeUpdate(query);
            if (res == 1) {
                set.add(person);
                queue.put("Создание успешное");
                queue.put("+++SUCCESS+++");
            } else {
                queue.put("Создание успешно провалено");
                queue.put("+++SUCCESS+++");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void Remove(ClientData clientData, String str_id){
        int id = Integer.parseInt(str_id);
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        SendAnswerThread send = new SendAnswerThread(clientData, queue);
        send.start();

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from g_user where user_name = '" + clientData.getUser_name() + "'");
            resultSet.next();
            int user_id = resultSet.getInt(1);


            PreparedStatement statement2 = connection.prepareStatement("select user_id from g_persons where id = "+ id);
            ResultSet resultSet1 = statement2.executeQuery();
            resultSet1.next();
            if (user_id != resultSet1.getInt(1)){
                queue.put("Это не ваш элемент");
                queue.put("+++SUCCESS+++");
                return;
            }
            int res = statement.executeUpdate("delete from g_persons where id = " + id);
            if (res == 1){
                removeInSet(id);
                queue.put("Удаление успешно");
                queue.put("+++SUCCESS+++");
            }else{
                queue.put("Что-то пошло не так");
                queue.put("+++SUCCESS+++");
            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void removeInSet(int id){
        for (Person p: set){
            if (p.getId() == id){
                set.remove(p);
            }
        }
    }

    public synchronized void Update(ClientData clientData){}




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

    public int LastPersonId() throws SQLException{
        int last_id = 0;
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery("select id from g_persons");
        while (result.next()){
            int current_id = result.getInt("id");
            if (last_id < current_id){
                last_id = current_id;
            }
        }
        return last_id;
    }

}
