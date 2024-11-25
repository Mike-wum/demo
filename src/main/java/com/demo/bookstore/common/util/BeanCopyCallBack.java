package com.demo.bookstore.common.util;

@FunctionalInterface
public interface BeanCopyCallBack <S, T> {

    /**
     * 定义默认回调方法
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
