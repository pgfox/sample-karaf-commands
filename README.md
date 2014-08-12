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
0: cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
1: cxf >> camel-cxf >> cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
2: cxf >> camel-cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
```

or using the -a  to search all available features

```java
sample:findbundleinfeatures -a mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
Number of Features available: 356
Bundle mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024 found in the following features:
0: servicemix-cxf-se >> cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
1: servicemix-cxf-se >> servicemix-cxf-bc >> cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
2: servicemix-cxf-se >> servicemix-cxf-bc >> fabric-cxf >> cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
3: servicemix-cxf-se >> servicemix-cxf-bc >> fabric-cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
4: servicemix-cxf-se >> servicemix-cxf-bc >> fabric-cxf >> cxf-ws-mex >> cxf-nmr >> cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
5: servicemix-cxf-se >> servicemix-cxf-bc >> fabric-cxf >> cxf-ws-mex >> cxf-nmr >> camel-cxf >> cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
6: servicemix-cxf-se >> servicemix-cxf-bc >> fabric-cxf >> cxf-ws-mex >> cxf-nmr >> camel-cxf >> cxf >> cxf-ws-mex >> mvn:org.apache.cxf/cxf-rt-ws-mex/2.6.0.redhat-60024
```
