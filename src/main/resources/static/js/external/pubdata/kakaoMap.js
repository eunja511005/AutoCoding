/**
 * This software is protected by copyright laws and international copyright
 * treaties. The ownership and intellectual property rights of this software
 * belong to the developer. Unauthorized reproduction, distribution,
 * modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences. This software is licensed to the user
 * and must be used in accordance with the terms of the license. Under no
 * circumstances should the source code or design of this software be disclosed
 * or leaked. The developer shall not be liable for any loss or damages. Please
 * read the license and usage permissions carefully before using.
 */

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var map;
var latitude = parseFloat(37.41237042374455);
var longitude = parseFloat(127.09847090595022);

$(document).ready(function() {
	debugger;
	
	initSearchCondition()
	  .then(() => {
	    searchMap();
	  })
	  .catch(error => {
	    console.error('셀렉트 박스 초기화 에러:', error);
	});
	
	// select box 변경 이벤트 핸들러
	$('#kakaoMapLoc').change(function() {
		$('#searchButton').click();
	});	
	
	$('#searchButton').on('click', function(event) {
		event.preventDefault();
		searchMap();
	});
	
	// 카드 헤더 클릭시 바디 토글
	$('.card-header').on('click', handleCollapseClick);
	
});

async function initSearchCondition(){
	await initSelectBox('kakaoMapLoc', '/commonCode/KAKAOMAPLOC', false);
}

function searchMap() {
	var container = document.getElementById('map');
	
	var kakaoMapLoc = document.getElementById("kakaoMapLoc").value;
	var parts = kakaoMapLoc.split(",");
	latitude = parseFloat(parts[0]);
	longitude = parseFloat(parts[1]);
	
	if(latitude === 0 && longitude === 0){
	    if (navigator.geolocation) {
	        navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
	      } else {
	        console.log("Geolocation is not supported by this browser.");
	      }
	} else {
		  // 위치 정보가 이미 제공된 경우 options 객체를 설정합니다
		  var options = {
		    center: new kakao.maps.LatLng(latitude, longitude),
		    level: 3
		  };
		  // 지도를 생성하고 보여줍니다
		  createMap(options);
	}
	
}

function successCallback(position) {
	  latitude = position.coords.latitude;
	  longitude = position.coords.longitude;

	  var options = {
	    center: new kakao.maps.LatLng(latitude, longitude),
	    level: 3
	  };
	  // 지도를 생성하고 보여줍니다
	  createMap(options);
}

function errorCallback(error) {
	  console.log("Error occurred while retrieving location: " + error.message);
	  // 위치 정보를 가져오지 못한 경우 기본 위치를 사용합니다
	  var defaultLatitude = parseFloat(37.41237042374455);
	  var defaultLongitude = parseFloat(127.09847090595022);

	  var options = {
	    center: new kakao.maps.LatLng(defaultLatitude, defaultLongitude),
	    level: 3
	  };
	  // 지도를 생성하고 보여줍니다
	  createMap(options);
	}

function createMap(options) {
	  var container = document.getElementById('map');
	  map = new kakao.maps.Map(container, options);
	  
	  // 지도를 클릭한 위치에 표출할 마커입니다
	  var marker = new kakao.maps.Marker({ 
		  // 지도 중심좌표에 마커를 생성합니다 
		  position: map.getCenter() 
	  }); 
	  // 지도에 마커를 표시합니다
	  marker.setMap(map);

	  // 지도에 클릭 이벤트를 등록합니다
	  // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
	  kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
		    
		  // 클릭한 위도, 경도 정보를 가져옵니다 
		  var latlng = mouseEvent.latLng; 
		    
		  // 마커 위치를 클릭한 위치로 옮깁니다
		  marker.setPosition(latlng);
		    
		  var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
		  message += '경도는 ' + latlng.getLng() + ' 입니다';
		    
		  var resultDiv = document.getElementById('clickLatlng'); 
		  resultDiv.innerHTML = message;
		    
	  });

}