package com.qing.forestpharmacy.common.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    //分页数据
    @lombok.Data
    public static class Data<T> {

        private List<T> list;

        private long total;

    }
    //封装接口返回结果
    private static <T> Result<T> result(String code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    // 成功
    public static <T> Result<T> success(T data) {
        return result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    // 成功，无返回数据
    public static <T> Result<T> success() {
        return success("00000");
    }

    // 成功，根据Code获取结果
    public static <T> Result<T> success(String code) {
        return reCode(code);
    }

    // 成功，无返回数据
    public static <T> Result<T> success(String msg, T data) {
        return result("00000", msg, data);
    }

    // 根据状态码获取结果
    public static <T> Result<T> reCode(String code) {
        ResultCode resultCode = ResultCode.getByCode(code);
        return result(resultCode.getCode(), resultCode.getMessage(), null);
    }

    // 自定义错误信息
    public static <T> Result<T> failed(String code, String msg) {
        return result(code, msg, null);
    }

    // 分页
    public static <T> Result<Data<T>> PageRresult(IPage<T> page) {
        Data<T> data = new Data<>();
        data.setList(page.getRecords());
        data.setTotal(page.getTotal());

        return result(ResultCode.SUCCESS.getCode(),"", data);
    }

}
