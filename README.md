WORK IN PROGRESS
================

Some karaf commands that may be helpful

**NOTE: the commands have had limited/adhoc testing - NOT recommended for use in production.**

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

- sample:checkFabricEnsemble - checks each ensemble server listed in zookeeper.url using the 'ruok' (by default), 'stat' (-s), 'envi' -e. For JBoss Fuse 6.1 and above ONLY

for eg. 

```java
sample:checkfabricensemble -v -s
Contents of PID io.fabric8.zookeeper
   service.pid = io.fabric8.zookeeper
   zookeeper.url = sideshow.home:2181
   fabric.zookeeper.pid = io.fabric8.zookeeper
CHECKING...:sideshow.home:2181
SEND: ruok
RESPONSE: imok
SEND: stat
RESPONSE: Zookeeper version: 3.4.5-1392090, built on 09/30/2012 17:52 GMT
RESPONSE: Clients:
RESPONSE:  /192.168.1.122:50650[1](queued=0,recved=1642,sent=1651)
RESPONSE:  /192.168.1.122:50692[0](queued=0,recved=1,sent=0)
RESPONSE:  /192.168.1.122:50659[1](queued=0,recved=3122,sent=3131)
RESPONSE:
RESPONSE: Latency min/avg/max: 0/0/1447
RESPONSE: Received: 5455
RESPONSE: Sent: 5478
RESPONSE: Connections: 3
RESPONSE: Outstanding: 0
RESPONSE: Zxid: 0x19a6
RESPONSE: Mode: standalone
RESPONSE: Node count: 157
FINISHED CHECK.
````

