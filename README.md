WORK IN PROGRESS
================

Some karaf commands that may be helpful


Installing
==========

To build:

mvn clean install

To install in karaf container:

osgi:install -s mvn:com.example.karaf.command/sample-karaf-command


Using
=====

- sample:listbundlesinfeatures - lists all the features installed on the karaf container recursively (to bundle level)

- sample:findbundleinfeatures bundlelocation - find the features (defaults to installed on the karaf container) that include specified bundle. Specifing the -a flag searches all features available on the container e.g. 

```java
sample:findbundleinfeatures mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
Number of Features installed: 77
Bundle mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024 found in the following features:
cxf >> cxf-ws-mex
camel-cxf >> cxf >> cxf-ws-mex
cxf-ws-mex
```

or using the -a  to search all available features

```java
sample:findbundleinfeatures -a mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
Number of Features available: 356
Bundle mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024 found in the following features:
servicemix-cxf-se >> cxf >> cxf-ws-mex
servicemix-cxf-bc >> cxf >> cxf-ws-mex
fabric-cxf >> cxf >> cxf-ws-mex
cxf-ws-mex
cxf-nmr >> cxf >> cxf-ws-mex
camel-cxf >> cxf >> cxf-ws-mex
cxf >> cxf-ws-mex
```
