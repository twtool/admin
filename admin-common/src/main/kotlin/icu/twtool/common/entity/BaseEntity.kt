package icu.twtool.common.entity

import java.time.LocalDateTime

/**
 * 基础实体类
 */
abstract class BaseEntity {

    /**
     * 备注
     */
    var remark: String? = null

    /**
     * 创建时间
     */
    var createTime: LocalDateTime? = null

    /**
     * 创建人
     */
    var createBy: String? = null

    /**
     * 更新时间
     */
    var updateTime: LocalDateTime? = null

    /**
     * 更新人
     */
    var updateBy: String? = null

    companion object {

        const val REMARK = "validation.base.entity.remark"
        const val CREATE_BY = "validation.base.entity.create.by"
        const val CREATE_TIME = "validation.base.entity.create.time"
        const val UPDATE_BY = "validation.base.entity.update.by"
        const val UPDATE_TIME = "validation.base.entity.update.time"
    }
}