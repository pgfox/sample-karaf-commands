package com.example.karaf.command;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.features.BundleInfo;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.ServiceReference;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Command(scope = "sample", name = "findBundleInFeatures", description = "find which installed feature installs the bundle e.g. indbundleinfeatures mvn:com.example/my-sample/2.6.0.redhat-60024")
public class FindBundleInFeaturesCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "bundleLocation", description = "bundle location (list -l)", required = true, multiValued = false)
    String targetBundle = null;
    private List<String> foundLocationList = new ArrayList<String>();


    @Option(name = "-v", aliases = "-verbose", description = "Shows output from search", required = false, multiValued = false)
    boolean verbose = false;

    @Option(name = "-a", aliases = "-allFeatures", description = "search all available features (not just installed features)", required = false, multiValued = false)
    boolean allFeatures = false;

    protected Object doExecute() throws Exception {
        // Get repository admin service.
        ServiceReference ref = getBundleContext().getServiceReference(FeaturesService.class.getName());
        if (ref == null) {
            System.out.println("FeaturesService service is unavailable.");
            return null;
        }
        try {
            FeaturesService featuresService = (FeaturesService) getBundleContext().getService(ref);
            if (featuresService == null) {
                System.out.println("FeaturesService service is unavailable.");
                return null;
            }

            doExecute(featuresService);
        } finally {
            getBundleContext().ungetService(ref);
        }
        return null;
    }


    protected void doExecute(FeaturesService featuresService) throws Exception {
        try {

            Feature[] features;

            if (allFeatures){
                features = featuresService.listFeatures();
                System.out.println("Number of Features available: " + features.length);
            }else{
                features = featuresService.listInstalledFeatures();
                System.out.println("Number of Features installed: " + features.length);
            }



            LinkedList<String> breadcrumb = new LinkedList<String>();

            //check feature for bundle
            checkFeatures(breadcrumb, "", features, featuresService, targetBundle);

            printlocationList();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    private void checkFeatures(LinkedList<String> breadcrumb, String prefix, Feature[] features, FeaturesService featuresService, String targetBundle)
            throws Exception {

        for (Feature feature : features) {

            Feature lookupFeature = featuresService.getFeature(feature.getName());

            checkFeature(breadcrumb, prefix, lookupFeature, featuresService, targetBundle);

        }

    }

    private void checkFeature(LinkedList<String> breadcrumb, String prefix, Feature feature, FeaturesService featuresService, String targetBundle)
            throws Exception {

        if (verbose) {
            System.out.println(prefix + "Feature name: " + feature.getName());
        }

        if (feature.getDependencies().size() != 0) {
            Feature[] features = feature.getDependencies().toArray(
                    new Feature[(feature.getDependencies().size())]);

            breadcrumb.addLast(feature.getName());
            checkFeatures(breadcrumb, prefix + "--", features, featuresService, targetBundle);
            breadcrumb.removeLast();
        }

        breadcrumb.addLast(feature.getName());
        checkBundles(breadcrumb, prefix + "--", feature, targetBundle);
        breadcrumb.removeLast();

    }

    private void checkBundles(LinkedList<String> breadcrumb, String prefix, Feature feature, String targetBundle) {

        for (BundleInfo bundleInfo : feature.getBundles()) {
            if (bundleInfo.getLocation().contains(targetBundle)) {
                if (verbose) {
                    System.out.println("FOUND Bundle: " + bundleInfo.getLocation());
                }
                breadcrumb.addLast(bundleInfo.getLocation());
                addlocation(breadcrumb);
            }
        }
    }

    private void addlocation(LinkedList<String> breadcrumb) {

        StringBuffer location = new StringBuffer();


        for (int i = 0; i < breadcrumb.size(); i++) {
            location.append(breadcrumb.get(i));

            //do not add the arrows at the end
            if ((i+1)!= breadcrumb.size()){
                location.append(" >> ");
            }

        }
        foundLocationList.add(location.toString());

    }

    private void printlocationList() {

        if (foundLocationList.isEmpty()) {
            System.out.println("Bundle " + targetBundle + " NOT found");
            return;
        }

        System.out.println("Bundle " + targetBundle + " found in the following features:");
        for (int i=0; i<foundLocationList.size(); i++) {
            System.out.println(i+": "+foundLocationList.get(i));
        }

    }


}