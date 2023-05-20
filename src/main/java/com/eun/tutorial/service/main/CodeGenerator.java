package com.eun.tutorial.service.main;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.main.AutoCodingDTO;
import com.eun.tutorial.dto.main.Field;

@Service
public class CodeGenerator {

    public AutoCodingDTO generateDTOClass(List<Field> fields, String subject) {
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
    	String className = capitalizedSubject+"DTO";
    	
        StringBuilder builder = new StringBuilder();
        
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
                   .append(field.getType().getSimpleName())
                   .append(" ")
                   .append(convertToCamelCase(field.getName()))
                   .append(";\n");
        }
        
        builder.append("\tprivate String id;\n");
        builder.append("\tprivate boolean delyn;\n");
        builder.append("\tprivate String createid;\n");
        builder.append("\tprivate LocalDateTime createdt;\n");
        builder.append("\tprivate String updateid;\n");
        builder.append("\tprivate LocalDateTime updatedt;\n");

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
        
        builder.append("package com.eun.tutorial.controller.main;\n");
        builder.append("\n");
        
        // Generate the imports
        builder.append("import org.springframework.stereotype.Controller;\n");
        builder.append("import org.springframework.web.bind.annotation.DeleteMapping;\n");
        builder.append("import org.springframework.web.bind.annotation.GetMapping;\n");
        builder.append("import org.springframework.web.bind.annotation.PathVariable;\n");
        builder.append("import org.springframework.web.bind.annotation.PostMapping;\n");
        builder.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        builder.append("import org.springframework.web.bind.annotation.ResponseBody;\n");
        builder.append("import org.springframework.web.servlet.ModelAndView;\n\n");
        builder.append("import com.eun.tutorial.dto.main.ApiResponse;\n");
        builder.append("import com.eun.tutorial.dto.main.%sDTO;\n");
        builder.append("import com.eun.tutorial.service.main.%sService;\n\n");
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

        // Generate the constructor
        builder.append("\tpublic %s(%sService %sService) {\n");
        builder.append("\t\tthis.%sService = %sService;\n");
        builder.append("\t}\n\n");

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
        builder.append("\t\ttry {\n");
        builder.append("\t\t\tlog.info(\"Post List by ID : {}\", id);\n");
        builder.append("\t\t\t%sDTO %sDTOList = %sService.get%sListById(id);\n");
        builder.append("\t\t\treturn new ApiResponse<>(true, \"Successfully retrieved the %s list.\", %sDTOList);\n");
        builder.append("\t\t} catch (Exception e) {\n");
        builder.append("\t\t\treturn new ApiResponse<>(false, \"Failed to retrieve the %s list.\", null);\n");
        builder.append("\t\t}\n");
        builder.append("\t}\n\n");

        builder.append("\t@PostMapping(\"/save\")\n");
        builder.append("\tpublic @ResponseBody ApiResponse save%s(%sDTO %sDTO) {\n");
        builder.append("\t\ttry {\n");
        builder.append("\t\t\t%sService.save%s(%sDTO);\n");
        builder.append("\t\t\treturn new ApiResponse<>(true, \"Success message\", null);\n");
        builder.append("\t\t} catch (Exception e) {\n");
        builder.append("\t\t\treturn new ApiResponse<>(false, \"Error message\", null);\n");
        builder.append("\t\t}\n");
        builder.append("\t}\n\n");

        builder.append("\t@DeleteMapping(\"/{id}\")\n");
        builder.append("\tpublic @ResponseBody ApiResponse delete%s(@PathVariable String id) {\n");
        builder.append("\t\ttry {\n");
        builder.append("\t\t\tlog.info(\"Delete by ID : {}\", id);\n");
        builder.append("\t\t\t%sService.delete%s(id);\n");
        builder.append("\t\t\treturn new ApiResponse<>(true, \"Successfully deleted the %s data.\", null);\n");
        builder.append("\t\t} catch (Exception e) {\n");
        builder.append("\t\t\treturn new ApiResponse<>(false, \"Failed to delete the %s data.\", null);\n");
        builder.append("\t\t}\n");
        builder.append("\t}\n\n");

        builder.append("}\n");

        String result = String.format(builder.toString(), 
        		capitalizedSubject, capitalizedSubject, //imports
        		subject, className, //declaration
        		capitalizedSubject, subject, //field
        		capitalizedSubject, capitalizedSubject, subject, subject, subject, //constructor
        		subject, //GetMapping
        		capitalizedSubject, capitalizedSubject, capitalizedSubject, capitalizedSubject, //PostMapping
        		capitalizedSubject, subject, capitalizedSubject, capitalizedSubject, subject, capitalizedSubject, subject, //PostMapping 
        		capitalizedSubject, capitalizedSubject, subject, capitalizedSubject, capitalizedSubject, subject, //PostMapping
        		capitalizedSubject, subject, capitalizedSubject, subject, subject); //DeleteMapping
        
        autoCodingDTO.setSourceName(className+".java");
        autoCodingDTO.setSourceCode(result);

        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateServiceClass(String subject) {
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
    	String className = capitalizedSubject+"Service";
    	
        StringBuilder builder = new StringBuilder();
        
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
        builder.append("\tint delete%s(String id);\n");
        builder.append("\t%sDTO get%sListById(String id);\n");

        builder.append("}");
        
        String result = String.format(builder.toString(), 
        		capitalizedSubject, className, 
        		capitalizedSubject, capitalizedSubject, 
        		capitalizedSubject, capitalizedSubject, subject,
        		capitalizedSubject, capitalizedSubject, capitalizedSubject);
        
        autoCodingDTO.setSourceName(className+".java");
        autoCodingDTO.setSourceCode(result);

        return autoCodingDTO;
    }
    
    public AutoCodingDTO generateServiceImplClass(String subject) {
    	AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
    	
    	String capitalizedSubject = capitalizeFirstLetter(subject);
    	String className = capitalizedSubject+"ServiceImpl";
    	
        StringBuilder builder = new StringBuilder();
        
        // Generate the package statement
        builder.append("package com.eun.tutorial.service.main;\n\n");

        // Generate the imports
        builder.append("import java.util.List;\n\n");
        builder.append("import java.util.UUID;\n\n");
        builder.append("import org.springframework.stereotype.Service;\n\n");
        builder.append("import com.eun.tutorial.dto.main.%sDTO;\n");
        builder.append("import com.eun.tutorial.mapper.main.%sMapper;\n\n");
        builder.append("import lombok.AllArgsConstructor;\n\n");

        // Generate the class declaration
        builder.append("@Service\n");
        builder.append("@AllArgsConstructor\n");
        builder.append("public class %s implements %sService {\n\n");

        // Generate the fields
        builder.append("\tprivate final %sMapper %sMapper;\n\n");

        // Generate the method implementations
        builder.append("\t@Override\n");
        builder.append("\tpublic List<%sDTO> get%sList() {\n");
        builder.append("\t\treturn %sMapper.select%sList();\n");
        builder.append("\t}\n\n");
        builder.append("\t@Override\n");
        builder.append("\tpublic int save%s(%sDTO %sDTO) {\n");
        builder.append("\t\tif (%sDTO.getId() == null) {\n");
        builder.append("\t\t\t%sDTO.setId(\"%s_\"+UUID.randomUUID());\n");
        builder.append("\t\t\treturn %sMapper.insert%s(%sDTO);\n");
        builder.append("\t\t} else {\n");
        builder.append("\t\t\treturn %sMapper.update%s(%sDTO);\n");
        builder.append("\t\t}\n");
        builder.append("\t}\n\n");
        builder.append("\t@Override\n");
        builder.append("\tpublic int delete%s(String id) {\n");
        builder.append("\t\treturn %sMapper.delete%s(id);\n");
        builder.append("\t}\n\n");
        builder.append("\t@Override\n");
        builder.append("\tpublic %sDTO get%sListById(String id) {\n");
        builder.append("\t\treturn %sMapper.get%sListById(id);\n");
        builder.append("\t}\n\n");

        builder.append("}");

        String result =  String.format(builder.toString(), 
        		capitalizedSubject, capitalizedSubject, //import
        		className, capitalizedSubject, //class 
        		capitalizedSubject, subject, //injection
        		capitalizedSubject, capitalizedSubject, subject, capitalizedSubject, //get
        		capitalizedSubject, capitalizedSubject, subject, subject, subject, subject, subject, capitalizedSubject, subject, subject, capitalizedSubject, subject,  //save
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
        String fieldNames = getFieldNames(fields);
        String wrappedFieldNames = getWrappedFieldNames(fields);
        String formattedFieldNames = getFormattedFieldNames(fields);
        
        StringBuilder content = new StringBuilder();
        content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        content.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        content.append("<mapper namespace=\"%s\">\n\n");
        
        content.append("\t<select id=\"select%sList\" resultType=\"com.eun.tutorial.dto.main.%sDTO\">\n");
        content.append("\t\tSELECT %s FROM %s where del_yn=0 ORDER BY update_dt, create_dt\n");
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
        content.append("\t\tUPDATE %s SET del_yn = 1, updatd_dt = CURRENT_TIMESTAMP WHERE id = #{id}\n");
        content.append("\t</delete>\n\n");
        
        content.append("\t<select id=\"get%sListById\" parameterType=\"string\" resultType=\"com.eun.tutorial.dto.main.%sDTO\">\n");
        content.append("\t\tSELECT %s FROM %s\n");
        content.append("\t\tWHERE id = #{id}\n");
        content.append("\t\tORDER BY update_dt, create_dt\n");
        content.append("\t</select>\n\n");
        content.append("</mapper>");
        
        String result = String.format(content.toString(), 
        		namespace, 
        		capitalizedSubject, capitalizedSubject, fieldNames, tableName, //select
        		capitalizedSubject, tableName, fieldNames, wrappedFieldNames, //insert
        		capitalizedSubject, tableName, formattedFieldNames,//update
        		capitalizedSubject, tableName, //delete
        		capitalizedSubject, capitalizedSubject, fieldNames, tableName); //selectById

        autoCodingDTO.setSourceName(className+".xml");
        autoCodingDTO.setSourceCode(result);
        
        return autoCodingDTO;
    }
    
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        char firstChar = Character.toUpperCase(input.charAt(0));
        return firstChar + input.substring(1);
    }
    
    public String getFieldNames(List<Field> fields) {
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
        
        wrappedFieldNames.append(", #{id}, 0, #{createId}, CURRENT_TIMESTAMP, #{updateId}, CURRENT_TIMESTAMP");

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