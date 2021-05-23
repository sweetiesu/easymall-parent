package cn.lit.seckill.mapper;

import com.jt.common.pojo.Seckill;
import com.jt.common.pojo.Success;

import java.util.List;

public interface SecMapper {
    List<Seckill> selectAll();

    Seckill selectOne(Long seckillId);

    int updateNumber(Long seckillId);

    void insertSuc(Success suc);
}
