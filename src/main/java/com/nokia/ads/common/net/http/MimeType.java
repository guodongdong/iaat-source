package com.nokia.ads.common.net.http;

/**
 * This a list of known Mime types
 */
public enum MimeType {

    htm("text/html"),
    css("text/css"),
    html("text/html"),
    txt("text/plain"),
    asc("text/plain"),
    gif("image/gif"),
    jpg("image/jpeg"),
    jpeg("image/jpeg"),
    png("image/png"),
    pdf("application/pdf"),
    doc("application/msword"),
    zip("application/octet-stream"),
    exe("application/octet-stream"),
    xls("application/vnd.ms-excel");
    
    private String contentType;

    MimeType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Return the Content Type String of this Mime
     * @return the Content Type String of this Mime
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Convert a String value to a MimeType.  Default to txt in case it can not.
     * @param value String to convert
     * @return string value to a MimeType.  Default to txt in case it can not.
     */
    public static MimeType convert(String value) {
        try {
            return value == null || value.trim().length() == 0 ? txt : Enum.valueOf(MimeType.class, value);
        } catch (IllegalArgumentException ex) {
            return txt;
        }

    }
}
