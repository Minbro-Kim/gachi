package com.gachi.binarycontent.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BinaryContentDeletedEvent {
    private UUID binaryContentId;
    private String storageKey;
}
