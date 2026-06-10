package com.famsun.momapi.core.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class Result implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    // Object统一承载：单个实体 / List / Page分页对象
    private Object data;

    // ===================== Success默认构造：code=200 msg="" =====================
    public static ResultBuilder Success() {
        ResultBuilder builder = new ResultBuilder();
        builder.withCode(200).withMsg("");
        return builder;
    }

    public static ResultBuilder build() {
        return new ResultBuilder();
    }

    // ===================== 无泛型建造者，无unchecked强转 =====================
    public static class ResultBuilder {
        private final Result result = new Result();

        public ResultBuilder withCode(int code) {
            result.setCode(code);
            return this;
        }

        public ResultBuilder withMsg(String msg) {
            result.setMsg(msg);
            return this;
        }

        // 任意对象数据
        public ResultBuilder withData(Object data) {
            result.setData(data);
            return this;
        }

        // 快捷列表
        public <E> ResultBuilder withList(List<E> list) {
            result.setData(list);
            return this;
        }

        // 快捷分页（仅传入Page对象）
        public <E> ResultBuilder withPage(IPage<E> page) {
            result.setData(page);
            return this;
        }

        public Result build() {
            return result;
        }
    }

    // ========== 兼容旧静态快捷方法 ==========
    public static Result success() {
        return Success().build();
    }

    public static Result success(Object data) {
        return Success().withData(data).build();
    }

    public static Result fail(String msg) {
        return build().withCode(500).withMsg(msg).build();
    }

    public static Result fail(int code, String msg) {
        return build().withCode(code).withMsg(msg).build();
    }
}
