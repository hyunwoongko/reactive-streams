package impements;

import impements.subscriber.Flowable;
import impements.subscriber.Mappable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Worker {

    private List<Flowable<PreparedStatement>> params;
    private Mappable<ResultSet, Object> mapper;
    private String query;

    static {
        try {
            Class.forName("DRIVER");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Worker SQL(String sql) {
        this.params = new Vector<>();
        this.query = sql;
        return this;
    }

    public Worker param(Flowable<PreparedStatement> param) {
        if (query == null) throw new NullPointerException("No Query");
        else params.add(param);
        return this;
    }

    private boolean isParameterError() {
        int countQuestionMarks = 0;
        for (char character : query.toCharArray())
            if (character == '?') countQuestionMarks++;
        return countQuestionMarks != params.size();
    }

    public Worker map(Mappable<ResultSet, Object> mapper) {
        if (query == null) throw new NullPointerException("No Query");
        else this.mapper = mapper;
        return this;
    }

    public <Out> Out getData(Class<Out> type) {
        if (query == null) throw new NullPointerException("No Query");
        else if (isParameterError())
            throw new IllegalArgumentException("Please call 'param' method for the number of question marks in the PreparedStatement.");
        else try (Connection connection = DriverManager.getConnection("URL", "ID", "PW");
                  PreparedStatement statement = connection.prepareStatement(query)) {
                for (Flowable<PreparedStatement> param : params) param.onNext(statement);
                try (ResultSet set = statement.executeQuery()) {
                    if (set.next()) {
                        if (type.isInstance(Collections.emptyList())) {
                            List<Out> list = new Vector<>();
                            do list.add((Out) mapper.onMap(set));
                            while (set.next());
                            return (Out) list;
                        } else return (Out) mapper.onMap(set);
                    } else return type.isInstance(Boolean.FALSE)
                            ? (Out) Boolean.FALSE
                            : type.isInstance(0)
                            ? (Out) Integer.valueOf(0)
                            : null;
                }
            } catch (Exception e) {
                return type.isInstance(Boolean.FALSE)
                        ? (Out) Boolean.FALSE
                        : type.isInstance(0)
                        ? (Out) Integer.valueOf(0)
                        : null;
            } finally {
                query = null;
                params = null;
                mapper = null;
            }

    }

    public boolean setData() {
        if (query == null) throw new NullPointerException("No Query");
        else if (isParameterError())
            throw new IllegalArgumentException("Please call the 'param' method for the number of question marks in the PreparedStatement.");
        else try (Connection connection = DriverManager.getConnection("URL", "ID", "PW");
                  PreparedStatement statement = connection.prepareStatement(query)) {
                for (Flowable<PreparedStatement> param : params) param.onNext(statement);
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                query = null;
                params = null;
            }
    }
}