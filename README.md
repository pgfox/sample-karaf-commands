WORK IN PROGRESS
================

Some karaf commands that may be helpful


Installing
==========

To build:

mvn clean install

To install in karaf container:

osgi:install -s mvn:com.example.karaf.command/sample-karaf-command


Using:

sample:listbundlesinfeatures - lists all the features installed on the karaf container recursively (to bundle level)


