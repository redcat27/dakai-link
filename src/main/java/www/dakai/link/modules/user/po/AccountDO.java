package www.dakai.link.modules.user.po;

import lombok.Data;
import www.dakai.link.common.beans.BaseDO;

@Data
public class AccountDO extends BaseDO {
    private String code;
    private String username;
    private String phone;
    private String password;
    private String name;
    private String email;
    private String token;
    private String remark;
    private String enabled;

}
