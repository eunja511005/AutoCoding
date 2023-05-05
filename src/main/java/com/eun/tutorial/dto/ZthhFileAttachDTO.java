package com.eun.tutorial.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZthhFileAttachDTO implements Serializable{
	
	private static final long serialVersionUID = -8091255477949233570L;
	
	private String attachId;
    private String prefix;
    private int sequence;
    private String originalFileName;
    private String fileName;
    private String fileType;
    private long fileSize;
    private String filePath;
    private String createId;
    private String createTime;
    private String updateId;
    private String updateTime;

}
