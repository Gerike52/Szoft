package Game.AdatB;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.File;
import java.util.List;

public class Jdbi {

    public static void insert(Result result) {
        org.jdbi.v3.core.Jdbi jdbi = org.jdbi.v3.core.Jdbi.create("jdbc:h2:file:" + System.getProperty("user.home") + File.separator + "ScoreMenu");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            ResultDAO dao = handle.attach(ResultDAO.class);
            dao.create();
            dao.addResult(result);
        }
    }

    public static List<Result> lister() {
        org.jdbi.v3.core.Jdbi jdbi = org.jdbi.v3.core.Jdbi.create("jdbc:h2:file:" + System.getProperty("user.home") + File.separator + "ScoreMenu");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            ResultDAO dao = handle.attach(ResultDAO.class);
            dao.create();
            return dao.listResult();
        }

    }
}

