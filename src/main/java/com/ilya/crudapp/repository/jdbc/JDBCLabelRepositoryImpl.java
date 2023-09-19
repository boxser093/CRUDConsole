package com.ilya.crudapp.repository.jdbc;

import com.ilya.crudapp.connectbd.MySQLConnector;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.LabelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
  1. Заменить все boolean и enum на text или varchar. --
 2. Нормализовать серриализацию и дессириализацию.
 3. Исправить changelog.
 **/
public class JDBCLabelRepositoryImpl implements LabelRepository {
    Connection connection = MySQLConnector.getMySQLConnector().getConnetion();

    @Override
    public Label getById(Long aLong) {
        Label resultLabel = new Label();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * from label where id ="+aLong);
            while (set.next()){
                resultLabel.setId((long) set.getInt(1));
                resultLabel.setName(set.getString(2));
                Status tmp = Enum.valueOf(Status.class, set.getString(3));
                resultLabel.setStatus(tmp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultLabel;
    }

    @Override
    public boolean deleteById(Long aLong) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM label WHERE id ="+ aLong);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Label update(Label label) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE label SET name_label=?, status=? where id=?");
            ps.setString(1,label.getName());
            ps.setString(2, String.valueOf(label.getStatus()));
            ps.setString(3, String.valueOf(label.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return label;
    }

    @Override
    public Label save(Label label) {
        try {
            PreparedStatement ps = MySQLConnector.getMySQLConnector().getConnetion().prepareStatement("INSERT INTO label (name_label, status) VALUES (?,?)");
            ps.setString(1, label.getName());
            ps.setString(2, label.getStatus().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return label;
    }

    @Override
    public List<Label> getAll() {
        List<Label> result = new ArrayList<>();
        Label tmp = new Label();
        try {
            Statement statement = MySQLConnector.getMySQLConnector().getConnetion().createStatement();
            ResultSet set = statement.executeQuery("SELECT * from label");
            while (set.next()){
                tmp.setId((long) set.getInt(1));
                tmp.setName(set.getString(2));
                String tmpEn = set.getString(3);
                tmp.setStatus(Enum.valueOf(Status.class, tmpEn ));
                result.add(tmp);
                tmp = new Label();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
