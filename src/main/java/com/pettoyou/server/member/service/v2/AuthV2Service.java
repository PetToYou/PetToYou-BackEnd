package com.pettoyou.server.member.service.v2;

import com.pettoyou.server.member.dto.request.LoginReqDto;
import com.pettoyou.server.member.dto.response.LoginRespDto;
import com.pettoyou.server.member.entity.enums.OAuthProvider;

public interface AuthV2Service {

    LoginRespDto socialLoginWithoutApple(
            LoginReqDto loginReqDto,
            OAuthProvider provider
    );
}
