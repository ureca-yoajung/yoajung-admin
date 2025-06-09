package com.ureca.yoajungadmin.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseCode {
    // common
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR_500",HttpStatus.INTERNAL_SERVER_ERROR,"예기치 못한 오류가 발생했습니다"),
    STATUS_OK("STATUS_OK_200", HttpStatus.OK, "서버가 정상적으로 동작 중입니다."),
    STATUS_AUTHENTICATED("STATUS_AUTHENTICATED_200", HttpStatus.OK, "로그인된 사용자입니다."),
    STATUS_UNAUTHORIZED("STATUS_UNAUTHORIZED_401", HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),

    // Plan
    PLAN_DETAIL_SUCCESS("FIND_PLAN_DETAIL_200", HttpStatus.OK, "요금제 상세 조회에 성공했습니다."),
    PLAN_LIST_SUCCESS("READ_PLAN_LIST_200", HttpStatus.OK, "요금제 목록 조회에 성공했습니다."),
    PLAN_NOT_FOUND("NOT_FOUND_PLAN_404", HttpStatus.NOT_FOUND, "해당 요금제를 찾을 수 없습니다."),
    PLAN_CREATE_SUCCESS("CREATE_PLAN_201", HttpStatus.CREATED, "요금제 생성에 성공했습니다."),

    // Review
    REVIEW_CREATE_SUCCESS("CREATE_REVIEW_201", HttpStatus.CREATED, "리뷰 생성에 성공했습니다."),
    REVIEW_UPDATE_SUCCESS("UPDATE_REVIEW_200", HttpStatus.OK, "리뷰 수정에 성공했습니다."),
    REVIEW_DELETE_SUCCESS("DELETE_REVIEW_200", HttpStatus.OK, "리뷰 삭제에 성공했습니다."),
    REVIEW_NOT_FOUND("NOT_FOUND_REVIEW_404", HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다."),

    // User
    USER_SIGNUP_SUCCESS("SIGNUP_USER_201", HttpStatus.CREATED, "회원가입에 성공했습니다."),
    USER_NOT_FOUND("NOT_FOUND_USER_404", HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),

    // Chat
    CHAT_SAVE_SUCCESS("SAVE_CHAT_201", HttpStatus.CREATED, "채팅 저장에 성공했습니다."),

    // Product
    PRODUCT_CREATE_SUCCESS("CREATE_PRODUCT_201", HttpStatus.CREATED, "서비스 생성에 성공했습니다."),
    PRODUCT_UPDATE_SUCCESS("UPDATE_PRODUCT_200", HttpStatus.OK, "서비스 수정에 성공했습니다."),
    PRODUCT_DELETE_SUCCESS("DELETE_PRODUCT_200", HttpStatus.OK, "서비스 삭제에 성공했습니다."),
    PRODUCT_LIST_SUCCESS("READ_PRODUCT_LIST_200", HttpStatus.OK, "서비스 목록 조회에 성공했습니다."),
    PRODUCT_DETAIL_SUCCESS("FIND_PRODUCT_DETAIL_200", HttpStatus.OK, "서비스 상세 조회에 성공했습니다."),
    PRODUCT_NOT_FOUND("NOT_FOUND_PRODUCT_404", HttpStatus.NOT_FOUND, "해당 서비스를 찾을 수 없습니다."),
    SERVICE_TYPE_LIST_SUCCESS("READ_SERVICE_TYPE_LIST_200", HttpStatus.OK, "서비스 타입 목록 조회에 성공했습니다."),
    SERVICE_CATEGORY_LIST_SUCCESS("READ_SERVICE_CATEGORY_LIST_200", HttpStatus.OK, "서비스 카테고리 목록 조회에 성공했습니다."),

    // Benefit
    BENEFIT_CREATE_SUCCESS("CREATE_BENEFIT_201", HttpStatus.CREATED, "혜택 생성에 성공했습니다."),
    BENEFIT_UPDATE_SUCCESS("UPDATE_BENEFIT_200", HttpStatus.OK, "혜택 수정에 성공했습니다."),
    BENEFIT_DELETE_SUCCESS("DELETE_BENEFIT_200", HttpStatus.OK, "혜택 삭제에 성공했습니다."),
    BENEFIT_LIST_SUCCESS("READ_BENEFIT_LIST_200", HttpStatus.OK, "혜택 목록 조회에 성공했습니다."),
    BENEFIT_DETAIL_SUCCESS("FIND_BENEFIT_DETAIL_200", HttpStatus.OK, "혜택 상세 조회에 성공했습니다."),
    BENEFIT_NOT_FOUND("NOT_FOUND_BENEFIT_404", HttpStatus.NOT_FOUND, "해당 혜택을 찾을 수 없습니다."),
    BENEFIT_TYPE_LIST_SUCCESS   ("READ_BENEFIT_TYPE_LIST_200",   HttpStatus.OK, "혜택 타입 목록 조회에 성공했습니다!");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
