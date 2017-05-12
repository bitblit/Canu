package com.erigir.canu;

import org.apache.commons.cli.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by cweiss on 5/10/16.
 */
public class CanuCLI {
    private static final Logger LOG = LoggerFactory.getLogger(CanuCLI.class);

    public static void main(String[] args) {
        try {
            Canu canu = canuFromCommandLineArgs(args);
            canu.sendEmail();
        } catch (ParseException pe) {
            new HelpFormatter().printHelp("CanuCLI", buildOptions());
        } catch (IOException ioe) {
            System.out.println("Error reading file : " + ioe.getMessage());
        }
    }

    public static Canu canuFromCommandLineArgs(String[] args)
            throws ParseException, IOException {
        Options options = buildOptions();

        CommandLineParser clp = new DefaultParser();
        CommandLine line = clp.parse(options, args);

        Canu canu = new Canu()
                .withFrom((String) line.getParsedOptionValue("from"))
                .withHtml(safeStringFromFile((File) line.getParsedOptionValue("html-body-file")))
                .withTxt(safeStringFromFile((File) line.getParsedOptionValue("text-body-file")))
                .withSubject((String) line.getParsedOptionValue("subject"));

        if (line.hasOption("to")) {
            canu.getTo().addAll(Arrays.asList(line.getOptionValues("to")));
        }
        if (line.hasOption("to-file")) {
            canu.getTo().addAll(Canu.readStringsFromFile((File) line.getParsedOptionValue("to-file")));
        }
        if (line.hasOption("cc-file")) {
            canu.getCc().addAll(Canu.readStringsFromFile((File) line.getParsedOptionValue("cc-file")));
        }
        if (line.hasOption("bcc-file")) {
            canu.getBcc().addAll(Canu.readStringsFromFile((File) line.getParsedOptionValue("bcc-file")));
        }
        if (line.hasOption("subject-file")) {
            canu.setSubject(safeStringFromFile((File)line.getParsedOptionValue("subject-file")));
        }
        if (line.hasOption("attachment")) {
            for (String s : line.getOptionValues("attachment")) {
                canu.getAttachments().add(new File(s));
            }
        }
        if (line.hasOption("text-body"))
        {
            canu.setTxt(line.getOptionValue("text-body"));
        }


        return canu;
    }

    private static String safeStringFromFile(File file)
            throws IOException {
        return (file == null) ? null : IOUtils.toString(new FileInputStream(file));
    }

    public static Options buildOptions() {
        Options options = new Options();
        options.addOption(Option.builder("t").required(false).longOpt("to").type(String.class).hasArgs().desc("Adds a TO address.  You must set at least one TO address, but can add any number").build());
        options.addOption(Option.builder("o").required(false).longOpt("to-file").type(File.class).desc("Path to a file with addresses, one per line, for the TO field.  You must set at least one TO address").hasArg(true).build());
        options.addOption(Option.builder("f").required(true).longOpt("from").type(String.class).desc("Required.  Sets the from address").hasArg(true).build());
        options.addOption(Option.builder("c").required(false).longOpt("cc-file").type(File.class).desc("Path to a file with addresses, one per line, for the CC field").hasArg(true).build());
        options.addOption(Option.builder("b").required(false).longOpt("bcc-file").type(File.class).desc("Path to a file with addresses, one per line, for the BCC field").hasArg(true).build());
        options.addOption(Option.builder("s").required(false).longOpt("subject").type(String.class).desc("Sets the subject line of the email").hasArg(true).build());
        options.addOption(Option.builder("u").required(false).longOpt("subject-file").type(File.class).desc("Sets the subject line of the email to the contents of the supplied file(given preference over --subject if both are set)").hasArg(true).build());
        options.addOption(Option.builder("h").required(false).longOpt("html-body-file").type(File.class).desc("Path to a file containing the HTML body of the file.  You must set at least one of html-body or text-body").hasArg(true).build());
        options.addOption(Option.builder("x").required(false).longOpt("text-body-file").type(File.class).desc("Path to a file containing the text body of the file.  You must set at least one of html-body or text-body").hasArg(true).build());
        options.addOption(Option.builder("e").required(false).longOpt("text-body").type(String.class).desc("String containing the text body for the email.  You must set at least one of html-body or text-body").hasArg(true).build());
        options.addOption(Option.builder("a").required(false).longOpt("attachment").type(File.class).desc("Path to a file to add as an attachment.  You may set as many of these as you like").hasArgs().build());
        return options;
    }

}
