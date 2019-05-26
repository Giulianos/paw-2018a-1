package ar.edu.itba.paw.webapp.utils;

public class URLResolver {

    final static String API_PREFIX = "/api";
    final static String URL = "http://pawserver.it.itba.edu.ar/paw-2018a-1";

    public static String getFullURL(final String resource) {
        return URL + API_PREFIX + resource;
    }
}
