package icu.twtool.system.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import icu.twtool.system.entity.SysUser
import icu.twtool.system.mapper.SysUserMapper
import icu.twtool.system.service.ISysUserService
import org.springframework.stereotype.Service

/**
 * 用户服务实现
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@Service
class SysUserServiceImpl : ServiceImpl<SysUserMapper, SysUser>(), ISysUserService {

    override fun getPasswordByUsername(username: String): String? {
        return ktQuery().select(SysUser::password).eq(SysUser::username, username)
            .oneOpt().map(SysUser::password)
            .orElse(null)
    }
}