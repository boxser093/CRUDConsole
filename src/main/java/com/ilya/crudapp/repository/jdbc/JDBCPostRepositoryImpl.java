package com.ilya.crudapp.repository.jdbc;

import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.PostRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.ilya.crudapp.connectbd.MySQLConnector.returnPrepareStatement;

public class JDBCPostRepositoryImpl implements PostRepository {

    @Override
    public Post getById(Long aLong) {
        try {
            PreparedStatement ps = returnPrepareStatement("select * from post join posts_labels on post.id = posts_labels.post_id join label on posts_labels.label_id = label.id where post.id=?");
            ps.setInt(1, Math.toIntExact(aLong));
            ResultSet set = ps.executeQuery();
           return returnPost(set);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long aLong) {
        Post tmp = getById(aLong);
        tmp.setUpdate(new java.util.Date());
        tmp.setStatus(Status.DELETED);
        return true;
    }

    @Override
    public Post save(Post post) {
        try {
            PreparedStatement ps = returnPrepareStatement("INSERT INTO post(content, created, status) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getContent());
            ps.setTimestamp(2,  new Timestamp(post.getCreated().getTime()));
            ps.setString(3, post.getStatus().name());
            ps.executeUpdate();
            saveLabels(getIdSaver(ps.getGeneratedKeys()),post.getLabels());
            post.setId((long) getIdSaver(ps.getGeneratedKeys()));
            return post;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post update(Post post) {
        try {
            PreparedStatement ps = returnPrepareStatement("UPDATE post SET content=?, `update`=? where id=?");
            ps.setString(1, post.getContent());
            ps.setTimestamp(2, Timestamp.valueOf(post.getUpdate().toString()));
            ps.setInt(3, Math.toIntExact(post.getId()));
            ps.executeUpdate();

            deletedFromManyToMany(Math.toIntExact(post.getId()));

            saveLabels(Math.toIntExact(post.getId()),post.getLabels());
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        try {
            ResultSet set = returnPrepareStatement("SELECT DISTINCT id FROM post").executeQuery();
            while (set.next()){
                result.add(getById((long) set.getInt(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    private Post returnPost(ResultSet set) {
        Post postResult = new Post();
        List<Label> labels = new ArrayList<>();
        try {
            while (set.next()) {
            postResult.setId((long) set.getInt(1));
            postResult.setContent(set.getString(2));
            postResult.setCreated(set.getDate(3));
            postResult.setUpdate(set.getDate(4));
            postResult.setStatus(Enum.valueOf(Status.class,set.getString(5)));
            Label tmp = new Label();
            tmp.setId((long) set.getInt(9));
            tmp.setName(set.getString(10));
            tmp.setStatus(Enum.valueOf(Status.class,set.getString(11)));
            if (tmp.getStatus().equals(Status.ACTIVE)){
                labels.add(tmp);
            }
            }
            postResult.setLabels(labels);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return postResult;
    }
    private void saveLabels(int idPost, List<Label> labelsOfPost){
        try {
            for (Label labelOfPost : labelsOfPost) {
                PreparedStatement ps1 = returnPrepareStatement("INSERT  INTO posts_labels (post_id,label_id) values (?,?)");
                ps1.setInt(1, idPost);
                ps1.setInt(2, Math.toIntExact(labelOfPost.getId()));
                ps1.execute();
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void deletedFromManyToMany(int idPost){
        try {
            PreparedStatement ps = returnPrepareStatement("DELETE * FROM posts_labels where post_id=?");
            ps.setInt(1, idPost);
            ps.executeUpdate();
        } catch (SQLException e){
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
