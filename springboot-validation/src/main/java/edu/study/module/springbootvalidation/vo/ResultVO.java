package edu.study.module.springbootvalidation.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;

/**
 * @author zl
 * @date 2021/1/24 11:32
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ResultVO extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";

    /**
     * 状态类型
     */
    public enum Type {
        /**
         * 成功
         */
        SUCCESS(1),
        /**
         * 警告
         */
        WARN(2),
        /**
         * 错误
         */
        ERROR(0),
        /**
         * 无权限
         */
        UNAUTH(3),
        /**
         * 未登录、登录超时
         */
        UNLOGIN(4);
        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 状态类型
     */
    private Type type;

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回内容
     */
    private String msg;

    /**
     * 数据对象
     */
    private Object data;

    /**
     * 初始化一个新创建的 ResultVO 对象
     *
     * @param type 状态类型
     * @param msg  返回内容
     */
    public ResultVO(Type type, String msg) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 ResultVO 对象
     *
     * @param type 状态类型
     * @param msg  返回内容
     * @param data 数据对象
     */
    public ResultVO(Type type, String msg, Object data) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
//         数据为空的时候，还是需要把参数传给前台
//        if (StringUtils.isNotNull(data)) {
//            super.put(DATA_TAG, data);
//        }
        super.put(DATA_TAG, data);
    }


    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static ResultVO success() {
        return ResultVO.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static ResultVO success(Object data) {
        return ResultVO.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static ResultVO success(String msg) {
        return ResultVO.success(msg, null);
    }


    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static ResultVO success(String msg, Object data) {
        return new ResultVO(Type.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ResultVO warn(String msg) {
        return ResultVO.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static ResultVO warn(String msg, Object data) {
        return new ResultVO(Type.WARN, msg, data);
    }


    /**
     * 返回错误消息
     *
     * @return
     */
    public static ResultVO error() {
        return ResultVO.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ResultVO error(String msg) {
        return ResultVO.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static ResultVO error(String msg, Object data) {
        return new ResultVO(Type.ERROR, msg, data);
    }


    /**
     * 无权限返回
     *
     * @return
     */
    public static ResultVO unAuth() {
        return new ResultVO(Type.UNAUTH, "您没有访问权限！", null);
    }

    /**
     * 无权限
     */
    public static ResultVO unAuth(String msg) {
        return new ResultVO(Type.UNAUTH, msg, null);
    }

    /**
     * 未登录或登录超时。请重新登录
     */
    public static ResultVO unLogin() {
        return new ResultVO(Type.UNLOGIN, "未登录或登录超时。请重新登录！", null);
    }

    public static class Success {

        public static ResultVO data(Object data) {
            return new ResultVO(Type.SUCCESS, "操作成功 Operation Successful", data);
        }

        public static ResultVO outMsg(String msg) {
            return new ResultVO(Type.SUCCESS, msg, null);
        }

        public static ResultVO outMsgAndData(String msg, Object data) {
            return new ResultVO(Type.SUCCESS, msg, data);
        }
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("code", getCode())
                .append("msg", getMsg())
                .append("data", getData())
                .toString();
    }
}
