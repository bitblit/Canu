package com.erigir.canu;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by cweiss on 5/10/16.
 */
public class Canu {
    private static final Logger LOG = LoggerFactory.getLogger(Canu.class);
    private SortedSet<String> to = new TreeSet<>();
    private String from;
    private SortedSet<String> cc = new TreeSet<>();
    private SortedSet<String> bcc = new TreeSet<>();
    private String subject = "Your Canu Email";
    private String html;
    private String txt;
    private Set<File> attachments = new HashSet<>();

    public void sendEmail()
    {
        validate();

        LOG.info("Attaching to SES");
        DefaultAWSCredentialsProviderChain chain = new DefaultAWSCredentialsProviderChain();
        AmazonSimpleEmailService ses = new AmazonSimpleEmailServiceClient(chain);

        Body body = new Body();
        body = (html==null)?body:body.withHtml(new Content(html));
        body = (txt==null)?body:body.withHtml(new Content(txt));

        SendEmailRequest ser = new SendEmailRequest()
                .withSource(from)
                .withDestination(new Destination().withToAddresses(to).withBccAddresses(bcc).withCcAddresses(cc))
                .withMessage(new Message(new Content(subject), body));

        ses.sendEmail(ser);
        LOG.info("Sent");
    }

    public void validate()
    {
        Objects.requireNonNull(from);
        if (to==null || to.size()==0)
        {
            throw new IllegalStateException("TO is null/empty");
        }
        if (html==null && txt==null)
        {
            throw new IllegalStateException("No body is defined");
        }
        if (subject==null)
        {
            throw new IllegalStateException("Null subject");
        }
    }

    public Canu withFrom(final String from) {
        this.from = from;
        return this;
    }

    public Canu withSubject(final String subject) {
        this.subject = subject;
        return this;
    }

    public Canu withHtml(final String html) {
        this.html = html;
        return this;
    }

    public Canu withTxt(final String txt) {
        this.txt = txt;
        return this;
    }

    public SortedSet<String> getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public SortedSet<String> getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Set<File> getAttachments() {
        return attachments;
    }

    public SortedSet<String> getCc() {
        return cc;
    }

    public static SortedSet<String> readStringsFromFile(File f)
    {
        try
        {
            SortedSet<String> rval = new TreeSet<>();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String next = br.readLine();
            while (next!=null)
            {
                rval.add(next);
                next = br.readLine();
            }
            br.close();
            return rval;
        }
        catch (IOException ioe)
        {
            throw new RuntimeException("Error reading file "+f,ioe);
        }
    }

}
