package com.eun.tutorial.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JsonStringToDTOSourceUtils {

	private static final String ROOT = "root";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) throws JsonProcessingException {
		//String jsonString = "{\"TourismStaying\":[{\"head\":[{\"list_total_count\":336},{\"RESULT\":{\"CODE\":\"INFO-000\",\"MESSAGE\":\"정상 처리되었습니다.\"}},{\"api_version\":\"1.0\"}]},{\"row\":[{\"SIGUN_CD\":\"41110\",\"SIGUN_NM\":\"수원시\",\"BIZPLC_NM\":\"제이디호텔\",\"LICENSG_DE\":\"20140905\",\"LICENSG_CANCL_DE\":null,\"BSN_STATE_NM\":\"폐업\",\"CLSBIZ_DE\":\"20170718\",\"BSN_STATE_DIV_CD\":\"03\",\"ROOM_CNT\":null,\"BULDNG_PURPOS_NM\":\"호텔\",\"BUILD_TOT_AR\":null,\"LOCPLC_FACLT_TELNO\":\"031-225-9039\",\"PLANNG_TOUR_INSRNC_BEGIN_DE\":null,\"LOCPLC_AR_INFO\":null,\"PLANNG_TOUR_INSRNC_END_DE\":null,\"CULTUR_BIZMAN_DIV_NM\":null,\"CULTUR_PHSTRN_INDUTYPE_NM\":\"관광숙박업\",\"ROADNM_ZIP_CD\":\"16491\",\"INSRNC_INST_NM\":null,\"INSRNC_BEGIN_DE\":null,\"INSRNC_END_DE\":null,\"FACLT_SCALE\":1494,\"ENG_CMPNM_NM\":null,\"MEDROOM_EXTNO\":null,\"CAPTL_AMT\":null,\"MANUFACT_TRTMNT_PRODLST_CONT\":null,\"REGION_DIV_NM\":\"중심상업지역\",\"BIZCOND_DIV_NM_INFO\":null,\"X_CRDNT_VL\":\"202873.610908854    \",\"Y_CRDNT_VL\":\"417421.284747791    \",\"TOT_FLOOR_CNT\":6,\"CIRCUMFR_ENVRN_NM\":null,\"REFINE_LOTNO_ADDR\":\"경기도 수원시 팔달구 인계동 1133-3 \",\"REFINE_ROADNM_ADDR\":\"경기도 수원시 팔달구 권광로134번길 44-8 (인계동)\",\"REFINE_ZIP_CD\":\"442835\",\"REFINE_WGS84_LOGT\":\"127.0332226802\",\"REFINE_WGS84_LAT\":\"37.2587136117\",\"GROUND_FLOOR_CNT\":5,\"UNDGRND_FLOOR_CNT\":1,\"ENG_CMPNM_ADDR\":null,\"SHIP_TOT_TON_CNT\":null,\"SHIP_CNT\":null,\"SHIP_DATA_CONT\":null,\"STAGE_AR\":null,\"SEAT_CNT\":null,\"SOVENR_KIND_NM\":null,\"MTNGRM_SMTM_ACEPTNC_PSN_CNT\":null,\"FACLT_AR\":1494.16,\"PLAY_ORGNZ_CNT_DTLS\":null,\"PLAYFACLT_CNT\":null,\"BRDCAST_FACLT_EXTNO\":null,\"DEVLPMT_FACLT_EXTNO\":null,\"GUID_SM_EXTNO\":null,\"SUB_FACLT_DTLS\":null},{\"SIGUN_CD\":\"41110\",\"SIGUN_NM\":\"수원시\",\"BIZPLC_NM\":\"더모스트호텔\",\"LICENSG_DE\":\"20070920\",\"LICENSG_CANCL_DE\":null,\"BSN_STATE_NM\":\"폐업\",\"CLSBIZ_DE\":\"20171212\",\"BSN_STATE_DIV_CD\":\"03\",\"ROOM_CNT\":45,\"BULDNG_PURPOS_NM\":null,\"BUILD_TOT_AR\":2966,\"LOCPLC_FACLT_TELNO\":\"239-8888\",\"PLANNG_TOUR_INSRNC_BEGIN_DE\":null,\"LOCPLC_AR_INFO\":null,\"PLANNG_TOUR_INSRNC_END_DE\":null,\"CULTUR_BIZMAN_DIV_NM\":\"관광사업\",\"CULTUR_PHSTRN_INDUTYPE_NM\":\"관광숙박업\",\"ROADNM_ZIP_CD\":\"16489\",\"INSRNC_INST_NM\":null,\"INSRNC_BEGIN_DE\":null,\"INSRNC_END_DE\":null,\"FACLT_SCALE\":2966,\"ENG_CMPNM_NM\":\"The Most Hotel\",\"MEDROOM_EXTNO\":null,\"CAPTL_AMT\":null,\"MANUFACT_TRTMNT_PRODLST_CONT\":null,\"REGION_DIV_NM\":\"중심상업지역\",\"BIZCOND_DIV_NM_INFO\":null,\"X_CRDNT_VL\":\"202567.152810558    \",\"Y_CRDNT_VL\":\"418155.941052877    \",\"TOT_FLOOR_CNT\":9,\"CIRCUMFR_ENVRN_NM\":\"유흥업소밀집지역\",\"REFINE_LOTNO_ADDR\":\"경기도 수원시 팔달구 인계동 1032-9 \",\"REFINE_ROADNM_ADDR\":\"경기도 수원시 팔달구 경수대로446번길 55 (인계동)\",\"REFINE_ZIP_CD\":\"442834\",\"REFINE_WGS84_LOGT\":\"127.0297416934\",\"REFINE_WGS84_LAT\":\"37.2653791728\",\"GROUND_FLOOR_CNT\":8,\"UNDGRND_FLOOR_CNT\":1,\"ENG_CMPNM_ADDR\":\"Tourism Hotel\",\"SHIP_TOT_TON_CNT\":null,\"SHIP_CNT\":null,\"SHIP_DATA_CONT\":null,\"STAGE_AR\":null,\"SEAT_CNT\":null,\"SOVENR_KIND_NM\":null,\"MTNGRM_SMTM_ACEPTNC_PSN_CNT\":null,\"FACLT_AR\":2965.98,\"PLAY_ORGNZ_CNT_DTLS\":null,\"PLAYFACLT_CNT\":null,\"BRDCAST_FACLT_EXTNO\":null,\"DEVLPMT_FACLT_EXTNO\":null,\"GUID_SM_EXTNO\":null,\"SUB_FACLT_DTLS\":null},{\"SIGUN_CD\":\"41110\",\"SIGUN_NM\":\"수원시\",\"BIZPLC_NM\":\"호텔까사\",\"LICENSG_DE\":\"20170202\",\"LICENSG_CANCL_DE\":null,\"BSN_STATE_NM\":\"폐업\",\"CLSBIZ_DE\":\"20171219\",\"BSN_STATE_DIV_CD\":\"03\",\"ROOM_CNT\":null,\"BULDNG_PURPOS_NM\":null,\"BUILD_TOT_AR\":null,\"LOCPLC_FACLT_TELNO\":\"031 224 5253\",\"PLANNG_TOUR_INSRNC_BEGIN_DE\":null,\"LOCPLC_AR_INFO\":null,\"PLANNG_TOUR_INSRNC_END_DE\":null,\"CULTUR_BIZMAN_DIV_NM\":null,\"CULTUR_PHSTRN_INDUTYPE_NM\":\"관광숙박업\",\"ROADNM_ZIP_CD\":\"16491\",\"INSRNC_INST_NM\":\"삼성화재\",\"INSRNC_BEGIN_DE\":null,\"INSRNC_END_DE\":null,\"FACLT_SCALE\":1703,\"ENG_CMPNM_NM\":null,\"MEDROOM_EXTNO\":null,\"CAPTL_AMT\":null,\"MANUFACT_TRTMNT_PRODLST_CONT\":null,\"REGION_DIV_NM\":\"중심상업지역\",\"BIZCOND_DIV_NM_INFO\":null,\"X_CRDNT_VL\":\"202881.725          \",\"Y_CRDNT_VL\":\"417515.265000001    \",\"TOT_FLOOR_CNT\":null,\"CIRCUMFR_ENVRN_NM\":null,\"REFINE_LOTNO_ADDR\":\"경기도 수원시 팔달구 인계동 1130-4 \",\"REFINE_ROADNM_ADDR\":\"경기도 수원시 팔달구 권광로142번길 36 (인계동)\",\"REFINE_ZIP_CD\":\"16491\",\"REFINE_WGS84_LOGT\":\"127.0332962799\",\"REFINE_WGS84_LAT\":\"37.2595792414\",\"GROUND_FLOOR_CNT\":6,\"UNDGRND_FLOOR_CNT\":1,\"ENG_CMPNM_ADDR\":null,\"SHIP_TOT_TON_CNT\":null,\"SHIP_CNT\":null,\"SHIP_DATA_CONT\":null,\"STAGE_AR\":null,\"SEAT_CNT\":null,\"SOVENR_KIND_NM\":null,\"MTNGRM_SMTM_ACEPTNC_PSN_CNT\":null,\"FACLT_AR\":1702.74,\"PLAY_ORGNZ_CNT_DTLS\":null,\"PLAYFACLT_CNT\":null,\"BRDCAST_FACLT_EXTNO\":null,\"DEVLPMT_FACLT_EXTNO\":null,\"GUID_SM_EXTNO\":null,\"SUB_FACLT_DTLS\":null},{\"SIGUN_CD\":\"41670\",\"SIGUN_NM\":\"여주시\",\"BIZPLC_NM\":\"일성레저산업(주) 남한강콘도&리조트\",\"LICENSG_DE\":\"20011025\",\"LICENSG_CANCL_DE\":null,\"BSN_STATE_NM\":\"영업중\",\"CLSBIZ_DE\":null,\"BSN_STATE_DIV_CD\":\"13\",\"ROOM_CNT\":167,\"BULDNG_PURPOS_NM\":\"콘도미니엄\",\"BUILD_TOT_AR\":22380,\"LOCPLC_FACLT_TELNO\":\"031-883-1199\",\"PLANNG_TOUR_INSRNC_BEGIN_DE\":null,\"LOCPLC_AR_INFO\":null,\"PLANNG_TOUR_INSRNC_END_DE\":null,\"CULTUR_BIZMAN_DIV_NM\":\"관광사업\",\"CULTUR_PHSTRN_INDUTYPE_NM\":\"관광숙박업\",\"ROADNM_ZIP_CD\":\"12636\",\"INSRNC_INST_NM\":null,\"INSRNC_BEGIN_DE\":null,\"INSRNC_END_DE\":null,\"FACLT_SCALE\":22392,\"ENG_CMPNM_NM\":\"Ilsung Namhangang Condominium\",\"MEDROOM_EXTNO\":null,\"CAPTL_AMT\":2000000000,\"MANUFACT_TRTMNT_PRODLST_CONT\":null,\"REGION_DIV_NM\":\"일반상업지역\",\"BIZCOND_DIV_NM_INFO\":null,\"X_CRDNT_VL\":\"257651.286011267    \",\"Y_CRDNT_VL\":\"422313.399144506    \",\"TOT_FLOOR_CNT\":17,\"CIRCUMFR_ENVRN_NM\":\"기타\",\"REFINE_LOTNO_ADDR\":\"경기도 여주시 천송동 561-1 \",\"REFINE_ROADNM_ADDR\":\"경기도 여주시 신륵로 5 (천송동, 일성남한강콘도미니엄)\",\"REFINE_ZIP_CD\":\"12636\",\"REFINE_WGS84_LOGT\":\"127.6507833872\",\"REFINE_WGS84_LAT\":\"37.3012544766\",\"GROUND_FLOOR_CNT\":13,\"UNDGRND_FLOOR_CNT\":4,\"ENG_CMPNM_ADDR\":\"Condominium\",\"SHIP_TOT_TON_CNT\":0,\"SHIP_CNT\":0,\"SHIP_DATA_CONT\":null,\"STAGE_AR\":0,\"SEAT_CNT\":0,\"SOVENR_KIND_NM\":null,\"MTNGRM_SMTM_ACEPTNC_PSN_CNT\":0,\"FACLT_AR\":22392,\"PLAY_ORGNZ_CNT_DTLS\":null,\"PLAYFACLT_CNT\":0,\"BRDCAST_FACLT_EXTNO\":null,\"DEVLPMT_FACLT_EXTNO\":null,\"GUID_SM_EXTNO\":null,\"SUB_FACLT_DTLS\":null},{\"SIGUN_CD\":\"41820\",\"SIGUN_NM\":\"가평군\",\"BIZPLC_NM\":\"가평관광호텔\",\"LICENSG_DE\":\"19940907\",\"LICENSG_CANCL_DE\":null,\"BSN_STATE_NM\":\"폐업\",\"CLSBIZ_DE\":\"20151214\",\"BSN_STATE_DIV_CD\":\"03\",\"ROOM_CNT\":null,\"BULDNG_PURPOS_NM\":\"호텔\",\"BUILD_TOT_AR\":null,\"LOCPLC_FACLT_TELNO\":null,\"PLANNG_TOUR_INSRNC_BEGIN_DE\":null,\"LOCPLC_AR_INFO\":null,\"PLANNG_TOUR_INSRNC_END_DE\":null,\"CULTUR_BIZMAN_DIV_NM\":\"관광사업\",\"CULTUR_PHSTRN_INDUTYPE_NM\":\"관광숙박업\",\"ROADNM_ZIP_CD\":\"12419\",\"INSRNC_INST_NM\":null,\"INSRNC_BEGIN_DE\":null,\"INSRNC_END_DE\":null,\"FACLT_SCALE\":2373,\"ENG_CMPNM_NM\":null,\"MEDROOM_EXTNO\":null,\"CAPTL_AMT\":null,\"MANUFACT_TRTMNT_PRODLST_CONT\":null,\"REGION_DIV_NM\":null,\"BIZCOND_DIV_NM_INFO\":null,\"X_CRDNT_VL\":\"245240.045160165\",\"Y_CRDNT_VL\":\"480903.482430904\",\"TOT_FLOOR_CNT\":7,\"CIRCUMFR_ENVRN_NM\":\"주택가주변\",\"REFINE_LOTNO_ADDR\":\"경기도 가평군 가평읍 읍내리 403번지\",\"REFINE_ROADNM_ADDR\":\"경기도 가평군 가평읍 보납로34번길 16\",\"REFINE_ZIP_CD\":\"477801\",\"REFINE_WGS84_LOGT\":\"127.5146099\",\"REFINE_WGS84_LAT\":\"37.8295091\",\"GROUND_FLOOR_CNT\":6,\"UNDGRND_FLOOR_CNT\":1,\"ENG_CMPNM_ADDR\":null,\"SHIP_TOT_TON_CNT\":null,\"SHIP_CNT\":null,\"SHIP_DATA_CONT\":null,\"STAGE_AR\":null,\"SEAT_CNT\":null,\"SOVENR_KIND_NM\":null,\"MTNGRM_SMTM_ACEPTNC_PSN_CNT\":null,\"FACLT_AR\":2373,\"PLAY_ORGNZ_CNT_DTLS\":null,\"PLAYFACLT_CNT\":null,\"BRDCAST_FACLT_EXTNO\":null,\"DEVLPMT_FACLT_EXTNO\":null,\"GUID_SM_EXTNO\":null,\"SUB_FACLT_DTLS\":null}]}]}";
		String jsonString = "{\"response\":{\"header\":{\"resultCode\":\"00\",\"resultMsg\":\"NORMAL_SERVICE\"},\"body\":{\"dataType\":\"JSON\",\"items\":{\"item\":[{\"baseDate\":\"20230709\",\"baseTime\":\"0000\",\"category\":\"PTY\",\"nx\":55,\"ny\":127,\"obsrValue\":\"0\"},{\"baseDate\":\"20230709\",\"baseTime\":\"0000\",\"category\":\"REH\",\"nx\":55,\"ny\":127,\"obsrValue\":\"87\"},{\"baseDate\":\"20230709\",\"baseTime\":\"0000\",\"category\":\"RN1\",\"nx\":55,\"ny\":127,\"obsrValue\":\"0\"},{\"baseDate\":\"20230709\",\"baseTime\":\"0000\",\"category\":\"T1H\",\"nx\":55,\"ny\":127,\"obsrValue\":\"24.8\"},{\"baseDate\":\"20230709\",\"baseTime\":\"0000\",\"category\":\"UUU\",\"nx\":55,\"ny\":127,\"obsrValue\":\"-0.6\"},{\"baseDate\":\"20230709\",\"baseTime\":\"0000\",\"category\":\"VEC\",\"nx\":55,\"ny\":127,\"obsrValue\":\"163\"},{\"baseDate\":\"20230709\",\"baseTime\":\"0000\",\"category\":\"VVV\",\"nx\":55,\"ny\":127,\"obsrValue\":\"2.3\"},{\"baseDate\":\"20230709\",\"baseTime\":\"0000\",\"category\":\"WSD\",\"nx\":55,\"ny\":127,\"obsrValue\":\"2.4\"}]},\"pageNo\":1,\"numOfRows\":1000,\"totalCount\":8}}}";
		//String jsonString = "{\"name\":\"John\", \"age\":30, \"address\": {\"street\": \"123 Main St\", \"city\": \"Anytown\"}, \"courses\": [\"Math\", \"Science\"]}";
		//String jsonString = "[{\"name\":\"Ram\", \"email\":\"Ram@gmail.com\"},{\"name\":\"Bob\", \"email\":\"bob32@gmail.com\"}]";
		//String jsonString = "[\"Sunday\", \"Monday\", \"Tuesday\", \"Wednesday\", \"Thursday\", \"Friday\", \"Saturday\"]";
		
		JsonNode rootNode = objectMapper.readTree(jsonString);

		LinkedHashMap<String, List<String>> objectMapList = new LinkedHashMap<>();
		List<String> lineList = new ArrayList<>();
		objectMapList.put(ROOT, lineList);

		traverseJsonNode(rootNode, objectMapList, lineList, ROOT);

		log.info("========================================================");
		log.info(objectMapList.toString());
		
		StringBuilder dtoSourceCode = generateDtoClasses(objectMapList);
		log.info("========================================================");
		log.info("\n"+dtoSourceCode.toString());
	}
	
	public String conversion(String jsonString) throws JsonProcessingException {
		JsonNode rootNode = objectMapper.readTree(jsonString);
		
		LinkedHashMap<String, List<String>> objectMapList = new LinkedHashMap<>();
		List<String> lineList = new ArrayList<>();
		objectMapList.put(ROOT, lineList);

		traverseJsonNode(rootNode, objectMapList, lineList, ROOT);
		
		StringBuilder dtoSourceCode = generateDtoClasses(objectMapList);
		
		return dtoSourceCode.toString();
	}


	public static void traverseJsonNode(JsonNode jsonNode, Map<String, List<String>> objectMapList,
			List<String> lineList, String parentName) {
		
		if(jsonNode.isArray()) {
			traverseJsonNode(jsonNode.get(0), objectMapList, lineList, ROOT);
		}else if(jsonNode.isObject()) {
			Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
			while (fields.hasNext()) {
				Map.Entry<String, JsonNode> field = fields.next();
				String fieldName = field.getKey();
				JsonNode fieldValue = field.getValue();

				if (fieldValue.isObject()) {
					List<String> nestedFields = new ArrayList<>();
					objectMapList.put(fieldName, nestedFields);
					lineList.add("OBJ " + fieldName);

					traverseJsonNode(fieldValue, objectMapList, nestedFields, fieldName);

				} else if (fieldValue.isArray()) {

					List<String> nestedFields = new ArrayList<>();
					objectMapList.put(fieldName, nestedFields);
					lineList.add("LST " + fieldName);
					for (JsonNode jsonNode2 : fieldValue) {
						
						if(jsonNode2.isObject() || jsonNode2.isArray()) {
							traverseJsonNode(jsonNode2, objectMapList, nestedFields, fieldName);
						}else {// 오브젝트 배열이 아니라 Primitive Type 배열인 경우
							objectMapList.remove(fieldName);
							lineList.remove("LST " + fieldName);
							
							String fieldType = getFieldType(jsonNode2);
							lineList.add("List<"+fieldType+"> "+fieldName);
							break; // 한번만 추가하고 for문 빠져 나가도록
						}
						
					}
				} else {
					String fieldType = getFieldType(fieldValue);
					String fieldDeclaration = fieldType + " " + fieldName;
					
		            if (!objectMapList.get(parentName).contains(fieldDeclaration)) {
		                objectMapList.get(parentName).add(fieldDeclaration);
		            }
				}
			}
		}
	}
	
	public static StringBuilder generateDtoClasses(Map<String, List<String>> objectMapList) {
		StringBuilder dtoSourceCode = new StringBuilder();
		
		dtoSourceCode.append("import com.fasterxml.jackson.annotation.JsonProperty;\n");  
		dtoSourceCode.append("import java.util.List;\n");  
		dtoSourceCode.append("import lombok.AllArgsConstructor;\n");  
		dtoSourceCode.append("import lombok.Data;\n");  
		dtoSourceCode.append("import lombok.NoArgsConstructor;\n\n"); 
		
		List<String> rootList = objectMapList.get(ROOT);
		objectMapList.remove(ROOT);
		
		dtoSourceCode.append("@Data\n");    
		dtoSourceCode.append("@NoArgsConstructor\n");
		dtoSourceCode.append("@AllArgsConstructor\n");
		dtoSourceCode.append("public class ").append(StringUtils.capitalize(ROOT)).append(" {\n");

		boolean isRoot = true;
		generatePrivateVariable(dtoSourceCode, rootList, isRoot);
		
		isRoot = false;
		
		for (Map.Entry<String, List<String>> entry : objectMapList.entrySet()) {
			String className = entry.getKey();
			List<String> fields = entry.getValue();

			dtoSourceCode.append("\t@Data\n");    
			dtoSourceCode.append("\t@NoArgsConstructor\n");
			dtoSourceCode.append("\t@AllArgsConstructor\n");
			dtoSourceCode.append("\tpublic static class ").append(StringUtils.capitalize(className) + "DTO").append(" {\n");

			generatePrivateVariable(dtoSourceCode, fields, isRoot);
			
			dtoSourceCode.append("\t}\n\n");
		}
		
		dtoSourceCode.append("}");

		return dtoSourceCode;
	}

	private static void generatePrivateVariable(StringBuilder dtoSourceCode, List<String> fields, boolean isRoot) {
		for (String field : fields){
			if(isRoot) {
				dtoSourceCode.append("\t@JsonProperty(\""+field.split(" ")[1]+"\")\n");
				dtoSourceCode.append("\tprivate ");
			}else {
				dtoSourceCode.append("\t\t@JsonProperty(\""+field.split(" ")[1]+"\")\n");
				dtoSourceCode.append("\t\tprivate ");
			}
			
			if(field.startsWith("OBJ ")) {
				field = field.replace("OBJ ", "");
				
				dtoSourceCode
				.append(StringUtils.capitalize(field) + "DTO")
				.append(" "+field);
			}else if(field.startsWith("LST ")) {
				field = field.replace("LST ", "");
				
				dtoSourceCode
				.append("List<")
				.append(StringUtils.capitalize(field) + "DTO")
				.append("> "+field);
			}else {
				dtoSourceCode.append(field);
			}
			if(isRoot) {
				dtoSourceCode.append(";\n\n");
			}else {
				dtoSourceCode.append(";\n");
			}
			
		}
	}

	public static String getFieldType(JsonNode fieldValue) {
		if (fieldValue.isBoolean()) {
			return "boolean";
		} else if (fieldValue.isInt()) {
			return "int";
		} else if (fieldValue.isLong()) {
			return "long";
		} else if (fieldValue.isDouble()) {
			return "double";
		} else {
			return "String";
		}
	}
}
