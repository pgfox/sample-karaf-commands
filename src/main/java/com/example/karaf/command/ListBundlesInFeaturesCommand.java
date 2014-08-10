package com.example.karaf.command;

import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Argument;
import org.apache.karaf.features.BundleInfo;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.ServiceReference;


@Command(scope = "sample", name = "listBundlesInFeatures", description = "list all features installed recursively (to bundle level)")
public class ListBundlesInFeaturesCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "featureName", description = "single feature name", required = false, multiValued = false)
    String featureName = null;


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

        Feature[] features = featuresService.listInstalledFeatures();
        System.out.println("Number of Features installed: " + features.length);

        if (featureName == null) {
            printFeatures("", features, featuresService);
        } else {
            Feature feature = featuresService.getFeature(featureName);
            printFeature("", feature, featuresService);
        }


    }

    private void printFeatures(String prefix, Feature[] features, FeaturesService featuresService)
            throws Exception {

        for (Feature feature : features) {

            Feature lookupFeature = featuresService.getFeature(feature.getName());

            printFeature(prefix, lookupFeature, featuresService);

        }

    }

    private void printFeature(String prefix, Feature feature, FeaturesService featuresService)
            throws Exception {

        System.out.println(prefix + "Feature name: " + feature.getName());

        if (feature.getDependencies().size() != 0) {
            Feature[] features = feature.getDependencies().toArray(
                    new Feature[(feature.getDependencies().size())]);
            printFeatures(prefix + "--", features, featuresService);
        }

        printBundles(prefix + "--", feature);


    }

    private void printBundles(String prefix, Feature feature) {

        for (BundleInfo bundleInfo : feature.getBundles()) {
            System.out.println(prefix + "bundle: " + bundleInfo.getLocation());
        }
    }


}