package spring.cloudparking.system.database;

import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseTestContainer
{
    static PostgreSQLContainer POSTGRES_SQL_CONTAINER = null;

   /* static {
        System.out.println("initializing Postgres Database Test Container");
        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer("postgres:10=alpine");
        POSTGRES_SQL_CONTAINER.start();
    }
*/
    public static void start()
    {
        if(POSTGRES_SQL_CONTAINER != null)
            return;

        System.out.println("initializing Postgres Database Test Container");
        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer("postgres:10-alpine");
        POSTGRES_SQL_CONTAINER.start();

        System.setProperty("spring.datasource.url", POSTGRES_SQL_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRES_SQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRES_SQL_CONTAINER.getPassword());
    }
}
