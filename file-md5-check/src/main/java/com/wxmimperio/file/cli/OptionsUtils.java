package com.wxmimperio.file.cli;

import org.apache.commons.cli.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiximing.imperio on 2017/2/16.
 */
public class OptionsUtils {

    public static Map<String, String[]> getOptions(String... args) {
        Map<String, String[]> path = new ConcurrentHashMap<>();

        Options options = new Options();
        options.addOption(new Option("in", "inPath", true, "Input Files Path, Split by comma"));
        options.addOption(new Option("out", "outPath", true, "Output Files Path, Split by comma"));
        options.addOption(new Option("h", "help", false, "help"));

        // print usage
        HelpFormatter formatter = new HelpFormatter();

        CommandLine commandLine;
        CommandLineParser parser = new DefaultParser();

        try {
            commandLine = parser.parse(options, args);
            if (args.length == 0 || commandLine.hasOption('h')) {
                printHelp(formatter, options);
            } else {
                System.out.println("--------------------------------------");
                Option[] opts = commandLine.getOptions();
                if (opts != null) {
                    for (Option opt1 : opts) {
                        String name = opt1.getLongOpt();
                        if (name.equals("inPath")) {
                            String value = commandLine.getOptionValue(name);
                            path.put("inputPath", value.split(","));
                        }
                        if (name.equals("outPath")) {
                            String value = commandLine.getOptionValue(name);
                            path.put("outputPath", value.split(","));
                        }
                    }
                }
            }
        } catch (ParseException e) {
            printHelp(formatter, options);
        }
        return path;
    }

    private static void printHelp(HelpFormatter formatter, Options options) {
        formatter.printHelp("OptsCommonsCLI", options, true);
        formatter.setWidth(110);
    }
}
