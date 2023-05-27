package Game.AdatB;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(Result.class)
public interface ResultDAO {
    @SqlUpdate("""
            create TABLE if not exists Score(
            jatekosEgy varchar not null,
            jatekosKetto varchar not null,
            nyertes varchar not null)
            """)
    void create();

    @SqlUpdate("insert into Score values(:jatekosEgy,:jatekosKetto,:nyertes)")
    void addResult(@BindBean Result result);

    @SqlQuery("SELECT * FROM Score")
    List<Result> listResult();
}

