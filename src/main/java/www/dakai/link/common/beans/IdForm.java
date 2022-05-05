package www.dakai.link.common.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * className:IdForm
 * package:com.pi2star.airmon.agent.common.beans
 * Description:
 *
 * @date: 2021/11/23 4:55 下午
 * @author: tangchengzao
 */
@Data
@ApiModel
public class IdForm {
    @NotBlank(message = "请选择需要操作的数据")
    @ApiModelProperty(value = "ID", required = true)
    private String id;
}
