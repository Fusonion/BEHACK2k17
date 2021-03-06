package com.example.ivan.toyotaconnected.dao;

/**
 * Used to abstract the construction of Content Providers.
 *
 * @author tejun
 */

public final class ContentProviderFactory {

    private static ContentProvider contentProvider;

    private ContentProviderFactory() {
        // Private constructor to disable object creation
    }

    /**
     * Constructs and returns the default stateless content provider.
     *
     * @return a content provider
     */
    public static synchronized ContentProvider getDefaultContentProvider() {

        // Lazy singleton instantiation
        if (contentProvider == null) {
            // Use the firebase content provider
            contentProvider = new FirebaseContentProvider();
        }

        return contentProvider;
    }
}
