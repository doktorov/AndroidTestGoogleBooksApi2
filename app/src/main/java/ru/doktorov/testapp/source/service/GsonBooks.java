package ru.doktorov.testapp.source.service;

import java.util.List;

public class GsonBooks {
    public String kind;
    public int totalItems;
    public List<Item> items;

    public class Item {
        public String kind;
        public String id;
        public String etag;
        public String selfLink;
        public VolumeInfo volumeInfo;
        public SaleInfo saleInfo;
        public AccessInfo accessInfo;
        public SearchInfo searchInfo;
    }

    public class VolumeInfo {
        public String title;
        public List<String> authors;
        public String publisher;
        public String publishedDate;
        public String description;
        public List<IndustryIdentifiers> industryIdentifiers;
        public ReadingModes readingModes;
        public int pageCount;
        public String printType;
        public List<String> categories;
        public float averageRating;
        public int ratingsCount;
        public String maturityRating;
        public boolean allowAnonLogging;
        public String contentVersion;
        public ImageLinks imageLinks;
        public String language;
        public String previewLink;
        public String infoLink;
        public String canonicalVolumeLink;
    }

    public class SaleInfo {
        public String country;
        public String saleability;
        public boolean isEbook;
    }

    public class AccessInfo {
        public String country;
        public String viewability;
        public boolean embeddable;
        public boolean publicDomain;
        public String textToSpeechPermission;
        public Format epub;
        public Format pdf;
        public String webReaderLink;
        public String accessViewStatus;
        public boolean quoteSharingAllowed;
    }

    public class SearchInfo {
        public String textSnippet;
    }

    public class IndustryIdentifiers {
        public String type;
        public String identifier;
    }

    public class ReadingModes {
        public boolean text;
        public boolean image;
    }

    public class ImageLinks {
        public String smallThumbnail;
        public String thumbnail;
    }

    public class Format {
        public boolean isAvailable;
    }
}
