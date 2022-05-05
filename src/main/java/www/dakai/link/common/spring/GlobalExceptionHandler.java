package www.dakai.link.common.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import www.dakai.link.common.beans.Result;
import www.dakai.link.common.beans.ResultCodeEnum;
import www.dakai.link.common.enums.DuplicateKeyMessageEnum;
import www.dakai.link.common.exceptions.InternalException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * className:GlobalExceptionHandler
 * package:com.pi2star.airmon.agent.common.spring
 * Description:
 *
 * @date: 2021/11/22 11:30 上午
 * @author: tangchengzao
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Pattern UK_NAME_PATTERN = Pattern.compile("duplicate key value violates unique constraint \"(.*?)\"\n");

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public Object execute(Throwable e) {
        if (e instanceof InternalException) {
            if (((InternalException) e).getLogException()) {
                log.error("系统异常，请及时处理", e);
            }
            return Result.buildFailResult(((InternalException) e).getResultCode(), e.getMessage());
        } else if (e instanceof DuplicateKeyException) {
            Matcher matcher = UK_NAME_PATTERN.matcher(e.getMessage());
            String keyName = DuplicateKeyMessageEnum.DEFAULT.getKeyName();
            if (matcher.find()) {
                keyName = matcher.group(1);
            }
            return Result.buildFailResult(ResultCodeEnum.DB_EXIST_ERROR, DuplicateKeyMessageEnum.parseErrorMessage(keyName));
        } else if (e instanceof MethodArgumentNotValidException) {
            return Result.buildFailResult(ResultCodeEnum.PARAM_VALID_ERROR, ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        } else {
            log.error("未知异常，请及时处理", e);
            return Result.buildFailResult(ResultCodeEnum.ERROR, ResultCodeEnum.ERROR.getMsg());
        }
    }

}
