package com.ilya.crudapp.repository.jdbc;

import com.ilya.crudapp.connectbd.MySQLConnector;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.model.Writer;
import com.ilya.crudapp.repository.LabelRepository;
import com.ilya.crudapp.repository.PostRepository;
import com.ilya.crudapp.repository.WriterRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCWriterRepositoryImpl implements WriterRepository {
    private final PostRepository bdPostRepository = new JDBCPostRepositoryImpl();
    Connection connection = MySQLConnector.getMySQLConnector().getConnetion();
    @Override
    public Writer getById(Long aLong) {
        Writer result = new Writer();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from writer where id=?");
            preparedStatement.setInt(1, Math.toIntExact(aLong));
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                result.setId((long) set.getInt(1));
                result.setStatus(Enum.valueOf(Status.class,set.getString(2)));
                result.setFirstName(set.getString(3));
                result.setLastName(set.getString(4));
            }
            List<Post> postList = new ArrayList<>();
            try {
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * from post where writer_id=?");
                preparedStatement1.setInt(1, Math.toIntExact(result.getId()));
                ResultSet set1 = preparedStatement1.executeQuery();
                while (set1.next()){
                    postList.add(bdPostRepository.getById((long) set1.getInt(1)));
                }
                result.setPosts(postList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean deleteById(Long aLong) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from writer where id=?");
            preparedStatement.setInt(1, Math.toIntExact(aLong));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Writer save(Writer writer) {
        int writerId = 0;
        try {
            // записать writer
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO writer(status,firstName, lastName) values (?,?,?)");
            preparedStatement.setString(1, writer.getStatus().name());
            preparedStatement.setString(2, writer.getFirstName());
            preparedStatement.setString(3, writer.getLastName());
            preparedStatement.executeUpdate();
            // получить id writer
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM writer ORDER BY ID DESC LIMIT 1");
            while (set.next()){
                writerId = set.getInt(1);
            }
            // списку постов закрепленному за writer присвоить writer_id
            for (Post postOfWriter: writer.getPosts()){
                PreparedStatement statement = connection.prepareStatement("UPDATE post SET writer_id=? where id=?");
                statement.setString(1, String.valueOf(writerId));
                statement.setString(2, String.valueOf(postOfWriter.getId()));
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getById((long) writerId);
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();

        try {
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM writer");
            while (set.next()){
                writers.add(getById((long) set.getInt(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writers;
    }

    @Override
    public Writer update(Writer writer) {
        return null;
    }
}
