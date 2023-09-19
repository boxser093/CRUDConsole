package com.ilya.crudapp.repository.jdbc;

import com.ilya.crudapp.connectbd.MySQLConnector;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.LabelRepository;
import com.ilya.crudapp.repository.PostRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. Заменить все boolean и enum на text или varchar;
 * 2. Протестировать процесс записи и восставновления объекта;
 *
 */

public class JDBCPostRepositoryImpl implements PostRepository {
    private final LabelRepository bdlabelRepository = new JDBCLabelRepositoryImpl();
    Connection connection = MySQLConnector.getMySQLConnector().getConnetion();

    @Override
    public Post getById(Long aLong) {
        Post resultPost = new Post();
        try {
            PreparedStatement ps = MySQLConnector.getMySQLConnector().getConnetion()
                    .prepareStatement("SELECT * from post where id =?");
            ps.setString(1, String.valueOf(aLong));
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                resultPost.setId((long) set.getInt("id"));
                resultPost.setContent(set.getString("content"));
                resultPost.setCreated(set.getBoolean("created"));
                resultPost.setUpdate(set.getBoolean("update"));
                resultPost.setStatus(Enum.valueOf(Status.class, set.getString("status")));
            }

            List<Label> labelsOfPost = new ArrayList<>();
            PreparedStatement ps1= MySQLConnector.getMySQLConnector().getConnetion()
                    .prepareStatement("SELECT * from posts_labels where post_id=?");
            ps1.setString(1, String.valueOf(aLong));
            ResultSet labelsSet = ps1.executeQuery();
            while (labelsSet.next()) {
                labelsOfPost.add(bdlabelRepository.getById((long) labelsSet.getInt(2)));
            }
            resultPost.setLabels(labelsOfPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultPost;
    }

    @Override
    public boolean deleteById(Long aLong) {
        try {
            PreparedStatement statement = MySQLConnector.getMySQLConnector().getConnetion()
                    .prepareStatement("DELETE FROM post WHERE id =?");
            statement.setString(1, String.valueOf(aLong));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post save(Post post) {
       int idNewOfPost = 0;
        try {
            PreparedStatement ps = MySQLConnector.getMySQLConnector().getConnetion()
                    .prepareStatement("INSERT INTO post(content, created, `update`, status) VALUES (?,?,?,?)");
            ps.setString(1, post.getContent());
            ps.setBoolean(2, post.isCreated());
            ps.setBoolean(3, post.isUpdate());
            ps.setString(4, Status.ACTIVE.name());
            ps.executeUpdate();

            ResultSet query = MySQLConnector.getMySQLConnector().getConnetion().createStatement().executeQuery("SELECT * FROM post ORDER BY ID DESC LIMIT 1");
            System.out.println(query.toString());
            while (query.next()){
                idNewOfPost = query.getInt(1);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement ps1;
            for (Label labelOfPosts : post.getLabels()) {
                ps1 = MySQLConnector.getMySQLConnector().getConnetion()
                        .prepareStatement("INSERT  INTO posts_labels (post_id,label_id) values (?,?)");

                ps1.setString(1, String.valueOf(idNewOfPost));
                ps1.setString(2, String.valueOf(labelOfPosts.getId()));
                System.out.println(ps1.toString());
                ps1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE post SET content=?, `update`=?, status=? where id=?");
            ps.setString(1, post.getContent());
            ps.setBoolean(2, true);
            ps.setString(3, post.getStatus().name());
            ps.setInt(4, Math.toIntExact(post.getId()));
            System.out.println(ps.toString());
            ps.executeUpdate();

            Statement ps1 = connection.createStatement();
            ps1.execute("DELETE  FROM posts_labels where post_id=" + post.getId());

            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO posts_labels(post_id,label_id) values (?,?)");
            ps2.setString(1, String.valueOf(post.getId()));
            for (Label postOfLabels : post.getLabels()) {
                ps2.setString(2, String.valueOf(postOfLabels.getId()));
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        Post tmp = new Post();
        try {
            Statement statement;
            statement = MySQLConnector.getMySQLConnector().getConnetion().createStatement();
            ResultSet set = statement.executeQuery("SELECT * from Post");
            while (set.next()) {
                tmp.setId((long) set.getInt(1));
                tmp.setContent(set.getString(2));
                tmp.setCreated(set.getBoolean(3));
                tmp.setUpdate(set.getBoolean(4));
                String enumPost = set.getString(5);
                tmp.setStatus(Enum.valueOf(Status.class,enumPost));

                List<Label> labelsOfPost = new ArrayList<>();
                PreparedStatement preparedStatement = MySQLConnector.getMySQLConnector().getConnetion()
                        .prepareStatement("SELECT * from posts_labels where post_id=?");
                preparedStatement.setInt(1, Math.toIntExact(tmp.getId()));

                ResultSet labelsSet = preparedStatement.executeQuery();
                while (labelsSet.next()) {
                    labelsOfPost.add(bdlabelRepository.getById((long) labelsSet.getInt(2)));
                }

                tmp.setLabels(labelsOfPost);
                result.add(tmp);
                tmp = new Post();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
