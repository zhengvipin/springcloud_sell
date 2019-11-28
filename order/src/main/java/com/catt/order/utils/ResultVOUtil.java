package com.catt.order.utils;

import com.catt.order.vo.ResultVO;

public class ResultVOUtil {
    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(data);
        return resultVO;
    }
}
