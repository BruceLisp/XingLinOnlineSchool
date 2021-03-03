package com.hnucm.xinglinonlineschool.service.impl.plus;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnucm.xinglinonlineschool.dao.plus.UserLoginMappers;
import com.hnucm.xinglinonlineschool.pojo.UserLogin;
import com.hnucm.xinglinonlineschool.service.plus.UserLoginServices;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public class UserLoginServiceImpls extends ServiceImpl<UserLoginMappers, UserLogin>
        implements UserLoginServices {
}
