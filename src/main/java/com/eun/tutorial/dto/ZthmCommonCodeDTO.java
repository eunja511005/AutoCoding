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
public class ZthmCommonCodeDTO implements Serializable{

	private static final long serialVersionUID = -5704276659291787607L;
	private String id;
    private String codeGroupId;
    private String codeGroupName;
    private String codeGroupDescription;
    private String codeId;
    private int codeLevel;
    private int codeSequence;
    private String codeName;
    private String codeDescription;
    
    private boolean isEnable;
    private String createId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date createTime;
    private String updateId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date updateTime;
}
