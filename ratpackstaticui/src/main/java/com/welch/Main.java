package com.welch;

import ratpack.handling.Context;
import ratpack.http.MediaType;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfigBuilder;
import ratpack.session.SessionModule;

import java.io.File;
import java.sql.Connection;

/**
 * Created by Administrator on 2018-10-27.
 */
public class Main {
    private RatpackServer ratpackServer;
    private static int port = 8888;

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.createRatpackServer();
        main.start();
    }

    private void createRatpackServer(){
        try{
            this.ratpackServer = RatpackServer.of(ratpackServerSpec -> ratpackServerSpec.serverConfig(serverConfigBuilder ->
                    serverConfigBuilder
                            .port(port)
                            .maxHeaderSize(4*8192)
                            .baseDir(BaseDir.find("web"))
            )
                    .registry(ratpack.guice.Guice.registry(b -> b.module(SessionModule.class)))
                    .handlers(chain -> chain
                            .get("health", this::getHealth)
                            .files(fileHandlerSpec -> fileHandlerSpec.dir("web").indexFiles("index.html"))
                    ));
        }catch (Exception e){

        }
    }

    private void start() throws Exception {
        this.ratpackServer.start();
    }

    private void getHealth(Context ctx){
        ctx.getResponse().send(MediaType.APPLICATION_JSON, "{result: 'success'}");
    }
}
