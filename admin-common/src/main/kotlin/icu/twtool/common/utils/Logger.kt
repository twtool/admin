package icu.twtool.common.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 获取 Logger 实例
 *
 * @author wen-flower
 * @since 2024-06-04
 */
fun <T : Any> T.getLogger(): Logger = LoggerFactory.getLogger(this::class.java)