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
public class ZthmCommonCodeMappingDTO implements Serializable{

	private static final long serialVersionUID = 7306719532165118976L;

    private long codeMappingId;
    private String codeMappingName;
    private String codeMappingDescription;

    private String fromCodeId;
    private String toCodeId;
    
    private boolean isEnable;
    private String createId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date createTime;
    private String updateId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date updateTime;
}
