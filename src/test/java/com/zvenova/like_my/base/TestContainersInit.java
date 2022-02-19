package com.zvenova.like_my.base;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainersInit {

    private static volatile TestContainersInit instance;

    public String url;

    public String username;

    public String password;

    private TestContainersInit() {

        DockerImageName myImage = DockerImageName.parse("postgres:14-alpine3.15")
                .asCompatibleSubstituteFor("postgres");
        PostgreSQLContainer dbContainer = new PostgreSQLContainer(myImage);
        dbContainer.withTmpFs(new HashMap<String, String>() {{
            put("/var/lib/postgresql/data", "rw");
        }});
//        dbContainer.withTmpFs(Map.of("/var/lib/postgresql/data", "rw"));
        dbContainer.withReuse(true);
        dbContainer.start();

        url = dbContainer.getJdbcUrl();
        username = dbContainer.getUsername();
        password = dbContainer.getPassword();

    }

    public static TestContainersInit getInstance() {

        TestContainersInit localInstance = instance;
        if (localInstance == null) {
            synchronized (TestContainersInit.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TestContainersInit();
                }
            }
        }
        return localInstance;
    }
}
