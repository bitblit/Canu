* Canu

A super simple tool for sending messages down the Amazon.

* How it works

This is meant to be executed inside batch files when you have access to an AWS account (and with it, SES) but not easy 
access to an SMTP server.  It is meant to be super simple so:
 
1) I only use the DefaultAWSCredentialsProviderChain.  There are about a thousand ways to configure it to get a key you
need.  I recommend you figure one out and use it.  Learning how to do so is outside the scope of this README.
2) The key retrieved by (1) must have the rights to use SES.  Figure that out too
3) SES must have the right to send to your intended targets.  Figure that out too.  Its probably best to setup DKIM if
you haven't already.

* Usage

** CLI



** As a class

I'm not sure why you would use Canu as a class since it would be just about as easy to just write to SES yourself, but 
if you must:

