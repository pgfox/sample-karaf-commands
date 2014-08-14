package com.example.karaf.command;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.features.BundleInfo;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.ServiceReference;

import java.io.BufferedReader;
import java.io.InputStreamReader;


@Command(scope = "sample", name = "lc", description = "line count")
public class LineCountCommand extends OsgiCommandSupport {



    protected Object doExecute() throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        String line;
        int lineCount=0;

        while ((line = r.readLine()) != null) {

            lineCount++;

            if (line.length() == 1 && line.charAt(0) == '\n') {
                break;
            }
        }

        System.out.println(lineCount);

        return null;
    }





}