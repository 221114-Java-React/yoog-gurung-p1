package com.revature.ribs.daos;

import com.revature.ribs.models.Role;
import com.revature.ribs.models.User;
import com.revature.ribs.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements CrudDAO<User> {
    @Override
    public void save(User obj){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (user_id, username, password, role) VALUES (?, ?, ?, ?::roles)");
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getPassword());
            ps.setString(4, String.valueOf(obj.getRole()));
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User obj){

    }

    @Override
    public void update(User obj){

    }
    @Override
    public User findById(){
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement(("SELECT * FROM users"));
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                User currentUser = new User(rs.getString("id"), rs.getString("username"), rs.getString("password"), Role.valueOf(rs.getString("role")));
                users.add(currentUser);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    public List<String> findAllUsernames(){
        List<String> usernames = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT (username) from users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String currentUsername = rs.getString("username");
                usernames.add(currentUsername);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usernames;
    }

    public User getUserByUsernameAndPassword(String username, String password){
        User user = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement(("SELECT users WHERE username = ? AND password = ?"));
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                user = new User(rs.getString("id"), rs.getString("username"), rs.getString("password"), Role.valueOf(rs.getString("role")));

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getAllUsersByUserName(String username){
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username LIKE ?");
            ps.setString(1, username + "%");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                User user = new User(rs.getString("id"), rs.getString("username"), rs.getString("password"), Role.valueOf(rs.getString("role")));
                users.add(user);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        return users;
    }

}
