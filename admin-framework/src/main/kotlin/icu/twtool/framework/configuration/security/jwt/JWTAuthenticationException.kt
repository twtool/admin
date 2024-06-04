package icu.twtool.framework.configuration.security.jwt

import icu.twtool.common.status.IStatus
import org.springframework.security.core.AuthenticationException

class JWTAuthenticationException(val status: IStatus) : AuthenticationException(status.msg)
