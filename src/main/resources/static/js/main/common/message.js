// 현재 언어 설정
var language = "ko";

// 다국어 메시지 객체
var messages = {
  en: {
    greeting: "Hello!",
    goodbye: "Goodbye!",
    dataTableAjaxCallError: "An error occurred during the Ajax call in dataTable."
  },
  ko: {
    greeting: "안녕하세요!",
    goodbye: "안녕히 가세요!",
    dataTableAjaxCallError: "데이터 테이블 조회를 위한 Ajax 호출시 오류 발생"
  }
};

// 메시지 가져오기 함수
function getMessage(key) {
  var message = messages[language][key];
  return message || "";
}
