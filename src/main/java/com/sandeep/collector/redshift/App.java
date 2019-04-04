package com.sandeep.collector.redshift;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;

public class App {
  public static void main(String[] args) {

    App main = new App();

    try {
      System.out.println("Starting the server on port: 8082");
      main.start(args);
    } catch (Exception e) {
      System.out.println("Error in server start: " + e.getMessage());
    }
  }


  private void start(String[] args) throws Exception {
    try {

      AppController appController = new AppController();

      final Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(100)
          .setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true)));

      vertx.deployVerticle(appController, new DeploymentOptions());
    } catch (Exception e) {
      System.out.println("Error in starting the vertx application: " + e.getMessage());
      throw e;
    }
  }
}
