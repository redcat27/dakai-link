package www.dakai.link.common.exceptions;

import lombok.Data;
import www.dakai.link.common.beans.ResultCodeEnum;

/**
 * className:InternalException
 * package:com.pi2star.airmon.agent.common.exceptions
 * Description:
 *
 * @date: 2021/11/22 11:59 上午
 * @author: tangchengzao
 */
@Data
public class InternalException extends RuntimeException {
    /**
     * 异常码
     */
    private ResultCodeEnum resultCode;
    /**
     * 是否打印异常日志
     */
    private Boolean logException;

    public InternalException(ResultCodeEnum resultCode) {
        this(resultCode, resultCode.getMsg());
    }

    public InternalException(ResultCodeEnum resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
        this.logException = false;
    }

    public InternalException(ResultCodeEnum resultCode, Throwable cause) {
        this(resultCode, resultCode.getMsg(), cause);
    }

    public InternalException(ResultCodeEnum resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
        this.logException = true;
    }

}
