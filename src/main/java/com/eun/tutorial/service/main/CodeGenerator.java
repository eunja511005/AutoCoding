package com.eun.tutorial.service.main;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.eun.tutorial.aspect.annotation.CheckAuthorization;
import com.eun.tutorial.aspect.annotation.CreatePermission;
import com.eun.tutorial.aspect.annotation.SetCreateAndUpdateId;
import com.eun.tutorial.aspect.annotation.SetUserTimeZoneAndFormat;
import com.eun.tutorial.dto.main.AutoCodingDTO;
import com.eun.tutorial.dto.main.Field;
import com.eun.tutorial.util.OracleTypeConverter;
import com.eun.tutorial.util.StringUtils;

@Service
public class CodeGenerator {
	
	String copyWrite = "/**\n"
			+ " * This software is protected by copyright laws and international copyright treaties.\n"
			+ " * The ownership and intellectual property rights of this software belong to the @autoCoding.\n"
			+ " * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited\n"
			+ " * and may result in legal consequences.\n"
			+ " * This software is licensed to the user and must be used in accordance with the terms of the license.\n"
			+ " * Under no circumstances should the source code or design of this software be disclosed or leaked.\n"
			+ " * The @autoCoding shall not be liable for any loss or damages.\n"
			+ " * Please read the license and usage permissions carefully before using.\n"
			+ " */\n\n";
	
	String copyWriteXml = "<!-- \n"
			+ "    This software is protected by copyright laws and international copyright treaties.\n"
			+ "    The ownership and intellectual property rights of this software belong to the @autoCoding.\n"
			+ "    Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited\n"
			+ "    and may result in legal consequences.\n"
			+ "    This software is licensed to the user and must be used in accordance with the terms of the license.\n"
			+ "    Under no circumstances should the source code or design of this software be disclosed or leaked.\n"
			+ "    The @autoCoding shall not be liable for any loss or damages.\n"
			+ "    Please read the license and usage permissions carefully before using.\n"
			+ "-->\n\n";

    public AutoCodingDTO generateDTOClass(List<Field> fields, String subject) {
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
    	String className = capitalizedSubject+"DTO";
    	
        StringBuilder builder = new StringBuilder();
        
        builder.append(copyWrite);
        builder.append("package com.eun.tutorial.dto.main;\n");
        builder.append("\n");
        builder.append("import lombok.AllArgsConstructor;\n");
        builder.append("import lombok.Data;\n");
        builder.append("import lombok.NoArgsConstructor;\n");
        builder.append("\n");
        builder.append("@Data\n");
        builder.append("@NoArgsConstructor\n");
        builder.append("@AllArgsConstructor\n");
        builder.append("public class "+className+" {\n");

        for (Field field : fields) {
            builder.append("\tprivate ")
                   .append(OracleTypeConverter.convertToJavaType(field.getType()).getSimpleName())
                   .append(" ")
                   .append(convertToCamelCase(field.getName()))
                   .append(";\n");
        }
        
        builder.append("\tprivate String id;\n");
        builder.append("\tprivate boolean delYn;\n");
        builder.append("\tprivate String createId;\n");
        builder.append("\tprivate String createDt;\n");
        builder.append("\tprivate String updateId;\n");
        builder.append("\tprivate String updateDt;\n");

        builder.append("}\n");
        
        autoCodingDTO.setSourceName(className+".java");
        autoCodingDTO.setSourceCode(builder.toString());

        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateControllerClass(String subject) {
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
    	String className = capitalizedSubject+"Controller";
    	
        StringBuilder builder = new StringBuilder();
        
        builder.append(copyWrite);
        builder.append("package com.eun.tutorial.controller.main;\n");
        builder.append("\n");
        
        // Generate the imports
        builder.append("import java.util.List;\n");
        builder.append("\n");
        builder.append("import org.springframework.stereotype.Controller;\n");
        builder.append("import org.springframework.web.bind.annotation.DeleteMapping;\n");
        builder.append("import org.springframework.web.bind.annotation.GetMapping;\n");
        builder.append("import org.springframework.web.bind.annotation.PathVariable;\n");
        builder.append("import org.springframework.web.bind.annotation.PostMapping;\n");
        builder.append("import org.springframework.web.bind.annotation.RequestBody;\n");
        builder.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        builder.append("import org.springframework.web.bind.annotation.ResponseBody;\n");
        builder.append("import org.springframework.web.servlet.ModelAndView;\n\n");
        builder.append("import com.eun.tutorial.dto.main.ApiResponse;\n");
        builder.append("import com.eun.tutorial.dto.main.%sDTO;\n");
        builder.append("import com.eun.tutorial.service.main.%sService;\n");
        builder.append("import com.eun.tutorial.util.StringUtils;\n");
        builder.append("\n");
        builder.append("import lombok.AllArgsConstructor;\n");
        builder.append("import lombok.extern.slf4j.Slf4j;\n\n");

        // Generate the class declaration
        builder.append("@Controller\n");
        builder.append("@RequestMapping(\"/%s\")\n");
        builder.append("@Slf4j\n");
        builder.append("@AllArgsConstructor\n");
        builder.append("public class %s {\n\n");

        // Generate the fields
        builder.append("\tprivate final %sService %sService;\n\n");

        // Generate the methods
        builder.append("\t@GetMapping(\"/list\")\n");
        builder.append("\tpublic ModelAndView list() {\n");
        builder.append("\t\tModelAndView modelAndView = new ModelAndView();\n");
        builder.append("\t\tmodelAndView.setViewName(\"jsp/main/content/%s\");\n");
        builder.append("\t\treturn modelAndView;\n");
        builder.append("\t}\n\n");

        builder.append("\t@PostMapping(\"/list\")\n");
        builder.append("\tpublic @ResponseBody List<%sDTO> get%sList() {\n");
        builder.append("\t\treturn %sService.get%sList();\n");
        builder.append("\t}\n\n");

        builder.append("\t@PostMapping(\"/list/{id}\")\n");
        builder.append("\tpublic @ResponseBody ApiResponse list(@PathVariable String id) {\n");
        builder.append("\t\tlog.info(\"Post List by ID : {}\", id);\n");
        builder.append("\t\t%sDTO %sDTOList = %sService.get%sListById(id);\n");
        builder.append("\t\treturn new ApiResponse<>(true, \"Successfully retrieved the %s list.\", %sDTOList);\n");
        builder.append("\t}\n\n");

        builder.append("\t@PostMapping(\"/save\")\n");
        builder.append("\tpublic @ResponseBody ApiResponse save%s(@RequestBody %sDTO %sDTO) {\n");
        builder.append("\t\tif (StringUtils.isBlank(%sDTO.getId())) {\n");
        builder.append("\t\t\t%sService.save%s(%sDTO);\n");
        builder.append("\t\t\treturn new ApiResponse<>(true, \"Success save\", null);\n");
        builder.append("\t\t} else {\n");
        builder.append("\t\t\t%sService.update%s(%sDTO);\n");
        builder.append("\t\t\treturn new ApiResponse<>(true, \"Success update\", null);\n");        
        builder.append("\t\t}\n");
        builder.append("\t}\n\n");

        builder.append("\t@DeleteMapping(\"/{id}\")\n");
        builder.append("\tpublic @ResponseBody ApiResponse delete%s(@PathVariable String id) {\n");
        builder.append("\t\tlog.info(\"Delete by ID : {}\", id);\n");
        builder.append("\t\t%sService.delete%s(id);\n");
        builder.append("\t\treturn new ApiResponse<>(true, \"Successfully deleted the %s data.\", null);\n");
        builder.append("\t}\n\n");

        builder.append("}\n");

        String result = String.format(builder.toString(), 
        		capitalizedSubject, capitalizedSubject, //imports
        		subject, className, //declaration
        		capitalizedSubject, subject, //field
        		subject, //GetMapping
        		capitalizedSubject, capitalizedSubject, subject, capitalizedSubject, //PostMapping
        		capitalizedSubject, subject, subject, capitalizedSubject, subject, subject, //PostMapping 
        		capitalizedSubject, capitalizedSubject, subject, subject, subject, capitalizedSubject, subject, subject, capitalizedSubject, subject,//PostMapping
        		capitalizedSubject, subject, capitalizedSubject, subject); //DeleteMapping
        
        autoCodingDTO.setSourceName(className+".java");
        autoCodingDTO.setSourceCode(result);

        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateServiceClass(String subject) {
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
    	String className = capitalizedSubject+"Service";
    	
        StringBuilder builder = new StringBuilder();
        
        builder.append(copyWrite);
        
        // Generate the package statement
        builder.append("package com.eun.tutorial.service.main;\n\n");

        // Generate the imports
        builder.append("import java.util.List;\n\n");
        builder.append("import com.eun.tutorial.dto.main.%sDTO;\n\n");

        // Generate the interface declaration
        builder.append("public interface %s {\n");

        // Generate the method signatures
        builder.append("\tList<%sDTO> get%sList();\n");
        builder.append("\tint save%s(%sDTO %sDTO);\n");
        builder.append("\tint update%s(%sDTO %sDTO);\n");
        builder.append("\tint delete%s(String id);\n");
        builder.append("\t%sDTO get%sListById(String id);\n");

        builder.append("}");
        
        String result = String.format(builder.toString(), 
        		capitalizedSubject, className, 
        		capitalizedSubject, capitalizedSubject, 
        		capitalizedSubject, capitalizedSubject, subject,
        		capitalizedSubject, capitalizedSubject, subject,
        		capitalizedSubject, 
        		capitalizedSubject, capitalizedSubject);
        
        autoCodingDTO.setSourceName(className+".java");
        autoCodingDTO.setSourceCode(result);

        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateServiceImplClass(String subject) {
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
    	String className = capitalizedSubject+"ServiceImpl";
    	
        StringBuilder builder = new StringBuilder();
        
        builder.append(copyWrite);
        
        // Generate the package statement
        builder.append("package com.eun.tutorial.service.main;\n\n");

        // Generate the imports
        builder.append("import java.util.List;\n");
        builder.append("import java.util.UUID;\n\n");
        builder.append("import org.springframework.stereotype.Service;\n\n");
        
        builder.append("import com.eun.tutorial.aspect.annotation.CheckAuthorization;\n");
        builder.append("import com.eun.tutorial.aspect.annotation.CreatePermission;\n");
        builder.append("import com.eun.tutorial.aspect.annotation.SetUserTimeZoneAndFormat;\n");
        builder.append("import com.eun.tutorial.aspect.annotation.SetCreateAndUpdateId;\n");
        builder.append("import com.eun.tutorial.dto.main.%sDTO;\n");
        builder.append("import com.eun.tutorial.mapper.main.%sMapper;\n\n");
        builder.append("import com.eun.tutorial.util.StringUtils;\n\n");
        builder.append("import lombok.AllArgsConstructor;\n\n");

        // Generate the class declaration
        builder.append("@Service\n");
        builder.append("@AllArgsConstructor\n");
        builder.append("public class %s implements %sService {\n\n");

        // Generate the fields
        builder.append("\tprivate final %sMapper %sMapper;\n\n");

        // Generate the method implementations
        builder.append("\t@Override\n");
        builder.append("\t@SetUserTimeZoneAndFormat\n");
        builder.append("\tpublic List<%sDTO> get%sList() {\n");
        builder.append("\t\treturn %sMapper.select%sList();\n");
        builder.append("\t}\n\n");
        builder.append("\t@Override\n");
        builder.append("\t@SetCreateAndUpdateId\n");
        builder.append("\t@CreatePermission\n");
        builder.append("\tpublic int save%s(%sDTO %sDTO) {\n");
        builder.append("\t\t%sDTO.setId(\"%s_\"+UUID.randomUUID());\n");
        builder.append("\t\treturn %sMapper.insert%s(%sDTO);\n");
        builder.append("\t}\n\n");
        builder.append("\t@Override\n");
        builder.append("\t@SetCreateAndUpdateId\n");
        builder.append("\t@CheckAuthorization\n");
        builder.append("\tpublic int update%s(%sDTO %sDTO) {\n");
        builder.append("\t\treturn %sMapper.update%s(%sDTO);\n");
        builder.append("\t}\n\n");
        builder.append("\t@Override\n");
        builder.append("\t@CheckAuthorization\n");
        builder.append("\tpublic int delete%s(String id) {\n");
        builder.append("\t\treturn %sMapper.delete%s(id);\n");
        builder.append("\t}\n\n");
        builder.append("\t@Override\n");
        builder.append("\t@CreatePermission\n");
        builder.append("\tpublic %sDTO get%sListById(String id) {\n");
        builder.append("\t\treturn %sMapper.get%sListById(id);\n");
        builder.append("\t}\n\n");

        builder.append("}");

        String result =  String.format(builder.toString(), 
        		capitalizedSubject, capitalizedSubject, //import
        		className, capitalizedSubject, //class 
        		capitalizedSubject, subject, //injection
        		capitalizedSubject, capitalizedSubject, subject, capitalizedSubject, //get
        		capitalizedSubject, capitalizedSubject, subject, subject, subject, subject, capitalizedSubject, subject, //save
        		capitalizedSubject, capitalizedSubject, subject, subject, capitalizedSubject, subject, //update
        		capitalizedSubject, subject, capitalizedSubject, //delete
        		capitalizedSubject, capitalizedSubject, subject, capitalizedSubject); //getById
        
        autoCodingDTO.setSourceName(className+".java");
        autoCodingDTO.setSourceCode(result);

        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateMapperClass(String subject) {
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
    	String className = capitalizedSubject+"Mapper";
    	
        StringBuilder builder = new StringBuilder();
        
        builder.append(copyWrite);
        
        // Generate the package statement
        builder.append("package com.eun.tutorial.mapper.main;\n\n");

        // Generate the imports
        builder.append("import java.util.List;\n\n");
        builder.append("import org.apache.ibatis.annotations.Mapper;\n\n");
        builder.append("import com.eun.tutorial.dto.main.%sDTO;\n\n");

        // Generate the interface declaration
        builder.append("@Mapper\n");
        builder.append("public interface %s {\n");

        // Generate the method declarations
        builder.append("\tList<%sDTO> select%sList();\n");
        builder.append("\tint insert%s(%sDTO %sDTO);\n");
        builder.append("\tint update%s(%sDTO %sDTO);\n");
        builder.append("\tint delete%s(String id);\n");
        builder.append("\t%sDTO get%sListById(String id);\n");
        builder.append("}");

        String result = String.format(builder.toString(), capitalizedSubject, className, 
        		capitalizedSubject, capitalizedSubject, capitalizedSubject, capitalizedSubject, subject,
        		capitalizedSubject, capitalizedSubject, subject,
        		capitalizedSubject, 
        		capitalizedSubject, capitalizedSubject, capitalizedSubject, capitalizedSubject);

        autoCodingDTO.setSourceName(className+".java");
        autoCodingDTO.setSourceCode(result);

        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateMapperXml(List<Field> fields, String subject) {
        if (subject == null || subject.isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or empty.");
        }
        
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
    	String className = capitalizedSubject+"Mapper";
        String namespace = "com.eun.tutorial.mapper.main." + capitalizedSubject + "Mapper";
        String tableName = "ZTHH_"+subject.toUpperCase();
        String selectFieldNames = getSelectFieldNames(fields);
        String insertFieldNames = getInsertFieldNames(fields);
        String wrappedFieldNames = getWrappedFieldNames(fields);
        String formattedFieldNames = getFormattedFieldNames(fields);
        
        StringBuilder content = new StringBuilder();
        content.append(copyWriteXml);
        content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        content.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        content.append("<mapper namespace=\"%s\">\n\n");
        
        content.append("\t<select id=\"select%sList\" resultType=\"com.eun.tutorial.dto.main.%sDTO\">\n");
        content.append("\t\tSELECT %s FROM %s where del_yn='N' ORDER BY update_dt, create_dt\n");
        content.append("\t</select>\n\n");
        
        content.append("\t<insert id=\"insert%s\">\n");
        content.append("\t\tINSERT INTO %s (%s)\n");
        content.append("\t\tVALUES (%s)\n");
        content.append("\t</insert>\n\n");
        
        content.append("\t<update id=\"update%s\">\n");
        content.append("\t\tUPDATE %s\n");
        content.append("\t\tSET %s\n");
        content.append("\t\tWHERE id = #{id}\n");
        content.append("\t</update>\n\n");
        
        content.append("\t<delete id=\"delete%s\">\n");
        content.append("\t\tUPDATE %s SET del_yn = 'Y', update_dt = CURRENT_TIMESTAMP WHERE id = #{id}\n");
        content.append("\t</delete>\n\n");
        
        content.append("\t<select id=\"get%sListById\" parameterType=\"string\" resultType=\"com.eun.tutorial.dto.main.%sDTO\">\n");
        content.append("\t\tSELECT %s FROM %s\n");
        content.append("\t\tWHERE id = #{id}\n");
        content.append("\t\tORDER BY update_dt, create_dt\n");
        content.append("\t</select>\n\n");
        content.append("</mapper>");
        
        String result = String.format(content.toString(), 
        		namespace, 
        		capitalizedSubject, capitalizedSubject, selectFieldNames, tableName, //select
        		capitalizedSubject, tableName, insertFieldNames, wrappedFieldNames, //insert
        		capitalizedSubject, tableName, formattedFieldNames,//update
        		capitalizedSubject, tableName, //delete
        		capitalizedSubject, capitalizedSubject, selectFieldNames, tableName); //selectById

        autoCodingDTO.setSourceName(className+".xml");
        autoCodingDTO.setSourceCode(result);
        
        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateSchemaSql(List<Field> fields, String subject) {
        if (subject == null || subject.isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or empty.");
        }
        
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
        
        StringBuilder content = new StringBuilder();
        content.append(copyWriteXml);
        content.append("DROP TABLE zthh_"+subject+";\n");
        content.append("CREATE TABLE zthh_"+subject+" (\n");
        content.append("\tid VARCHAR2(255),\n");
        
        
        for (Field field : fields) {
            String fieldName = field.getName();
            String fiedlType = field.getType();
            if (fiedlType.equals("VARCHAR2")) {
            	fiedlType = "VARCHAR2(255)";
			}
            content.append("\t");
            content.append(fieldName);
            content.append(" ");
            content.append(fiedlType);
            content.append(",\n");
        }
        
        
        content.append("\tdel_yn CHAR(1),\n");
        content.append("\tcreate_id VARCHAR2(50),\n");
        content.append("\tcreate_dt TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,\n");
        content.append("\tupdate_id VARCHAR2(50),\n");
        content.append("\tupdate_dt TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,\n");
        
        content.append("\tCONSTRAINT pk_zthh_"+subject+" PRIMARY KEY (id)\n");
        
        content.append(");");
        
        autoCodingDTO.setSourceName("schema.sql");
        autoCodingDTO.setSourceCode(content.toString());
        
        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateJsp(List<Field> fields, String subject) {
    	if (subject == null || subject.isEmpty()) {
    		throw new IllegalArgumentException("Subject cannot be null or empty.");
    	}
    	
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
        String formFields = getFormFields(fields);
        String tableheader = getTableHeader(fields);
        
        StringBuilder content = new StringBuilder();
        content.append(copyWriteXml);
        content.append("<div class=\"container-fluid px-4\">\n");
        content.append("\t<div class=\"d-flex justify-content-between align-items-center mb-4\">\n");
        content.append("\t\t<h1 class=\"mt-4\">%s</h1>\n");
        content.append("\t</div>\n");
        content.append("\n");
        
        content.append("\t<ol class=\"breadcrumb mb-4\">\n");
        content.append("\t\t<li class=\"breadcrumb-item\"><a href=\"index.html\">Dashboard</a></li>\n");
        content.append("\t\t<li class=\"breadcrumb-item active\">%s</li>\n");
        content.append("\t</ol>\n");
        content.append("\n");
        
        content.append("\t<div class=\"card mb-4\">\n");
        content.append("\t\t<div class=\"card-header\" id=\"fieldHeading\">\n");
        content.append("\t\t\t<h1 class=\"mb-0\">\n");
        content.append("\t\t\t\t<button id=\"newField\" class=\"btn btn-link\" type=\"button\" aria-expanded=\"true\" aria-controls=\"fieldCollapse\">\n");
        content.append("\t\t\t\t\tNew %s\n");
        content.append("\t\t\t\t</button>\n");
        content.append("\t\t\t</h1>\n");
        content.append("\t\t</div>\n");
        content.append("\t\t<div id=\"fieldCollapse\" class=\"collapse show\" aria-labelledby=\"fieldHeading\">\n");
        content.append("\t\t\t<div class=\"card-body\">\n");
        content.append("\t\t\t\t<div class=\"container-fluid\">\n");
        content.append("\t\t\t\t\t<div class=\"row\">\n");
        content.append("\t\t\t\t\t\t<div class=\"col-12\">\n");
        content.append("\t\t\t\t\t\t\t<form id=\"%sForm\" class=\"row g-2 align-items-center\">\n");
        content.append("\t\t\t\t\t\t\t\t<input type=\"hidden\" id=\"id\" name=\"id\">\n");
        content.append(formFields);
      	content.append("\t\t\t\t\t\t\t\t<div class=\"col-md text-center\">\n");
      	content.append("\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-outline-primary\"><i class=\"fas fa-save\"></i></button>\n");
      	content.append("\t\t\t\t\t\t\t\t\t<button type=\"reset\" class=\"btn btn-outline-secondary\" id=\"clear-btn\"><i class=\"fas fa-undo\"></i></button>\n");
      	content.append("\t\t\t\t\t\t\t\t</div>\n");
        content.append("\t\t\t\t\t\t\t</form>\n");
        content.append("\t\t\t\t\t\t</div>\n");
        content.append("\t\t\t\t\t</div>\n");
        content.append("\t\t\t\t</div>\n");
        content.append("\t\t\t</div>\n");
        content.append("\t\t</div>\n");
        content.append("\t</div>\n");
        content.append("\n");
        
        content.append("\t<div class=\"card mb-4\" style=\"width:100%%\">\n");
        content.append("\t\t<div class=\"card-header\" id=\"fieldHeading\">\n");
        content.append("\t\t\t<h1 class=\"mb-0\">\n");
        content.append("\t\t\t\t<button id=\"searchField\" class=\"btn btn-link\" type=\"button\" aria-expanded=\"true\" aria-controls=\"fieldCollapse\">\n");
        content.append("\t\t\t\t\tSearch %s\n");
        content.append("\t\t\t\t</button>\n");
        content.append("\t\t\t</h1>\n");
        content.append("\t\t</div>\n");
        content.append("\t\t<div id=\"fieldCollapse\" class=\"collapse show\" aria-labelledby=\"fieldHeading\">\n");
        content.append("\t\t\t<div class=\"card-body\" style=\"width:100%%\">\n");
        content.append("\t\t\t\t<table id=\"%sTable\" class=\"table table-hover\" style=\"width:100%%\">\n");
        content.append("\t\t\t\t\t<thead>\n");
        content.append("\t\t\t\t\t\t<tr>\n");
        content.append(tableheader);
        content.append("\t\t\t\t\t\t</tr>\n");
        content.append("\t\t\t\t\t</thead>\n");
        content.append("\t\t\t\t\t<tbody>\n");
        content.append("\t\t\t\t\t</tbody>\n");
        content.append("\t\t\t\t</table>\n");
        content.append("\t\t\t</div>\n");
        content.append("\t\t</div>\n");
        content.append("\t</div>\n");
        content.append("\n");
        
        content.append("</div>\n");
        content.append("\n");
        
        content.append("<script src=\"/js/main/content/%s.js\"></script>\n");
        
        String result = String.format(content.toString(), 
        		capitalizedSubject, //h1 title
        		capitalizedSubject, //ol tag
        		capitalizedSubject, //New card-header
        		subject, //New form
        		capitalizedSubject, //table card-header
        		subject, //table id
        		subject); //js file name

        autoCodingDTO.setSourceName(subject+".jsp");
        autoCodingDTO.setSourceCode(result);
        
        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateJs(List<Field> fields, String subject) {
    	if (subject == null || subject.isEmpty()) {
    		throw new IllegalArgumentException("Subject cannot be null or empty.");
    	}
    	
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
        String column = getColumn(fields);
        String editField = getEditField(fields);
        
        StringBuilder content = new StringBuilder();
        content.append(copyWrite);
        content.append("var csrfheader = $(\"meta[name='_csrf_header']\").attr(\"content\");\n");
        content.append("var csrftoken = $(\"meta[name='_csrf']\").attr(\"content\");\n");
        content.append("var table;\n");
        content.append("\n");
        
        content.append("$(document).ready(function() {\n");
        content.append("\tdebugger;\n");
        content.append("\tinitialize%sTable();\n");
        content.append("\n");
        
        content.append("\t$('#%sForm').on('submit', function(event) {\n");
        content.append("\t\tevent.preventDefault();\n");
        content.append("\t\tvar formData = new FormData(this);\n");
        content.append("\t\tsave%s(formData);\n");
        content.append("\t});\n");
        content.append("\n");
        
        content.append("\t$('#clear-btn').on('click', function() {\n");
        content.append("\t\t$('#%sForm input').val('');\n");
        content.append("\t});\n");
        content.append("\n");
        
        content.append("\tfunction handleCollapseClick() {\n");
        content.append("\t\tvar $cardBody = $(this).parents('.card').find('.card-body');\n");
        content.append("\t\t$cardBody.slideToggle();\n");
        content.append("\t}\n");
        content.append("\n");

        content.append("\t$('#newField, #searchField').click(handleCollapseClick);\n");
        content.append("});\n");

        content.append("function initialize%sTable() {\n");
        content.append("\ttable = $('#%sTable').DataTable({\n");
        content.append("\t\tscrollX: true, \n");
        content.append("\t\tajax: {\n");
        content.append("\t\t\turl: '/%s/list',\n");
        content.append("\t\t\t\"type\": \"POST\",\n");
        content.append("\t\t\tbeforeSend : function(xhr){\n");
        content.append("\t\t\t\txhr.setRequestHeader(csrfheader, csrftoken);\n");
        content.append("\t\t\t},\n");
        content.append("\t\t\tdataSrc: '',\n");
        content.append("\t\t},\n");
        content.append("\t\tcolumns: [\n");
        content.append(column);
        content.append("\t\t\t{\n");
        content.append("\t\t\t\tdata: null,\n");
        content.append("\t\t\t\trender: function(data, type, row, meta) {\n");
        content.append("\t\t\t\t\tvar editButton = '<button type=\"button\" class=\"btn btn-sm btn-outline-info mx-1 edit-button\" data-id=\"' + row.id + '\"><i class=\"fas fa-edit\"></i></button>';\n");
        content.append("\t\t\t\t\treturn editButton;\n");
        content.append("\t\t\t\t},\n");
        content.append("\t\t\t},\n");
        content.append("\t\t\t{\n");
        content.append("\t\t\t\tdata: null,\n");
        content.append("\t\t\t\trender: function(data, type, row, meta) {\n");
        content.append("\t\t\t\t\tvar deleteButton = '<button type=\"button\" class=\"btn btn-sm btn-outline-danger mx-1 delete-button\" data-id=\"' + row.id + '\"><i class=\"fas fa-trash\"></i></button>';\n");
        content.append("\t\t\t\t\treturn deleteButton;\n");
        content.append("\t\t\t\t},\n");
        content.append("\t\t\t},\n");
        content.append("\t\t],\n");
        content.append("\t\torder: [], // 자동 정렬 비활성화\n");
        content.append("\t});\n");
        content.append("\n");
        
        content.append("\t$('#%sTable tbody').on('click', '.edit-button', function() {\n");
        content.append("\t\tvar id = $(this).data('id');\n");
        content.append("\t\tedit%s(id);\n");
        content.append("\t});\n");
        content.append("\n");
        
        content.append("\t$('#%sTable tbody').on('click', '.delete-button', function() {\n");
        content.append("\t\tvar id = $(this).data('id');\n");
        content.append("\t\tdelete%s(id);\n");
        content.append("\t});\n");
        content.append("\n");
        
        content.append("\t$('#%sTable tbody').on('click', 'tr', function() {\n");
        content.append("\t\t$(this).toggleClass('selected');\n");
        content.append("\t});\n");
        content.append("\n");
        
        content.append("}\n");
        content.append("\n");
        
        
        content.append("function save%s(formData) {\n");
        content.append("\tcallAjax(\"/%s/save\", \"POST\", formData, saveCallback);\n");
        content.append("}\n");
        content.append("\n");
        
        content.append("function edit%s(id) {\n");
        content.append("\tcallAjax(\"/%s/list/\"+ id, \"POST\", null, editCallback);\n");
        content.append("}\n");
        content.append("\n");
        
        content.append("function delete%s(id) {\n");
        content.append("\tif (confirm('Are you sure you want to delete?')) {\n");
        content.append("\t\tcallAjax(\"/%s/\"+ id, \"DELETE\", null, deleteCallback);\n");
        content.append("\t}\n");
        content.append("}\n");
        content.append("\n");
        
        content.append("function editCallback(response){\n");
        content.append(editField);
        content.append("\n");
        content.append("\t$('#%sForm').attr('data-mode', 'edit');\n");
        content.append("}\n");
        content.append("\n");
        
        content.append("function saveCallback(response){\n");
        content.append("\t$('#%sForm input').val('');\n");
        content.append("\t$('#%sTable').DataTable().ajax.reload();\n");
        content.append("}\n");
        content.append("\n");
        
        content.append("function deleteCallback(response){\n");
        content.append("\t$('#%sTable tbody').find('tr[data-id=\"' + id + '\"]').remove();\n");
        content.append("\tswal({\n");
        content.append("\t\ttitle: \"Success\",\n");
        content.append("\t\ttext: \"%s deleted successfully.\",\n");
        content.append("\t\ticon: \"success\",\n");
        content.append("\t\tbutton: \"OK\",\n");
        content.append("\t})\n");
        content.append("\t$('#%sTable').DataTable().ajax.reload();\n");
        content.append("}\n");
        content.append("\n");
        
        String result = String.format(content.toString(), 
        		capitalizedSubject, //initialize table
        		subject, capitalizedSubject, // new form submit event add
        		subject, //clear event add
        		capitalizedSubject, subject, subject, // initialize function
        		subject, capitalizedSubject, //Edit event
        		subject, capitalizedSubject, //Delete event
        		subject, //Toggle event
        		capitalizedSubject, subject, //Save function
        		capitalizedSubject, subject, //Edit function
        		capitalizedSubject, subject, //Delete function
        		subject, //edit callback function
        		subject, subject,//save callback function
        		subject, subject, subject); //delete callback function

        autoCodingDTO.setSourceName(subject+".js");
        autoCodingDTO.setSourceCode(result);
        
        return autoCodingDTO;
    }
    
    private String getEditField(List<Field> fields) {
    	StringBuilder result = new StringBuilder();
    	result.append("\t$('#id').val(response.data.id);\n");
    	for (Field field : fields) {
    		result.append("\t$('#"+convertToCamelCase(field.getName())+"').val(response.data."+convertToCamelCase(field.getName())+");\n");
    	}
		return result.toString();
	}

	private String getColumn(List<Field> fields) {
    	StringBuilder result = new StringBuilder();
    	for (Field field : fields) {
    		result.append("\t\t\t{ data: '"+convertToCamelCase(field.getName())+"' },\n");
    	}
		return result.toString();
	}

	private String getTableHeader(List<Field> fields) {
    	StringBuilder result = new StringBuilder();
    	for (Field field : fields) {
    		result.append("\t\t\t\t\t\t\t<th>"+field.getName()+"</th>\n");
    	}
    	result.append("\t\t\t\t\t\t\t<th>Edit</th>\n");
    	result.append("\t\t\t\t\t\t\t<th>Delete</th>\n");
        	
		return result.toString();
	}

	private String getFormFields(List<Field> fields) {
    	StringBuilder result = new StringBuilder();
      
    	String input = "";
    	for (Field field : fields) {
    		String fieldName = convertToCamelCase(field.getName());
    		String fieldType = field.getType();
    		OracleTypeConverter.convertToJavaType(field.getType()).getSimpleName();
    		
    		StringBuilder sb = new StringBuilder();
        	sb.append("\t\t\t\t\t\t\t\t<div class=\"col-md\">\n");
        	sb.append("\t\t\t\t\t\t\t\t\t<div class=\"form-floating\">\n");
        	
        	if(fieldType.equalsIgnoreCase("CHAR")) {
        		sb.append("\t\t\t\t\t\t\t\t\t\t<select class=\"form-select\" id=\"%s\" name=\"%s\" required>\n");
        		sb.append("\t\t\t\t\t\t\t\t\t\t\t<option value=\"Y\">Yes</option>\n");
        		sb.append("\t\t\t\t\t\t\t\t\t\t\t<option value=\"N\">No</option>\n");
        		sb.append("\t\t\t\t\t\t\t\t\t\t</select>\n");
        	}else if(fieldType.equalsIgnoreCase("NUMBER")) {
        		sb.append("\t\t\t\t\t\t\t\t\t\t<input type=\"number\" class=\"form-control\" id=\"%s\" name=\"%s\" placeholder=\"Enter %s\" required>\n");
        	}else if(fieldType.equalsIgnoreCase("DATE") || field.getType().equalsIgnoreCase("TIMESTAMP")) {
        		sb.append("\t\t\t\t\t\t\t\t\t\t<input type=\"date\" class=\"form-control\" id=\"%s\" name=\"%s\" placeholder=\"Enter %s\" required>\n");
        	}else {
        		sb.append("\t\t\t\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"%s\" name=\"%s\" placeholder=\"Enter %s\" required>\n");
        	}
        	sb.append("\t\t\t\t\t\t\t\t\t\t<label for=\"%s\">%s</label>\n");
        	sb.append("\t\t\t\t\t\t\t\t\t</div>\n");
        	sb.append("\t\t\t\t\t\t\t\t</div>\n");
        	
        	input = String.format(sb.toString(), fieldName, fieldName, fieldName
        			, fieldName, field.getName());
        	
        	result.append(input);
		}
    	
		return result.toString();
	}

	public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        char firstChar = Character.toUpperCase(input.charAt(0));
        return firstChar + input.substring(1);
    }
	
    public String getInsertFieldNames(List<Field> fields) {
        StringBuilder fieldNames = new StringBuilder();

        for (Field field : fields) {
            fieldNames.append(field.getName()).append(", ");
        }

        // Remove the trailing comma if there are any fields
        if (fieldNames.length() > 0) {
            fieldNames.setLength(fieldNames.length() - 2);
        }
        
        fieldNames.append(", id, del_yn, create_id, create_dt, update_id, update_dt");

        return fieldNames.toString();
    }
    
    public String getSelectFieldNames(List<Field> fields) {
        StringBuilder fieldNames = new StringBuilder();

        for (Field field : fields) {
        	
        	if(field.getName().endsWith("_dt") || field.getName().endsWith("_time") || field.getName().endsWith("_at")) {
        		fieldNames
        		.append("TO_CHAR(")
        		.append(field.getName())
        		.append(", 'YYYY-MM-DD HH24:MI:SS') AS ")
        		.append(field.getName())
        		.append(", ");
        	}
        	
            fieldNames.append(field.getName()).append(", ");
        }

        // Remove the trailing comma if there are any fields
        if (fieldNames.length() > 0) {
            fieldNames.setLength(fieldNames.length() - 2);
        }
        
        fieldNames.append(", id, del_yn, create_id, TO_CHAR(create_dt, 'YYYY-MM-DD HH24:MI:SS') AS create_dt, update_id, TO_CHAR(update_dt, 'YYYY-MM-DD HH24:MI:SS') AS update_dt");

        return fieldNames.toString();
    }
    
    public String getWrappedFieldNames(List<Field> fields) {
        StringBuilder wrappedFieldNames = new StringBuilder();

        for (Field field : fields) {
            String fieldName = field.getName();
            wrappedFieldNames.append("#{" + convertToCamelCase(fieldName) + "}, ");
        }

        // Remove the trailing comma if there are any fields
        if (wrappedFieldNames.length() > 0) {
            wrappedFieldNames.setLength(wrappedFieldNames.length() - 2);
        }
        
        wrappedFieldNames.append(", #{id}, 'N', #{createId}, CURRENT_TIMESTAMP, #{updateId}, CURRENT_TIMESTAMP");

        return wrappedFieldNames.toString();
    }

    public String getFormattedFieldNames(List<Field> fields) {
        StringBuilder formattedFieldNames = new StringBuilder();

        for (Field field : fields) {
            String fieldName = field.getName();
            formattedFieldNames.append(fieldName).append(" = #{").append(convertToCamelCase(fieldName)).append("}, ");
        }

        // Remove the trailing comma and space if there are any fields
        if (formattedFieldNames.length() > 0) {
            formattedFieldNames.setLength(formattedFieldNames.length() - 2);
        }
        
        formattedFieldNames.append(", update_id = #{updateId}, update_dt = CURRENT_TIMESTAMP");

        return formattedFieldNames.toString();
    }
    
    private String convertToCamelCase(String fieldName) {
        StringBuilder camelCase = new StringBuilder();
        boolean nextUpperCase = false;

        for (int i = 0; i < fieldName.length(); i++) {
            char currentChar = fieldName.charAt(i);

            if (currentChar == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    camelCase.append(Character.toUpperCase(currentChar));
                    nextUpperCase = false;
                } else {
                    camelCase.append(Character.toLowerCase(currentChar));
                }
            }
        }

        return camelCase.toString();
    }

}