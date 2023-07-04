package com.eun.tutorial.dto.main;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstatePriceItem {
    private ResponseBodyDTO response;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseBodyDTO {
        private HeaderDTO header;
        private BodyDTO body;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HeaderDTO {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    @NoArgsConstructor
    public static class BodyDTO {
        private ItemsDTO items;

        public BodyDTO(ItemsDTO items) {
            this.items = items;
        }

        public ItemsDTO getItems() {
            if (items == null) {
                items = new ItemsDTO();
            }
            return items;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemsDTO {
        private List<RealEstatePriceItemDTO> item;
        private int totalSize;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RealEstatePriceItemDTO {
        private String 거래금액;
        private String 거래유형;
        private String 건축년도;
        private String 년;
        private String 법정동;
        private String 아파트;
        private String 월;
        private String 일;
        private String 전용면적;
        private String 중개사소재지;
        private String 지번;
        private String 지역코드;
        private String 층;
        private String 해제사유발생일;
        private String 해제여부;
        
        public double getTransactionAmount() {
            String amountWithoutCommas = 거래금액.replace(",", "");
            return Double.parseDouble(amountWithoutCommas);
        }
        
        public double getExclusiveArea() {
            return Double.parseDouble(전용면적);
        }
    }
}
