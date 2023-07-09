package com.eun.tutorial.dto.external.pubdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUltraSrtNcstRootDTO {
	@JsonProperty("response")
	private ResponseDTO response;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ResponseDTO {
		@JsonProperty("header")
		private HeaderDTO header;
		@JsonProperty("body")
		private BodyDTO body;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class HeaderDTO {
		@JsonProperty("resultCode")
		private String resultCode;
		@JsonProperty("resultMsg")
		private String resultMsg;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BodyDTO {
		@JsonProperty("dataType")
		private String dataType;
		@JsonProperty("items")
		private ItemsDTO items;
		@JsonProperty("pageNo")
		private int pageNo;
		@JsonProperty("numOfRows")
		private int numOfRows;
		@JsonProperty("totalCount")
		private int totalCount;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ItemsDTO {
		@JsonProperty("item")
		private List<ItemDTO> item;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ItemDTO {
		@JsonProperty("baseDate")
		private String baseDate;
		@JsonProperty("baseTime")
		private String baseTime;
		@JsonProperty("category")
		private String category;
		@JsonProperty("nx")
		private int nx;
		@JsonProperty("ny")
		private int ny;
		@JsonProperty("obsrValue")
		private String obsrValue;
	}

}
