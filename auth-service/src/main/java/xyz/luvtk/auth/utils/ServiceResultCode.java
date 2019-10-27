package xyz.luvtk.auth.utils;

/**
 * @author Tank Zheng
 * @since 20190918
 * 结果常量类
 */
public final class  ServiceResultCode {
    private ServiceResultCode() {
    }

    /**
     * 内部服务错误
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /**
     * 客户端请求错误
     */
    public static final int INVALID_ERROR = 400;
}
