package com.sandeep.collector.redshift;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class AppController extends AbstractVerticle {
  private final String TABLE_NAME = "tableName";

  @Override
  public void start(Future<Void> fut) {

    Router router = Router.router(vertx);

    router.route("/").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily("Hello from my first application"));
    });
    router.get("/api/table/:tableName").handler(this::getTableData);

    vertx.createHttpServer().requestHandler(router::accept).listen(

        config().getInteger("http.port", 8082), result -> {
          if (result.succeeded()) {
            fut.complete();
          } else {
            fut.fail(result.cause());
          }
        });
  }

  private void getTableData(RoutingContext routingContext) {
    try {
      final String tableName = routingContext.request().getParam(TABLE_NAME);
      RedShiftConnection connection = new RedShiftConnection();
      routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(connection.getTableData(tableName)));
    } catch (final IllegalArgumentException e) {
      String error = "EXCEPTION_WHILE_PROCESSING_REQUEST_CAUSE, Error: " + e.getMessage();
      System.out.println(error);
      routingContext.response().setStatusCode(404).end(Json.encodePrettily(error));
    } catch (final Exception e) {
      String error = "ERROR_GETTING_TABLE_DATA, Error: " + e.getMessage();
      System.out.println(error);
      routingContext.response().setStatusCode(404).end(Json.encodePrettily(error));
    }
  }

}
