package com.gachi.content.external.dto;

public record ExternalContentImportResult(
    int requestedCount,
    int savedCount,
    int skippedCount,
    int imageSavedCount,
    int imageFailedCount
) {
}
