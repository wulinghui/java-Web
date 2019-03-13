/**
 *  cn.wlh.framework.ioc以Class为主，之后一一对应factory。
 *  这样对factory的管理过于混乱。
 *  这里的核心思想是，一个容器管理factory。当getBean的时候遍历factory的getBean。
 */
/**
 * @author 吴灵辉
 *
 */
package cn.wlh.framework.ioc.factory;