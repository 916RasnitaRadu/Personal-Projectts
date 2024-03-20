package com.exportApp.mapper;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class FileResponse {
    public String name;
    public String type;
    public String path;
    private byte[] content;
    private Map<String, String> metadata;

}
