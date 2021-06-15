package org.example.business;

import java.util.ArrayList;
import java.util.List;


public class Perimeter {
    private final Point a, b, c, d;

    private Perimeter(double latA, double latB, double latC, double latD,
                    double lonA, double lonB, double lonC, double lonD) {
        this.a = new Point(latA, lonA, -1);
        this.b = new Point(latB, lonB, -1);
        this.c = new Point(latC, lonC, -1);
        this.d = new Point(latD, lonD, -1);
    }

    public static class Factory {
        private final List<Double> latitudes, longitudes;

        private Factory() {
            this.latitudes = new ArrayList<>();
            this.longitudes = new ArrayList<>();
        }
        public void clear() {
            latitudes.clear();
            longitudes.clear();
        }

        public Factory addPoint(double latitude, double longitude) {
            latitudes.add(latitude);
            longitudes.add(longitude);
            return this;
        }

        public Perimeter build() throws IllegalStateException {
        if(latitudes.size() != 4)
                throw new IllegalStateException("Points must be four in a rectangle perimeter");
            Perimeter p = new Perimeter(latitudes.get(0), latitudes.get(1), latitudes.get(2), latitudes.get(3),
                    longitudes.get(0), longitudes.get(1), longitudes.get(2), longitudes.get(3));
            if(!isRectangle(p))
                throw new IllegalStateException("This is not a rectangle");
            clear();
            return p;
        }

        private static boolean isRectangle(Perimeter p) {
            double cLat, cLon, ddA, ddB, ddC, ddD;
            cLat = (p.a.getLatitude() + p.b.getLatitude() + p.c.getLatitude() + p.d.getLatitude()) / 4.0;
            cLon = (p.a.getLongitude() + p.b.getLongitude() + p.c.getLongitude() + p.d.getLongitude()) / 4.0;
            ddA = Math.sqrt(Math.abs(cLat - p.a.getLatitude())) + Math.sqrt(Math.abs(cLon - p.a.getLongitude()));
            ddB = Math.sqrt(Math.abs(cLat - p.b.getLatitude())) + Math.sqrt(Math.abs(cLon - p.b.getLongitude()));
            ddC = Math.sqrt(Math.abs(cLat - p.c.getLatitude())) + Math.sqrt(Math.abs(cLon - p.c.getLongitude()));
            ddD = Math.sqrt(Math.abs(cLat - p.d.getLatitude())) + Math.sqrt(Math.abs(cLon - p.d.getLongitude()));
            return Double.compare(ddA, ddB) == 0 &&
                   Double.compare(ddA, ddC) == 0 &&
                   Double.compare(ddA, ddD) == 0;
        }
    }

    public static Factory getFactory() {
        return new Factory();
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Point getC() {
        return c;
    }

    public Point getD() {
        return d;
    }


}
