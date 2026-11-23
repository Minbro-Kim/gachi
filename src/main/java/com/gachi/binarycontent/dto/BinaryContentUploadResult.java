package com.gachi.binarycontent.dto;

import com.gachi.binarycontent.entity.BinaryContent;

public record BinaryContentUploadResult(
    BinaryContent binaryContent,
    String url
) {
}
