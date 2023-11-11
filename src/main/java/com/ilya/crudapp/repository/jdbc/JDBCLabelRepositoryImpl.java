package com.ilya.crudapp.repository.jdbc;

import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.LabelRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ilya.crudapp.connectbd.MySQLConnector.returnPrepareStatement;
import static com.ilya.crudapp.connectbd.MySQLConnector.returnStatement;

public class JDBCLabelRepositoryImpl implements LabelRepository {

    @Override
    public Label getById(Long aLong) {
        Label resultLabel;
        try {
            ResultSet set = returnStatement().executeQuery("SELECT * from label where id ="+aLong);
            resultLabel = returnLabel(set);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultLabel;
    }

    @Override
    public boolean deleteById(Long aLong) {
        Label label = getById(aLong);
            label.setStatus(Status.DELETED);
            update(label);
            return true;
    }

    @Override
    public Label update(Label label) {
        try {
            PreparedStatement ps = returnPrepareStatement("UPDATE label SET name_label=? where id=?");
            ps.setString(1,label.getName());
            ps.setInt(2, Math.toIntExact(label.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return label;
    }

    @Override
    public Label save(Label label) {
        try {
            PreparedStatement ps = returnPrepareStatement("INSERT INTO label (name_label, status) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, label.getName());
            ps.setString(2, label.getStatus().name());
            ps.executeUpdate();
            label.setId((long) getIdSaver(ps.getGeneratedKeys()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        label.setId(generateId);
        return label;
    }

    @Override
    public List<Label> getAll() {
        List<Label> result = new ArrayList<>();
        try {
            ResultSet set = returnStatement().executeQuery("SELECT * from label");
            while (set.next()){
                Label label = new Label();
                label.setId((long) set.getInt(1));
                label.setName(set.getString(2));
                label.setStatus(Enum.valueOf(Status.class,set.getString(3)));
                result.add(label);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    private Label returnLabel(ResultSet setOfMethod){
        Label label = new Label();
        try {
            while (setOfMethod.next()) {
                label.setId((long) setOfMethod.getInt(1));
                label.setName(setOfMethod.getString(2));
                Status tmp = Enum.valueOf(Status.class, setOfMethod.getString(3));
                label.setStatus(tmp);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return label;
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
