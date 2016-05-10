package com.erigir.canu;

import org.apache.commons.cli.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by cweiss on 5/10/16.
 */
public class CanuCLI {
    private static final Logger LOG = LoggerFactory.getLogger(CanuCLI.class);

    public static void main(String[] args) {
        try
        {
            Canu canu = canuFromCommandLineArgs(args);
            canu.sendEmail();
        }
        catch (ParseException pe)
        {
            System.out.println("Usage : ");
            new HelpFormatter().printHelp("ant", buildOptions());
        }
        catch (IOException ioe)
        {
            System.out.println("Error reading file : "+ioe.getMessage());
        }
    }

    public static Canu canuFromCommandLineArgs(String[] args)
            throws ParseException, IOException
    {
        Options options = buildOptions();

        CommandLineParser clp = new DefaultParser();
            CommandLine line = clp.parse(options, args);

            Canu canu = new Canu()
                    .withFrom((String) line.getParsedOptionValue("from"))
                    .withHtml(IOUtils.toString(new FileInputStream((File) line.getParsedOptionValue("html-body-file"))))
                    .withTxt(IOUtils.toString(new FileInputStream((File) line.getParsedOptionValue("text-body-file"))))
                    .withSubject((String) line.getParsedOptionValue("subject"));

            canu.getTo().addAll(Canu.readStringsFromFile((File)line.getParsedOptionValue("to-file")));
            canu.getCc().addAll(Canu.readStringsFromFile((File)line.getParsedOptionValue("cc-file")));
            canu.getBcc().addAll(Canu.readStringsFromFile((File) line.getParsedOptionValue("bcc-file")));
            canu.getAttachments().addAll((List<File>)line.getParsedOptionValue("attachment"));

        return canu;
    }

    public static Options buildOptions()
    {
        Options options = new Options();
        options.addOption(Option.builder("t").required(true).longOpt("to-file").type(File.class).hasArg(true).build());
        options.addOption(Option.builder("f").required(true).longOpt("from").type(String.class).hasArg(true).build());
        options.addOption(Option.builder("c").required(true).longOpt("cc-file").type(File.class).hasArg(true).build());
        options.addOption(Option.builder("b").required(true).longOpt("bcc-file").type(File.class).hasArg(true).build());
        options.addOption(Option.builder("s").required(true).longOpt("subject").type(String.class).hasArg(true).build());
        options.addOption(Option.builder("h").required(true).longOpt("html-body-file").type(File.class).hasArg(true).build());
        options.addOption(Option.builder("x").required(true).longOpt("text-body-file").type(File.class).hasArg(true).build());
        options.addOption(Option.builder("a").required(true).longOpt("attachment").type(File.class).hasArgs().build());
        return options;
    }

}
