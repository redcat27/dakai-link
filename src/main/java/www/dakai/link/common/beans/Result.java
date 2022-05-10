package www.dakai.link.common.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import static www.dakai.link.common.beans.ResultCodeEnum.SUCCESS;

@Data
@Accessors(chain = true)
public class Result<TData> {

    private TData data;

    private Integer code;

    private String message;

    @ApiModelProperty(value = "版本号")
    private String version = "1.0.0";

    /**
     * 请求成功处理结果
     *
     * @param data
     * @param <TData>
     * @return
     */
    public static <TData> Result<TData> buildSuccessResult(TData data) {
        return new Result<TData>().setCode(SUCCESS.getCode()).setMessage(SUCCESS.getMsg()).setData(data);
    }

    /**
     * 无数据请求成功处理结果
     *
     * @param <TData>
     * @return
     */
    public static <TData> Result<TData> buildEmptySuccessResult() {
        return new Result<TData>().setCode(SUCCESS.getCode()).setMessage("成功");
    }

    /**
     * 请求失败处理结果
     *
     * @param resultCode
     * @param <TData>
     * @return
     */
    public static <TData> Result<TData> buildFailResult(ResultCodeEnum resultCode) {
        return new Result<TData>().setCode(resultCode.getCode()).setMessage(resultCode.getMsg());
    }

    /**
     * 请求失败处理结果
     *
     * @param resultCode
     * @param message
     * @param <TData>
     * @return
     */
    public static <TData> Result<TData> buildFailResult(ResultCodeEnum resultCode, String message) {
        return new Result<TData>().setCode(resultCode.getCode()).setMessage(message);
    }
}
