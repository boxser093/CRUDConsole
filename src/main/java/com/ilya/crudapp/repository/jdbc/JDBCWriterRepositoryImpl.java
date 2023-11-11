package com.ilya.crudapp.repository.jdbc;

import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.model.Writer;
import com.ilya.crudapp.repository.WriterRepository;
import static com.ilya.crudapp.connectbd.MySQLConnector.returnPrepareStatement;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCWriterRepositoryImpl implements WriterRepository {
    @Override
    public Writer getById(Long aLong) {
        try {
            PreparedStatement ps = returnPrepareStatement("SELECT * from writer where id=?");
            ps.setInt(1, Math.toIntExact(aLong));
            ResultSet resultSet = ps.executeQuery();
            return returnWriter(resultSet);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean deleteById(Long aLong) {
        Writer writer = getById(aLong);
        try {
            PreparedStatement ps = returnPrepareStatement("UPDATE writer SET status=? where id=?");
            ps.setString(1,Status.DELETED.name());
            ps.setInt(2, Math.toIntExact(aLong));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Writer save(Writer writer) {
        try {

            PreparedStatement ps = returnPrepareStatement("INSERT INTO writer(status,firstName,lastName) value(?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, writer.getStatus().name());
            ps.setString(2, writer.getFirstName());
            ps.setString(3, writer.getLastName());
            ps.executeUpdate();
            writer.setId((long) getIdSaver(ps.getGeneratedKeys()));
            updatePostOfWriters(writer);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return writer;
    }
    @Override
    public Writer update(Writer writer) {
        try {
            PreparedStatement ps = returnPrepareStatement("UPDATE writer SET firstName=?, lastName=? where id=?");
            {
                ps.setString(1, writer.getFirstName());
                ps.setString(2, writer.getLastName());
                ps.setInt(3, Math.toIntExact(writer.getId()));
            }
            System.out.println(setNullOldPosts(writer)+" ,"+ updatePostOfWriters(writer));
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return writer;
    }
    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        try {
            PreparedStatement ps = returnPrepareStatement("SELECT DISTINCT id FROM writer");
            ResultSet set = ps.executeQuery();
            while (set.next()){
                Writer tmp = getById((long) set.getInt(1));
                if(tmp.getStatus().equals(Status.ACTIVE)) {
                    writers.add(tmp);
                }
            }
            return writers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Writer returnWriter(ResultSet set){
       Writer writer = new Writer();
        try {
            // мапим писателя
            while (set.next()){
                writer.setId((long) set.getInt(1));
                writer.setStatus(Enum.valueOf(Status.class,set.getString(2)));
                writer.setFirstName(set.getString(3));
                writer.setLastName(set.getString(4));
            }

            List<Post> postList = new ArrayList<>();
            PreparedStatement ps1 = returnPrepareStatement("SELECT DISTINCT id FROM post where writer_id=?");
            ps1.setInt(1, Math.toIntExact(writer.getId()));
            ResultSet set1 = ps1.executeQuery();
            while (set1.next()){
                postList.add(getByIdPost((long) set1.getInt(1)));
            }
            writer.setPosts(postList);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return writer;
    }
    public Post getByIdPost(Long aLong) {
        try {
            PreparedStatement ps = returnPrepareStatement("select * from post join posts_labels on post.id = posts_labels.post_id join label on posts_labels.label_id = label.id where post.id=?");
            ps.setInt(1, Math.toIntExact(aLong));
            ResultSet set = ps.executeQuery();
            return returnPost(set);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Post returnPost(ResultSet set) {
        Post post = new Post();
        List<Label> labels = new ArrayList<>();
        try {
            while (set.next()) {
                post.setId((long) set.getInt(1));
                post.setContent(set.getString(2));
                post.setCreated(set.getDate(3));
                post.setUpdate(set.getDate(4));
                post.setStatus(Enum.valueOf(Status.class,set.getString(5)));
                Label tmp = new Label();
                tmp.setId((long) set.getInt(9));
                tmp.setName(set.getString(10));
                tmp.setStatus(Enum.valueOf(Status.class,set.getString(11)));
                if (tmp.getStatus().equals(Status.ACTIVE)){
                    labels.add(tmp);
                }
            }
            post.setLabels(labels);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }
    private boolean updatePostOfWriters(Writer writerIn) {
        try {
            PreparedStatement postNewUpdate = returnPrepareStatement("UPDATE post SET writer_id=?, `update`=? where id=?");
            for (Post post : writerIn.getPosts()) {
                postNewUpdate.setInt(1, Math.toIntExact(writerIn.getId()));
                Date date1 = new java.util.Date();
                Timestamp timestamp1 = new Timestamp(date1.getTime());
                postNewUpdate.setTimestamp(2, timestamp1);
                postNewUpdate.setInt(3, Math.toIntExact(post.getId()));
                postNewUpdate.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    private boolean setNullOldPosts(Writer writerIn) {
        try {
            PreparedStatement postUpdate = returnPrepareStatement("UPDATE post SET writer_id=?, `update`=? where id=?");
            postUpdate.setString(1, null);
            Date date = new java.util.Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            postUpdate.setTimestamp(2, timestamp);
            postUpdate.setInt(3, Math.toIntExact(writerIn.getId()));
            postUpdate.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private int getIdSaver(ResultSet query){
        try {
            int idSaver=0;
            if(query.first()){
                idSaver = query.getInt(1);
            }
            return idSaver;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
