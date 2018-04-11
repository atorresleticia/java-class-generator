package com.ws.calendar;

import com.ws.commons.server.JettyServer;

public final class Main {

    /**
     *
     * @param args
     * @throws Exception
     */
    private Main(String[] args) throws Exception {
        Main.main(args);
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        JettyServer.start();
    }
}