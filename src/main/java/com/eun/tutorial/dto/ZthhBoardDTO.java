package com.eun.tutorial.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZthhBoardDTO implements Serializable{
	private static final long serialVersionUID = -7201529890898899281L;
	
	private String boardId;
    private String title;
    private String content;
    private boolean isSecret;
    private String createId;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date createTime;
    
    private String updateId;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date updateTime;

}
