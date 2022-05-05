package www.dakai.link.common.beans;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class BasePageForm extends BaseQuery {
    /**
     * 当前页数
     */
    @NotNull(message = "请传入当前页数")
    @Min(1)
    @ApiModelProperty(value = "当前页数", required = true)
    private Long page;
    /**
     * 页大小
     */
    @ApiModelProperty(value = "页数据量", required = true)
    private Long size;

    public Page createPage() {
        return new Page(page, size);
    }
}
