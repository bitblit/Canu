# Canu

A super simple tool for sending messages down the Amazon.

# How it works

This is meant to be executed inside batch files when you have access to an AWS account (and with it, SES) but not easy 
access to an SMTP server.  It is meant to be super simple so:
 
1. I only use the DefaultAWSCredentialsProviderChain.  There are about a thousand ways to configure it to get a key you
need.  I recommend you figure one out and use it.  Learning how to do so is outside the scope of this README.
2. The key retrieved by (1) must have the rights to use SES.  Figure that out too
3. SES must have the right to send to your intended targets.  Figure that out too.  Its probably best to setup DKIM if
you haven't already.

# Usage

## CLI

Canu builds a stand-alone jar file which can be run like:

*java -jar Canu.jar {options}*

The options are:

```
usage: CanuCLI
 -a,--attachment <arg>       Path to a file to add as an attachment.  You
                             may set as many of these as you like
 -b,--bcc-file <arg>         Path to a file with addresses, one per line,
                             for the BCC field
 -c,--cc-file <arg>          Path to a file with addresses, one per line,
                             for the CC field
 -e,--text-body <arg>        String containing the text body for the
                             email.  You must set at least one of
                             html-body or text-body
 -f,--from <arg>             Required.  Sets the from address
 -h,--html-body-file <arg>   Path to a file containing the HTML body of
                             the file.  You must set at least one of
                             html-body or text-body
 -o,--to-file <arg>          Path to a file with addresses, one per line,
                             for the TO field.  You must set at least one
                             TO address
 -s,--subject <arg>          Sets the subject line of the email
 -t,--to <arg>               Adds a TO address.  You must set at least one
                             TO address, but can add any number
 -x,--text-body-file <arg>   Path to a file containing the text body of
                             the file.  You must set at least one of
                             html-body or text-body
```                             

## As a class

I'm not sure why you would use Canu as a class since it would be just about as easy to just write to SES yourself, but 
if you must:

1. Create an instance of the Canu class
2. You MUST set the from address, at least one TO address, and at least one of the HTML or TXT properties (or both)
3. You SHOULD set a subject line (otherwise itll be "Your Canu Email")
4. You MAY set one or more of the cc or bcc addresses
5. You MAY add one or more files to the attachments list
6. Call canu.sendEmail();

That's it.


