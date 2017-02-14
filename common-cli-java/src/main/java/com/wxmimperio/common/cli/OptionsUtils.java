package com.wxmimperio.common.cli;

import org.apache.commons.cli.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by weiximing.imperio on 2017/2/14.
 */
public class OptionsUtils {

    public static void getOptions(String... args) {

        Options options = new Options();
        // 第一个参数设定这个 option 的单字符名字
        // 第二个参数设定这个 option 的单字符名字全称
        // 第三个参数指明这个 option 是否需要输入数值
        // 第四个参数是对这个 option 的简要描述
        options.addOption(new Option("h", "help", false, "list help info"));
        options.addOption(new Option("host", "hostname", true, "set host"));
        options.addOption(new Option("p", "port", true, "set post"));
        options.addOption(new Option("d", "date", true, "set date"));


        //-Dkey2=value2 这样的类型
        Option property = Option.builder("D")
                .numberOfArgs(2)
                .valueSeparator()
                .longOpt("property")
                .desc("use value for given property(property=value)")
                .argName("property=value").build();
        property.setRequired(true);
        options.addOption(property);

        // print usage
        HelpFormatter formatter = new HelpFormatter();
        System.out.println();

        CommandLine commandLine = null;
        CommandLineParser parser = new DefaultParser();
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) {
                // 打印使用帮助
                formatter.printHelp("AntOptsCommonsCLI", options, true);
                formatter.setWidth(110);
            }

            // 打印opts的名称和值
            System.out.println("--------------------------------------");
            Option[] opts = commandLine.getOptions();
            if (opts != null) {
                for (Option opt1 : opts) {
                    String name = opt1.getLongOpt();
                    if (name.equals("date")) {
                        DateFormat df3 = new SimpleDateFormat(commandLine.getOptionValue(name));
                        System.out.println(name + "=>" + df3.format(System.currentTimeMillis()));
                    } else {
                        String value = commandLine.getOptionValue(name);
                        System.out.println(name + "=>" + value);
                    }
                }
            }
        } catch (ParseException e) {
            formatter.printHelp("AntOptsCommonsCLI", options, true);
            formatter.setWidth(110);
        }
    }
}
