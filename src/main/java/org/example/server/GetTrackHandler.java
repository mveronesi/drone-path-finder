package org.example.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.business.Controller;
import org.example.business.point.Point;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GetTrackHandler implements HttpHandler {
    private final Server server;

    public GetTrackHandler(Server server){
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();
        String response;
        if(server.isControllerActive()) {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Double startLatitude = null, startLongitude = null;
                try {
                    startLatitude = Double.parseDouble(exchange.getAttribute("latitude").toString());
                    startLongitude = Double.parseDouble(exchange.getAttribute("longitude").toString());
                } catch (Exception e) {
                    System.out.println("Error fetching parameters");
                }
                if (startLatitude != null && startLongitude != null) {
                    List<Point> track = server.getController().getTrack(startLatitude.doubleValue(), startLongitude.doubleValue());
                    List<String> tmp = new ArrayList<>();
                    for (Point p : track)
                        tmp.add(p.toString());
                    response = tmp.toString();
                } else
                    response = "Error getting track";
            } else
                response = "Only GET requests";
        } else
            response = "I need a perimeter";

        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
