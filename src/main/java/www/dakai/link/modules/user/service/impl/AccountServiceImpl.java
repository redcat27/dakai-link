package www.dakai.link.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import www.dakai.link.modules.user.mapper.AccountMapper;
import www.dakai.link.modules.user.po.AccountDO;
import www.dakai.link.modules.user.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountDO> implements AccountService {
}
