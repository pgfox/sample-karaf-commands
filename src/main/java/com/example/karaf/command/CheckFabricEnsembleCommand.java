package com.example.karaf.command;

import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Enumeration;


@Command(scope = "sample", name = "checkFabricEnsemble", description = "list all features installed recursively (to bundle level)")
public class CheckFabricEnsembleCommand extends OsgiCommandSupport {

    public static final String IO_FABRIC8_ZOOKEEPER = "io.fabric8.zookeeper";
    public static final String ZOOKEEPER_URL = "zookeeper.url";
    @Option(name = "-v", aliases = "-verbose", description = "Shows detailed output", required = false, multiValued = false)
    boolean verbose = false;

    @Option(name = "-s", aliases = "-stat", description = "invoke zookeeper 'stat' command", required = false, multiValued = false)
    private boolean stat = false;

    @Option(name = "-e", aliases = "-envi", description = "invoke zookeeper 'envi' command", required = false, multiValued = false)
    private boolean envi = false;

    protected Object doExecute() throws Exception {
        // Get config admin service.
        ConfigurationAdmin admin = getConfigurationAdmin();
        if (admin == null) {
            System.out.println("ConfigurationAdmin service is unavailable.");
            return null;
        }

        doExecute(admin);
        return null;
    }

    private void doExecute(ConfigurationAdmin admin) throws Exception {


        String[] urls = getZooKeeperURLs(admin);


        for (String url : urls) {

            System.out.println("CHECKING...:" + url);

            try {
                issueCommand(url, "ruok");
            } catch (Exception ex) {
                System.out.println("ruok" + " command threw exception: " + ex.getMessage());
                continue;
            }

            if (stat) {
                try {
                    issueCommand(url, "stat");
                } catch (Exception ex) {
                    System.out.println("stat" + " command threw exception: " + ex.getMessage());
                    continue;
                }
            }

            if (envi) {
                try {
                    issueCommand(url, "envi");
                } catch (Exception ex) {
                    System.out.println("envi" + " command threw exception: " + ex.getMessage());
                    continue;
                }
            }
            System.out.println("FINISHED CHECK.");

        }


    }

    private String[] getZooKeeperURLs(ConfigurationAdmin admin) throws IOException {
        // get zookeeper URL
        Configuration configuration = admin.getConfiguration(IO_FABRIC8_ZOOKEEPER);
        Dictionary dictionary = configuration.getProperties();


        if (verbose) {
            System.out.println("Contents of PID " + IO_FABRIC8_ZOOKEEPER);

            for (Enumeration e = dictionary.keys(); e.hasMoreElements(); ) {
                Object key = e.nextElement();
                System.out.println("   " + key + " = " + dictionary.get(key));
            }
        }


        String zookeeperURL = (String) dictionary.get(ZOOKEEPER_URL);
        return zookeeperURL.split(",");
    }


    private ConfigurationAdmin getConfigurationAdmin() {
        return getService(ConfigurationAdmin.class);
    }


    //simple tcp commands

    private void issueCommand(String url, String cmd) throws Exception {

        String[] split = url.split(":");

        String host = split[0];
        String port = split[1];

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(host, Integer.parseInt(port));

            out = new PrintWriter(socket.getOutputStream(),
                    true);

            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            System.out.println("SEND: " + cmd);
            out.write(cmd);
            out.flush();

            String line = "";

            while (line != null) {
                line = in.readLine();

                if (line != null) {
                    System.out.println("RESPONSE: " + line);
                }
            }

        } finally {

            if (out != null) {
                out.close();
            }

            if (in != null) {
                in.close();
            }

            if (in != null) {
                socket.close();
            }

        }
    }
}