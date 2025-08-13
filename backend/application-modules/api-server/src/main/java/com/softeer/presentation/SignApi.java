package com.softeer.presentation;

import com.softeer.dto.request.SignInRequest;
import com.softeer.dto.request.SignUpRequest;
import com.softeer.security.auth.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "SignApi", description = "로그인 및 회원가입 관련된 API")
@RequestMapping("/user")
public interface SignApi {

    @Operation(
            summary = "로그인 아이디 중복 및 유효성 확인",
            description = """
        로그인 아이디가 사용 가능한지 확인합니다.
        - 이미 존재하면 400 오류(USR-006)
        - 길이가 6자 미만 또는 20자 초과이면 400 오류(USR-007)
        - 영어/숫자 외의 문자가 포함되면 400 오류(USR-008)
        - 사용 가능하면 200 OK 반환
        """,
            responses = {
                    @ApiResponse(responseCode = "400", description = "이미 등록된 아이디",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                    {
                      "errorCode": "USR-006",
                      "message": "이미 등록된 아이디입니다.",
                      "timeStamp": "2025-08-13T13:16:21.607012"
                    }
                """)
                            )
                    )
            }
    )
    @GetMapping("/login-id")
    ResponseEntity<Void> checkLoginId(@RequestParam("loginId") String loginId);

    @Operation(
            summary = "닉네임 중복 및 유효성 확인",
            description = """
        닉네임이 사용 가능한지 확인합니다.
        - 이미 존재하면 400 오류(USR-001)
        - 한글이 아니면 400 오류(USR-002)
        - 길이가 2자 미만 또는 20자 초과이면 400 오류(USR-003)
        - 사용 가능하면 200 OK 반환
        """,
            responses = {
                    @ApiResponse(responseCode = "400", description = "이미 등록된 닉네임",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                    {
                      "errorCode": "USR-001",
                      "message": "이미 등록된 닉네임입니다.",
                      "timeStamp": "2025-08-13T13:16:21.607012"
                    }
                """)
                            )
                    )
            }
    )
    @GetMapping("/nickname")
    ResponseEntity<Void> checkNickname(@RequestParam("nickname") String nickname);


    @Operation(
            summary = "회원가입",
            description = """
        닉네임, 로그인 아이디, 비밀번호를 이용해 새 사용자를 등록합니다.

        **오류 코드 및 상태**
        - USR-001 (400): 이미 등록된 닉네임입니다.
        - USR-002 (400): 한글로 된 닉네임만 가능합니다.
        - USR-003 (400): 닉네임은 2자 이상 20자 이하여야 합니다.
        - USR-004 (400): 비밀번호는 8자 이상이여야 합니다.
        - USR-005 (400): 영어와 숫자로 된 비밀번호만 가능합니다.
        - USR-006 (400): 이미 등록된 아이디입니다.
        - USR-007 (400): 아이디는 6자 이상 20자 이하여야 합니다.
        - USR-008 (400): 영어와 숫자로 된 아이디만 가능합니다.
        - USER-009 (400): 이미 등록된 닉네임 또는 아이디입니다.
        """
    )
    @PostMapping("/sign-up")
    ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest);


    @Operation(
            summary = "로그인",
            description = """
        로그인 아이디와 비밀번호를 이용해 인증을 수행하고, JWT 액세스 토큰을 반환합니다.

        **성공 시**
        - 200: JSON Body에 `value` 필드로 JWT 토큰이 포함됩니다.
          예: `{ "value": "<JWT 토큰 값>" }`

        **오류 코드 및 상태**
        - USR-010 (400): 아이디 또는 패스워드를 잘못 입력하셨습니다.
        """
    )
    @PostMapping("/sign-in")
    ResponseEntity<Token> signIn(@RequestBody SignInRequest signInRequest);

}
