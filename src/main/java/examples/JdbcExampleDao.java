package examples;

import jdbc.Accessor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author : Hyunwoong
 * @When : 2019-01-13 오후 11:43
 * @Homepage : https://github.com/gusdnd852
 */
public class JdbcExampleDao extends Accessor {

    public ExamplesDto select(ExamplesDto dto) {
        return install(Info.url, Info.id, Info.pw)
                .quary("select * from table where primaryKey=?")
                .param(statement -> statement.setString(1, dto.getPrimaryKey()))
                .map(set -> ExamplesDto.builder()
                        .primaryKey(set.getString("primaryKey"))
                        .property(set.getString("property"))
                        .build())
                .getOnce(ExamplesDto.class);
    }

    public List<ExamplesDto> selectAll() {
        return install(Info.url, Info.id, Info.pw)
                .quary("select * from table")
                .map(set -> ExamplesDto.builder()
                        .primaryKey(set.getString("primaryKey"))
                        .property(set.getString("property"))
                        .build())
                .getList(ExamplesDto.class);
    }

    public boolean insert(ExamplesDto dto) {
        return install(Info.url, Info.id, Info.pw)
                .quary("insert into table values (?,?)")
                .param(statement -> statement.setString(1, dto.getPrimaryKey()))
                .param(statement -> statement.setString(2, dto.getProperty()))
                .set();
    }

    public boolean update(ExamplesDto dto, final String property, final String value) {
        return install(Info.url, Info.id, Info.pw)
                .quary("update table set " + property + " = ?" + "where  primaryKey = ?")
                .param(statement -> statement.setString(1, value))
                .param(statement -> statement.setString(2, dto.getPrimaryKey()))
                .set();
    }

    public boolean delete(ExamplesDto dto) {
        return install(Info.url, Info.id, Info.pw)
                .quary("delete from table where + ?")
                .param(statement -> statement.setString(1, dto.getPrimaryKey()))
                .set();
    }
}

@Builder @Data
class ExamplesDto {
    private String primaryKey;
    private String property;
}

class Info {
    static final String url = "yourDatabaseUrl";
    static final String id = "yourDatabaseId";
    static final String pw = "yourDatabasePw";
}
