package com.test.demo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: syz
 * @Date: 2020/12/17
 */
@Data
public class BaseModel extends Model<BaseModel> {
    private Integer cUser;
}
