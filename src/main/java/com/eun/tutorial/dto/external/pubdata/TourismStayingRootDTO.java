package com.eun.tutorial.dto.external.pubdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourismStayingRootDTO {
	@JsonProperty("TourismStaying")
	private List<TourismStayingDTO> TourismStaying;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TourismStayingDTO {
		@JsonProperty("head")
		private List<HeadDTO> head;
		@JsonProperty("row")
		private List<RowDTO> row;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class HeadDTO {
		@JsonProperty("list_total_count")
		private int list_total_count;
		@JsonProperty("RESULT")
		private RESULTDTO RESULT;
		@JsonProperty("api_version")
		private String api_version;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RESULTDTO {
		@JsonProperty("CODE")
		private String CODE;
		@JsonProperty("MESSAGE")
		private String MESSAGE;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RowDTO {
		@JsonProperty("SIGUN_CD")
		private String SIGUN_CD;
		@JsonProperty("SIGUN_NM")
		private String SIGUN_NM;
		@JsonProperty("BIZPLC_NM")
		private String BIZPLC_NM;
		@JsonProperty("LICENSG_DE")
		private String LICENSG_DE;
		@JsonProperty("LICENSG_CANCL_DE")
		private String LICENSG_CANCL_DE;
		@JsonProperty("BSN_STATE_NM")
		private String BSN_STATE_NM;
		@JsonProperty("CLSBIZ_DE")
		private String CLSBIZ_DE;
		@JsonProperty("BSN_STATE_DIV_CD")
		private String BSN_STATE_DIV_CD;
		@JsonProperty("ROOM_CNT")
		private String ROOM_CNT;
		@JsonProperty("BULDNG_PURPOS_NM")
		private String BULDNG_PURPOS_NM;
		@JsonProperty("BUILD_TOT_AR")
		private String BUILD_TOT_AR;
		@JsonProperty("LOCPLC_FACLT_TELNO")
		private String LOCPLC_FACLT_TELNO;
		@JsonProperty("PLANNG_TOUR_INSRNC_BEGIN_DE")
		private String PLANNG_TOUR_INSRNC_BEGIN_DE;
		@JsonProperty("LOCPLC_AR_INFO")
		private String LOCPLC_AR_INFO;
		@JsonProperty("PLANNG_TOUR_INSRNC_END_DE")
		private String PLANNG_TOUR_INSRNC_END_DE;
		@JsonProperty("CULTUR_BIZMAN_DIV_NM")
		private String CULTUR_BIZMAN_DIV_NM;
		@JsonProperty("CULTUR_PHSTRN_INDUTYPE_NM")
		private String CULTUR_PHSTRN_INDUTYPE_NM;
		@JsonProperty("ROADNM_ZIP_CD")
		private String ROADNM_ZIP_CD;
		@JsonProperty("INSRNC_INST_NM")
		private String INSRNC_INST_NM;
		@JsonProperty("INSRNC_BEGIN_DE")
		private String INSRNC_BEGIN_DE;
		@JsonProperty("INSRNC_END_DE")
		private String INSRNC_END_DE;
		@JsonProperty("FACLT_SCALE")
		private int FACLT_SCALE;
		@JsonProperty("ENG_CMPNM_NM")
		private String ENG_CMPNM_NM;
		@JsonProperty("MEDROOM_EXTNO")
		private String MEDROOM_EXTNO;
		@JsonProperty("CAPTL_AMT")
		private String CAPTL_AMT;
		@JsonProperty("MANUFACT_TRTMNT_PRODLST_CONT")
		private String MANUFACT_TRTMNT_PRODLST_CONT;
		@JsonProperty("REGION_DIV_NM")
		private String REGION_DIV_NM;
		@JsonProperty("BIZCOND_DIV_NM_INFO")
		private String BIZCOND_DIV_NM_INFO;
		@JsonProperty("X_CRDNT_VL")
		private String X_CRDNT_VL;
		@JsonProperty("Y_CRDNT_VL")
		private String Y_CRDNT_VL;
		@JsonProperty("TOT_FLOOR_CNT")
		private int TOT_FLOOR_CNT;
		@JsonProperty("CIRCUMFR_ENVRN_NM")
		private String CIRCUMFR_ENVRN_NM;
		@JsonProperty("REFINE_LOTNO_ADDR")
		private String REFINE_LOTNO_ADDR;
		@JsonProperty("REFINE_ROADNM_ADDR")
		private String REFINE_ROADNM_ADDR;
		@JsonProperty("REFINE_ZIP_CD")
		private String REFINE_ZIP_CD;
		@JsonProperty("REFINE_WGS84_LOGT")
		private String REFINE_WGS84_LOGT;
		@JsonProperty("REFINE_WGS84_LAT")
		private String REFINE_WGS84_LAT;
		@JsonProperty("GROUND_FLOOR_CNT")
		private int GROUND_FLOOR_CNT;
		@JsonProperty("UNDGRND_FLOOR_CNT")
		private int UNDGRND_FLOOR_CNT;
		@JsonProperty("ENG_CMPNM_ADDR")
		private String ENG_CMPNM_ADDR;
		@JsonProperty("SHIP_TOT_TON_CNT")
		private String SHIP_TOT_TON_CNT;
		@JsonProperty("SHIP_CNT")
		private String SHIP_CNT;
		@JsonProperty("SHIP_DATA_CONT")
		private String SHIP_DATA_CONT;
		@JsonProperty("STAGE_AR")
		private String STAGE_AR;
		@JsonProperty("SEAT_CNT")
		private String SEAT_CNT;
		@JsonProperty("SOVENR_KIND_NM")
		private String SOVENR_KIND_NM;
		@JsonProperty("MTNGRM_SMTM_ACEPTNC_PSN_CNT")
		private String MTNGRM_SMTM_ACEPTNC_PSN_CNT;
		@JsonProperty("FACLT_AR")
		private double FACLT_AR;
		@JsonProperty("PLAY_ORGNZ_CNT_DTLS")
		private String PLAY_ORGNZ_CNT_DTLS;
		@JsonProperty("PLAYFACLT_CNT")
		private String PLAYFACLT_CNT;
		@JsonProperty("BRDCAST_FACLT_EXTNO")
		private String BRDCAST_FACLT_EXTNO;
		@JsonProperty("DEVLPMT_FACLT_EXTNO")
		private String DEVLPMT_FACLT_EXTNO;
		@JsonProperty("GUID_SM_EXTNO")
		private String GUID_SM_EXTNO;
		@JsonProperty("SUB_FACLT_DTLS")
		private String SUB_FACLT_DTLS;
	}

}

