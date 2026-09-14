package com.aviator.content_service.utility;


public class SlugUtility {
    // Private constructor prevents people from creating objects of this class
    private SlugUtility() {
        throw new UnsupportedOperationException("Slug Utility class");
    }

    public static String generateSlug(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        return input.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "") // Remove special characters
                .replaceAll("\\s+", "-");        // Replace spaces with hyphens
    }
}
